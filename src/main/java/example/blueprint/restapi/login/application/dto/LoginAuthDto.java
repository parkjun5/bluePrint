package example.blueprint.restapi.login.application.dto;

import example.blueprint.restapi.member.domain.MemberRole;
import lombok.*;

import java.util.Set;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
public class LoginAuthDto {
    private String username;
    private String password;
    private Set<MemberRole> roles;
}
