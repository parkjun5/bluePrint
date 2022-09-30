package example.blueprint.restapi.member.application.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto extends RepresentationModel<MemberResponseDto> {

    private String username;

    private String name;

}
