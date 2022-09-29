package example.blueprint.restapi.member.application;

import example.blueprint.restapi.member.application.dto.SignUpMemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Object> signUp(@RequestBody @Validated SignUpMemberDto signUpMemberDto, BindingResult bindingResult) {
        return memberService.signup(signUpMemberDto, bindingResult);
    }


}
