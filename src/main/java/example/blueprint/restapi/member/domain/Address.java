package example.blueprint.restapi.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @NotBlank
    private String location;
    @NotBlank
    private String addressNo;

    public Address(String location, String addressNo) {
        this.location = location;
        this.addressNo = addressNo;
    }
}
