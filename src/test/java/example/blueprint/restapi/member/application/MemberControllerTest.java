package example.blueprint.restapi.member.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.blueprint.restapi.member.application.dto.SignUpMemberDto;
import example.blueprint.restapi.member.domain.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

    @Test
    void signUpTest() throws Exception {
        //given
        SignUpMemberDto signUpMemberDto = SignUpMemberDto.builder()
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
                .andExpect(status().isOk());
    }

    @Test
    void signUpNULLTest() throws Exception {
        //given
        SignUpMemberDto signUpMemberDto = SignUpMemberDto.builder()
                .username("Tester@gmail.com")
                .password("1231231212")
                .name("손오공")
                .regNo("820326-2715702")
                .build();

        mockMvc.perform(post("/member/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpMemberDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}