package example.blueprint.restapi.member.domain.repository;

public interface MemberRepositoryQuerydsl {
    Long findCountByUsername(String username);

    Long findCountByNameAndRegNo(String name, String regNo);
}
