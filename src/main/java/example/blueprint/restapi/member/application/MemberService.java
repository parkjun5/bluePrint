package example.blueprint.restapi.member.application;

import example.blueprint.restapi.common.application.dto.BindingResultRepresentation;
import example.blueprint.restapi.common.security.encoder.RegNoEncoder;
import example.blueprint.restapi.member.application.dto.MemberDtoRepresentation;
import example.blueprint.restapi.member.application.dto.MemberResponseDto;
import example.blueprint.restapi.member.application.dto.MemberSignupDto;
import example.blueprint.restapi.member.domain.repository.MemberRepository;
import example.blueprint.restapi.member.domain.Member;
import example.blueprint.restapi.member.ui.MemberController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final RegNoEncoder customEncoder;
    private final PasswordEncoder passwordEncoder;
    private final MemberValidator memberValidator;

    public ResponseEntity<Object> signup(MemberSignupDto memberSignupDto, BindingResult bindingResult) {
        memberSignupDto.encryptInfo(customEncoder, passwordEncoder);

        if (hasErrorByValidate(memberSignupDto, bindingResult)) {
            return handleBindingResultError(bindingResult);
        }

        return memberSave(memberSignupDto);
    }

    private boolean hasErrorByValidate(MemberSignupDto memberSignupDto, BindingResult bindingResult) {
        memberValidator.validate(memberSignupDto, bindingResult);
        return bindingResult.hasErrors();
    }

    private ResponseEntity<Object> handleBindingResultError(BindingResult bindingResult) {
        BindingResultRepresentation representation = new BindingResultRepresentation(bindingResult);
        representation.add(linkTo(MemberController.class).withRel("index"));
        representation.add(Link.of("swagger-ui"));
        return ResponseEntity.badRequest().body(representation);
    }

    private ResponseEntity<Object> memberSave(MemberSignupDto signUpMemberDto) {
        Member savedMember = memberRepository.save(Member.createMember(signUpMemberDto));
        MemberDtoRepresentation memberDtoRepresentation = new MemberDtoRepresentation(savedMember.getId(), new MemberResponseDto(savedMember.getUsername(), savedMember.getName()));
        return ResponseEntity.ok().body(memberDtoRepresentation);
    }

}
