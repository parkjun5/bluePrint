package example.blueprint.restapi.member.domain;

import example.blueprint.restapi.member.application.dto.MemberSignupDto;
import lombok.Getter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    private String password;

    private String name;

    private String regNo;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<MemberRole> roles;

    @Embedded
    private Address address;

    public static Member createMember(MemberSignupDto signUpMemberDto) {
        Member member = new Member();
        member.username = signUpMemberDto.getUsername();
        member.password = signUpMemberDto.getPassword();
        member.regNo = signUpMemberDto.getRegNo();
        member.name = signUpMemberDto.getName();
        member.roles = Collections.singleton(MemberRole.CUSTOMER);
        member.address = signUpMemberDto.getAddress();
        return member;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Member member = (Member) o;
        return id != null && Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
