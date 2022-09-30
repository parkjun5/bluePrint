package example.blueprint.restapi.member.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.blueprint.restapi.member.application.dto.MemberSignupDto;
import example.blueprint.restapi.member.domain.Address;
import example.blueprint.restapi.member.ui.MemberController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("no CSRF Token then MissingCsrfTokenException")
    void missingCsrfTokenException() throws Exception {
        //given
        MemberSignupDto signUpMemberDto = MemberSignupDto.builder()
                .username("Tester@gmail.com")
                .password("qjrmfltm")
                .name("손오공")
                .regNo("820326-2715702")
                .address(new Address("a", "b"))
                .build();

        mockMvc.perform(post("/member/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpMemberDto)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("401 에러발생 SecurityContextHolder null SecurityContextImpl [Null authentication]")
    void signUpTestNoAuth() throws Exception {
        //given
        MemberSignupDto signUpMemberDto = MemberSignupDto.builder()
                .username("Tester@gmail.com")
                .password("qjrmfltm")
                .name("손오공")
                .regNo("820326-2715702")
                .address(new Address("a", "b"))
                .build();

        mockMvc.perform(post("/member/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpMemberDto))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void signUpTest() throws Exception {
        //given
        MemberSignupDto signUpMemberDto = MemberSignupDto.builder()
                .username("Tester@gmail.com")
                .password("qjrmfltm")
                .name("손오공")
                .regNo("820326-2715702")
                .address(new Address("a", "b"))
                .build();

        mockMvc.perform(post("/member/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpMemberDto))
                            .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

}