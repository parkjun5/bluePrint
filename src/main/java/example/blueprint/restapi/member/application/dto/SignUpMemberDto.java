package example.blueprint.restapi.member.application.dto;

import example.blueprint.restapi.common.security.encoder.RegNoEncoder;
import example.blueprint.restapi.member.domain.Address;
import example.blueprint.restapi.member.exception.MemberSignupException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.security.GeneralSecurityException;
import java.util.Objects;

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

    public void encryptInfo(RegNoEncoder customEncoder, PasswordEncoder passwordEncoder) {
        encryptRegNo(customEncoder);
        this.password = passwordEncoder.encode(password);
    }

    private void encryptRegNo(RegNoEncoder customEncoder) {
        try {
            this.regNo = customEncoder.encrypt(regNo);
        } catch (GeneralSecurityException e) {
            throw new MemberSignupException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignUpMemberDto that = (SignUpMemberDto) o;
        return Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getPassword(), that.getPassword()) && Objects.equals(getName(), that.getName()) && Objects.equals(getRegNo(), that.getRegNo()) && Objects.equals(getAddress(), that.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), getName(), getRegNo(), getAddress());
    }
}
