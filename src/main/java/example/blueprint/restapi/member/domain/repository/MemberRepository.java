package example.blueprint.restapi.member.domain.repository;

import example.blueprint.restapi.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryQuerydsl {
}
