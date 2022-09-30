package example.blueprint.restapi.member.application;

import example.blueprint.restapi.member.application.dto.MemberSignupDto;
import example.blueprint.restapi.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
@RequiredArgsConstructor
public class MemberValidator {

    private final MemberRepository memberRepository;

    public void validate(MemberSignupDto memberSignupDto, BindingResult bindingResult) {

        boolean isDuplicateUsername = checkDuplicateUsername(memberSignupDto.getUsername());
        if (isDuplicateUsername) {
            bindingResult.rejectValue("username", "signup", "username is duplicate");
        }

        boolean isDuplicateNameAndRegNo = checkDuplicateNameAndRegNo(memberSignupDto.getName(), memberSignupDto.getRegNo());
        if (isDuplicateNameAndRegNo) {
            bindingResult.rejectValue("nameAndRegNo", "signup", "nameAndRegNo is duplicate");
        }
    }

    public boolean checkDuplicateUsername(String username) {
        return memberRepository.findCountByUsername(username) > 0L;
    }

    public boolean checkDuplicateNameAndRegNo(String name, String regNo) {
        return memberRepository.findCountByNameAndRegNo(name, regNo) > 0L;
    }
}
