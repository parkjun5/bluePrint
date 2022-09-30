package example.blueprint.restapi.member.application.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import example.blueprint.restapi.member.ui.MemberController;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class MemberDtoRepresentation extends RepresentationModel<MemberResponseDto> {

    @JsonUnwrapped
    private final MemberResponseDto memberResponseDto;

    public MemberDtoRepresentation(Long id, MemberResponseDto memberResponseDto) {
        this.memberResponseDto = memberResponseDto;
        add(linkTo(MemberController.class).slash(id).withSelfRel());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MemberDtoRepresentation that = (MemberDtoRepresentation) o;
        return Objects.equals(memberResponseDto, that.memberResponseDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), memberResponseDto);
    }
}
