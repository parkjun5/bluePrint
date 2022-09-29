package example.blueprint.restapi.member.domain;

import lombok.Getter;
import org.hibernate.Hibernate;

import javax.persistence.*;
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

    public static Member createMember(Member input, Address address) {
        Member member = new Member();
        member.username = input.username;
        member.password = input.password;
        member.regNo = input.regNo;
        member.name = input.name;
        member.roles = input.roles;
        member.address = address;
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
