package example.blueprint.restapi.member.application;

import example.blueprint.restapi.member.application.dto.SignUpMemberDto;
import example.blueprint.restapi.member.exception.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
@RequiredArgsConstructor
public class MemberValidator {

    private final MemberService memberService;

    public void validate(SignUpMemberDto signUpMemberDto, BindingResult bindingResult) {

        boolean isDuplicateUsername = memberService.checkDuplicateUsername(signUpMemberDto.getUsername());
        if (isDuplicateUsername) {
            bindingResult.reject("400", "중복된 회원 아이디입니다.");
            throw new DuplicateMemberException();
        }

        boolean isDuplicateNameAndRegNo = memberService.checkDuplicateNameAndRegNo(signUpMemberDto.getName(), signUpMemberDto.getRegNo());
        if (isDuplicateNameAndRegNo) {
            throw new DuplicateMemberException();
        }
    }
}
