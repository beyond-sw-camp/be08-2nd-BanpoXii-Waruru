package waruru.backend.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import waruru.backend.user.config.JwtTokenProvider;
import waruru.backend.user.domain.Member;
import waruru.backend.user.domain.MemberRepository;
import waruru.backend.user.domain.MemberRole;
import waruru.backend.user.domain.MemberStatus;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class MemberController {

    @Autowired
    private MemberRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String, String> member) {
        Member saved = null;
        ResponseEntity response = null;
        try {
                String hashPassword = passwordEncoder.encode(member.get("password"));
                saved = userRepository.save(Member.builder()
                            .email(member.get("email"))
                            .password(hashPassword)
                            .name(member.get("name"))
                            .nickname(member.get("nickname"))
                            .role(MemberRole.valueOf(member.get("role")))
                            .status(MemberStatus.valueOf(member.get("status")))
                            .build());

            if (saved.getId() > 0) {
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("Given member details are successfully registered");
            }
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return response;
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        Member foundUser = userRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입 되지 않은 이메일입니다."));
        if (!passwordEncoder.matches(user.get("password"), foundUser.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 맞지 않습니다.");
        }

        return jwtTokenProvider.createToken(foundUser.getEmail(), foundUser.getRole());
    }

}
