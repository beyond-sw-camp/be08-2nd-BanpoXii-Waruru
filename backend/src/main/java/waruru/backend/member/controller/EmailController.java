package waruru.backend.member.controller;

import io.swagger.v3.oas.annotations.Operation;
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

@Tag(name = "User")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/user/email")
public class EmailController {

    private final EmailService emailService;

    @Operation(summary = "회원 가입 시 입력한 이메일로 인증 코드를 발송하는 API")
    @PostMapping("/send")
    public String mailSend(@RequestBody MemberEmailRequestDto dto) throws MessagingException {
        log.info("EmailController.mailSend()");
        emailService.sendEmail(dto.getEmail());
        return "인증코드가 발송되었습니다.";
    }

    @Operation(summary = "이메일과 인증코드를 대조하여 확인하는 API")
    @PostMapping("/verify")
    public String verify(@RequestBody MemberEmailVerifyRequestDto dto) {
        log.info("EmailController.verify()");
        boolean isVerify = emailService.verifyEmailCode(dto.getEmail(), dto.getVerificationCode());
        return isVerify ? "인증이 완료되었습니다." : "인증을 실패하셨습니다.";
    }

    @Operation(summary = "이메일로 초기화된 비밀번호를 발송하는 API")
    @PostMapping("/send/password")
    public String passwordMailSend(@RequestBody MemberEmailRequestDto memberEmailRequestDto) throws MessagingException{
        log.info("EmailController.passwordMailSend()");
        emailService.sendPasswordEmail(memberEmailRequestDto.getEmail());
        return "초기화된 비밀번호가 발송되었습니다.";
    }
}