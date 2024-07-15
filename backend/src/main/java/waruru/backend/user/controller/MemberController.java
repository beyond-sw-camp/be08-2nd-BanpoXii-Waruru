package waruru.backend.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import waruru.backend.user.config.JwtTokenProvider;
import waruru.backend.user.domain.Member;
import waruru.backend.user.dto.MemberLoginRequestDTO;
import waruru.backend.user.dto.MemberRegisterRequestDTO;
import waruru.backend.user.dto.MemberUpdateRequestDTO;
import waruru.backend.user.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원 정보를 등록하는 API")
    @PostMapping("/register")
    public ResponseEntity<String> registerMember(@RequestBody @Valid MemberRegisterRequestDTO memberRegisterRequestDTO) {
        memberService.registerMember(memberRegisterRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "로그인 API")
    @PostMapping("/login")
    public String login(@RequestBody @Valid MemberLoginRequestDTO memberLoginRequestDTO) {

        return memberService.createToken(memberLoginRequestDTO);
    }

    @Operation(summary = "회원 정보를 수정하는 API")
    @PutMapping("/update")
    public ResponseEntity<String> updateMember(@RequestBody @Valid MemberUpdateRequestDTO memberUpdateRequestDTO) {
        memberService.updateMember(memberUpdateRequestDTO);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "회원 상태를 탈퇴 상태로 수정하는 API")
    @PutMapping("/delete/{email}")
    public ResponseEntity<String> deleteMember(@PathVariable String email) {
        memberService.deleteMember(email);

        return ResponseEntity.noContent().build();
    }
}
