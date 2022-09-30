package example.blueprint.restapi.common.application.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.EntityModel;
import org.springframework.validation.BindingResult;

import java.util.Objects;

public class BindingResultRepresentation extends EntityModel<BindingResult> {
    @JsonUnwrapped
    private final BindingResult bindingResult;

    public BindingResultRepresentation(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BindingResultRepresentation that = (BindingResultRepresentation) o;
        return Objects.equals(bindingResult, that.bindingResult);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), bindingResult);
    }
}
