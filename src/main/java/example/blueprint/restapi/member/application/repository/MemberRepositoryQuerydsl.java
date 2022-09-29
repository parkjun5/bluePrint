package example.blueprint.restapi.member.application.repository;

public interface MemberRepositoryQuerydsl {
    Long findCountByUsername(String username);

    Long findCountByNameAndRegNo(String name, String regNo);
}
