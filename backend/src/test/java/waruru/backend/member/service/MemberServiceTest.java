package waruru.backend.member.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import waruru.backend.member.domain.Member;
import waruru.backend.member.domain.MemberRepository;
import waruru.backend.member.domain.MemberRole;
import waruru.backend.member.domain.MemberStatus;
import waruru.backend.member.dto.MemberLoginRequestDTO;
import waruru.backend.member.dto.MemberRegisterRequestDTO;
import waruru.backend.member.dto.MemberUpdateRequestDTO;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Spy
    private PasswordEncoder passwordEncoder;

    @DisplayName("회원 가입")
    @Test
    public void registerMemberTest() {

        // given
        passwordEncoder = new BCryptPasswordEncoder();
        MemberRegisterRequestDTO requestDTO = MemberRegisterRequestDTO.builder()
                .email("test@example.com")
                .password("example")
                .name("test")
                .nickname("testman")
                .role(MemberRole.LESSEE)
                .status(MemberStatus.Y)
                .build();

        String hashedPassword = passwordEncoder.encode(requestDTO.getPassword());
        Member member = Member.builder()
                .email(requestDTO.getEmail())
                .password(hashedPassword)
                .name(requestDTO.getName())
                .nickname(requestDTO.getNickname())
                .role(requestDTO.getRole())
                .status(requestDTO.getStatus())
                .build();

        when(memberRepository.save(member)).thenReturn(member);

        //when
        member = memberRepository.save(member);

        // then
        assertEquals(member.getEmail(), requestDTO.getEmail());
        assertTrue(passwordEncoder.matches(requestDTO.getPassword(), member.getPassword()));
        assertEquals(member.getName(), requestDTO.getName());
        assertEquals(member.getNickname(), requestDTO.getNickname());
        assertEquals(member.getRole(), requestDTO.getRole());
        assertEquals(member.getStatus(), requestDTO.getStatus());

    }

    @DisplayName("이메일 패스워드로 회원 확인")
    @Test
    public void findMemberByEmailPasswordTest() {

        //given
        passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode("test");
        Member member = Member.builder()
                .email("test@example.com")
                .password(hashedPassword)
                .name("test")
                .nickname("testman")
                .role(MemberRole.LESSEE)
                .status(MemberStatus.Y)
                .build();

        MemberLoginRequestDTO requestDTO = MemberLoginRequestDTO.builder()
                .email("test@example.com")
                .password("test")
                .build();

        when(memberRepository.findByEmail(requestDTO.getEmail())).thenReturn(Optional.of(member));

        //when
        member = memberRepository.findByEmail(requestDTO.getEmail()).orElseThrow(() ->
                new IllegalArgumentException("invalid email"));

        //then
        assertEquals(member.getEmail(), requestDTO.getEmail());
        assertTrue(passwordEncoder.matches(requestDTO.getPassword(), member.getPassword()));
    }

    @DisplayName("회원 정보 수정")
    @Test
    public void updateMember() {

        //given
        String hashedPassword = passwordEncoder.encode("testpassword");
        Member member = Member.builder()
                .email("test1@example.com")
                .password(hashedPassword)
                .name("test1")
                .nickname("testnick1")
                .role(MemberRole.LESSEE)
                .status(MemberStatus.Y)
                .build();

        Member member2 = Member.builder()
                .email("test1@example.com")
                .password(hashedPassword)
                .name("test2")
                .nickname("testnick2")
                .role(MemberRole.LESSOR)
                .status(MemberStatus.Y)
                .build();

        MemberUpdateRequestDTO requestDTO = MemberUpdateRequestDTO.builder()
                .email("test1@example.com")
                .name("test2")
                .nickname("testnick2")
                .role(MemberRole.LESSOR)
                .build();

        when(memberRepository.findByEmail(requestDTO.getEmail())).thenReturn(Optional.of(member));
        when(memberRepository.save(member)).thenReturn(member);

        //when
        member = memberRepository.findByEmail(requestDTO.getEmail()).orElseThrow(() ->
                new IllegalArgumentException("invalid email"));
        member.update(requestDTO);
        member = memberRepository.save(member);

        //then
        assertEquals(member.getEmail(), member2.getEmail());
        assertEquals(member.getName(), member2.getName());
        assertEquals(member.getNickname(), member2.getNickname());
        assertEquals(member.getRole(), member2.getRole());

    }

    @DisplayName("회원 탈퇴")
    @Test
    public void deleteMemberTest() {

        //given
        String hashedPassword = passwordEncoder.encode("testpassword");
        Member member = Member.builder()
                .email("test1@example.com")
                .password(hashedPassword)
                .name("test1")
                .nickname("testnick1")
                .role(MemberRole.LESSEE)
                .status(MemberStatus.Y)
                .build();

        when(memberRepository.findByEmail(member.getEmail())).thenReturn(Optional.of(member));
        when(memberRepository.save(member)).thenReturn(member);

        member = memberRepository.save(member);

        String path = member.getEmail();

        //when
        Member foundmember = memberRepository.findByEmail(path).orElseThrow(() ->
                new IllegalArgumentException("invalid email address :" + path));
        foundmember.updateStatus();
        assertEquals(member.getStatus(), MemberStatus.N);
    }
}