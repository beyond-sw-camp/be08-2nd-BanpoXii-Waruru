package waruru.backend.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import waruru.backend.member.domain.*;
import waruru.backend.member.dto.MemberLoginRequestDTO;
import waruru.backend.member.dto.MemberRegisterRequestDTO;
import waruru.backend.member.dto.MemberUpdateRequestDTO;
import waruru.backend.member.util.JwtTokenProvider;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtTokenProvider jwtTokenProvider;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    @Transactional
    public Optional<Member> registerMember(MemberRegisterRequestDTO memberRegisterRequestDTO){

        String hashPassword = passwordEncoder.encode(memberRegisterRequestDTO.getPassword());

        Member member = Member.builder()
                .email(memberRegisterRequestDTO.getEmail())
                .password(hashPassword)
                .name(memberRegisterRequestDTO.getName())
                .nickname(memberRegisterRequestDTO.getNickname())
                .role(memberRegisterRequestDTO.getRole())
                .status(memberRegisterRequestDTO.getStatus())
                .build();

        return Optional.of(memberRepository.save(member));
    }

    @Transactional
    public Member findMemberByEmailPassword(MemberLoginRequestDTO memberLoginRequestDTO) {

        Member member = memberRepository.findByEmail(memberLoginRequestDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입 되지 않은 이메일입니다."));

        if (!passwordEncoder.matches(memberLoginRequestDTO.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 맞지 않습니다.");
        }
        if (member.getStatus().equals(MemberStatus.N)){
            throw  new IllegalArgumentException("탈퇴한 회원입니다.");
        }
        return member;
    }

    @Transactional
    public Member findMemberByEmail(String email) {

        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new IllegalArgumentException("이미 가입되어 있는 이메일 입니다."));

        return member;
    }

    @Transactional
    public ResponseCookie createToken(MemberLoginRequestDTO memberLoginRequestDTO) {

        Member member = this.findMemberByEmailPassword(memberLoginRequestDTO);

        String accessToken = jwtTokenProvider.createAccessToken(accessHeader ,member.getEmail(), member.getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken(refreshHeader, member.getEmail(), member.getRole());

        RefreshToken newRefreshToken = RefreshToken.builder()
                .username(member.getEmail())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiration(new Date(new Date().getTime() + jwtTokenProvider.getRefreshTokenValidTime()).getTime())
                .build();

        refreshTokenRepository.save(newRefreshToken);

        ResponseCookie cookie = ResponseCookie.from("access_token", accessToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(3600)
                .build();

        RefreshToken found = refreshTokenRepository.findByAccessToken(newRefreshToken.getAccessToken()).orElseThrow(() ->
                new IllegalArgumentException("invalid email"));

        return cookie;
    }

    @Transactional
    public Optional<Void> updateMember(MemberUpdateRequestDTO memberUpdateRequestDTO) {

        Member member = memberRepository.findByEmail(memberUpdateRequestDTO.getEmail()).orElseThrow(() ->
                        new IllegalArgumentException("Invalid email address: " + memberUpdateRequestDTO.getEmail()));

        member.update(memberUpdateRequestDTO);

        return Optional.empty();
    }

    @Transactional
    public Optional<Void> deleteMember(String email) {

        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new IllegalArgumentException("Invalid email address: " + email));

        member.updateStatus();

        return Optional.empty();
    }
}
