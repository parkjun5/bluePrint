package example.blueprint.restapi.member.application;

import example.blueprint.restapi.member.application.dto.MemberSignupDto;
import example.blueprint.restapi.member.domain.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MemberValidatorTest {

    @InjectMocks
    private MemberValidator memberValidator;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    BindingResult bindingResult;

    @Test
    void validate() {
        MemberSignupDto signUpMemberDto = getSignUpMemberDto();
        when(memberRepository.findCountByUsername(any(String.class))).thenReturn(0L);
        when(memberRepository.findCountByNameAndRegNo(any(String.class), any(String.class))).thenReturn(0L);

        memberValidator.validate(signUpMemberDto, bindingResult);

        Assertions.assertThat(bindingResult.hasErrors()).isFalse();
    }

    @Test
    void duplicateName() {
        MemberSignupDto signUpMemberDto = getSignUpMemberDto();
        when(memberRepository.findCountByUsername(any(String.class))).thenReturn(1L);
        when(memberRepository.findCountByNameAndRegNo(any(String.class), any(String.class))).thenReturn(0L);

        memberValidator.validate(signUpMemberDto, bindingResult);

        Assertions.assertThat(bindingResult.hasErrors()).isTrue();
    }

    private MemberSignupDto getSignUpMemberDto() {
        MemberSignupDto signUpMemberDto = new MemberSignupDto();
        signUpMemberDto.setUsername("username");
        signUpMemberDto.setPassword("password");
        signUpMemberDto.setName("name");
        signUpMemberDto.setRegNo("xxxxxx-xxxxxxx");
        return signUpMemberDto;
    }
}