package waruru.backend.member.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import waruru.backend.member.dto.MemberEmailRequestDto;
import waruru.backend.member.dto.MemberEmailVerifyRequestDto;
import waruru.backend.member.service.EmailService;

@Tag(name = "UserEmail", description = "이메일 인증 관리")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public String mailSend(@RequestBody MemberEmailRequestDto dto) throws MessagingException {
        log.info("EmailController.mailSend()");
        System.out.println("Email : " + dto.getEmail());
        emailService.sendEmail(dto.getEmail());
        return "인증코드가 발송되었습니다.";
    }

    // 인증코드 인증
    @PostMapping("/verify")
    public String verify(@RequestBody MemberEmailVerifyRequestDto dto) {
        log.info("EmailController.verify()");
        boolean isVerify = emailService.verifyEmailCode(dto.getEmail(), dto.getVerificationCode());
        return isVerify ? "인증이 완료되었습니다." : "인증을 실패하셨습니다.";
    }
}
