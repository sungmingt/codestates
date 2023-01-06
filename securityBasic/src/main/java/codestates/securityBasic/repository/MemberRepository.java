package codestates.securityBasic.repository;

import codestates.securityBasic.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByUsername(String username);
}
