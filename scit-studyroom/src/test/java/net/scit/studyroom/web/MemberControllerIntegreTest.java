package net.scit.studyroom.web;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import lombok.extern.slf4j.Slf4j;
import net.scit.studyroom.domain.Member;
import net.scit.studyroom.dto.MemberDTO;
import net.scit.studyroom.service.MemberService;


/**
 * 통합테스트 (모든 Bean들을 똑같이 IoC에 올리고 테스트 하는 것)
 * WebEnvironment.MOCK = 실제 톰켓을 올리는게 아니라, 다른 톰켓으로 테스트
 * WebEnvironment.RANDOM_PORT = 실제 톰켓으로 테스트
 * @AutoConfigureMockMvc MockMvc를 IoC에 등록해줌
 * @Transactional 각각의 테스트함수가 종료될 때마다 트랜잭션을 rollback해주는 Annotation
 * @author minkuk
 *
 */
@Slf4j
@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK) 
public class MemberControllerIntegreTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean // Ioc환경에 Bean등록됨
	private MemberService memberService;
	
	@Test
	public void save_test() {
		log.info("save_test ===============================================");
		Member member = memberService.addMember(new MemberDTO("saveTest","abcd","saveName"));
		System.out.println("member: "+member);
	}
}
