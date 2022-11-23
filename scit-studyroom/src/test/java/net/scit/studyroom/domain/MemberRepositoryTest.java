package net.scit.studyroom.domain;

import javax.transaction.Transactional;

import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

// 단위 테스트(DB 관련된 Bean이 Ioc에 등록되면 됨)

@Transactional
@AutoConfigureTestDatabase(replace=Replace.ANY) // Replace.ANY 가짜 DB로 테스트, Replace.NONE 실제 DB로 테스트
@DataJpaTest // Repository들을 다 IoC에 등록해 둠
public class MemberRepositoryTest {

	@Mock
	private MemberRepository memberRepository;
}
