package example.blueprint.restapi.member.application;

import example.blueprint.restapi.common.security.encoder.RegNoEncoder;
import example.blueprint.restapi.member.application.dto.SignUpMemberDto;
import example.blueprint.restapi.member.application.repository.MemberRepository;
import example.blueprint.restapi.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final RegNoEncoder customEncoder;
    private final PasswordEncoder passwordEncoder;
    private final MemberValidator memberValidator;

    public ResponseEntity<Object> signup(SignUpMemberDto signUpMemberDto, BindingResult bindingResult) {

        memberValidator.validate(signUpMemberDto, bindingResult);

        if (bindingResult != null) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError());
        }

        memberSave(signUpMemberDto);
        return ResponseEntity.ok().body(null);
    }

    private void memberSave(SignUpMemberDto signUpMemberDto) {
        signUpMemberDto.encryptInfo(customEncoder, passwordEncoder);
        Member member = Member.createMember(signUpMemberDto);
        memberRepository.save(member);
    }

    public boolean checkDuplicateUsername(String username) {
        return memberRepository.findCountByUsername(username) > 0L;
    }

    public boolean checkDuplicateNameAndRegNo(String name, String regNo) {
        return memberRepository.findCountByNameAndRegNo(name, regNo) > 0L;
    }
}
