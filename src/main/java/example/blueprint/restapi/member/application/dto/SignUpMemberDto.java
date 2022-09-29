package example.blueprint.restapi.member.application.dto;

import example.blueprint.restapi.member.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpMemberDto {

    @NotBlank
    @Email
    private String username;

    @NotBlank
    @Length(min = 5, max = 30)
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String regNo;

    @NotNull
    private Address address;
}
