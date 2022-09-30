package example.blueprint.restapi.member.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static example.blueprint.restapi.member.domain.QMember.*;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;
    @Override
    public Long findCountByUsername(String username) {
        return queryFactory.select(member.count())
                .from(member)
                .where(member.username.eq(username))
                .fetchOne();
    }

    @Override
    public Long findCountByNameAndRegNo(String name, String regNo) {
        return queryFactory.select(member.count())
                .from(member)
                .where(member.name.eq(name)
                        .and(member.regNo.eq(regNo)))
                .fetchOne();
    }
}
