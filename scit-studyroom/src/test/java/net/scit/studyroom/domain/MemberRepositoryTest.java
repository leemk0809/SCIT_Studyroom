package net.scit.studyroom.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.transaction.Transactional;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.junit.jupiter.api.Test;

// 단위 테스트(DB 관련된 Bean이 Ioc에 등록되면 됨)

@Transactional
@AutoConfigureTestDatabase(replace=Replace.NONE) // Replace.ANY 가짜 DB로 테스트, Replace.NONE 실제 DB로 테스트
@DataJpaTest // Repository들을 다 IoC에 등록해 둠
public class MemberRepositoryTest {

	@Autowired
	private MemberRepository memberRepository;
	
	@Test
	public void save_test() {
		// given
		Member member = new Member("repo","repo","repo");
		
		// when
		Member memberEntity = memberRepository.save(member);
		
		// then
		assertEquals("repo", memberEntity.getName());
	}
}
