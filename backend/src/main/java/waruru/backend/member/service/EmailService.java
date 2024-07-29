package waruru.backend.member.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import waruru.backend.member.domain.Email;
import waruru.backend.member.domain.EmailRepository;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    private final EmailRepository emailRepository;

    private static final String senderEmail = "macleod.park@gmail.com";

    private String createCode() {

        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 6;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 | i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private MimeMessage createEmailForm(String email) throws MessagingException {

        String authCode = createCode();

        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("안녕하세요. 인증번호입니다.");
        message.setFrom(senderEmail);
        message.setText(authCode, "utf-8", "html");

        Email emailToSave = Email.builder()
                .email(email)
                .verificationCode(authCode)
                .build();

        emailRepository.save(emailToSave);

        return message;
    }

    public void sendEmail(String toEmail) throws MessagingException {

        Email exist = emailRepository.findByEmail(toEmail).orElse(null);
        if(exist != null) {
            emailRepository.deleteByEmail(toEmail);
        }

        MimeMessage emailForm = createEmailForm(toEmail);

        javaMailSender.send(emailForm);
    }

    public Boolean verifyEmailCode(String email, String code) {

        Email found = emailRepository.findByEmail(email).orElse(null);
        log.info("code found by email: " + found);
        if (found == null) {
            return false;
        }
        return found.getVerificationCode().equals(code);
    }
}
