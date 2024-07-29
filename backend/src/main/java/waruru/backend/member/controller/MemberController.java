package waruru.backend.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import waruru.backend.member.dto.MemberLoginRequestDTO;
import waruru.backend.member.dto.MemberRegisterRequestDTO;
import waruru.backend.member.dto.MemberUpdateRequestDTO;
import waruru.backend.member.service.MemberService;

@Tag(name = "User", description = "회원 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/user")
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
    public ResponseEntity<String> login(@RequestBody @Valid MemberLoginRequestDTO memberLoginRequestDTO) {
        memberService.findMemberByEmail(memberLoginRequestDTO.getEmail());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, memberService.createToken(memberLoginRequestDTO).toString())
                .body("login success");
    }

    @Operation(summary = "회원 정보를 수정하는 API")
    @PutMapping("/update")
    public ResponseEntity<String> updateMember(@RequestBody @Valid MemberUpdateRequestDTO memberUpdateRequestDTO) {
        memberService.updateMember(memberUpdateRequestDTO);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "회원 상태를 탈퇴 상태로 수정하는 API")
    @PutMapping("/delete/{email}")
    public ResponseEntity<String> deleteMember(@PathVariable String email) {
        memberService.deleteMember(email);

        return ResponseEntity.ok().build();
    }
}
