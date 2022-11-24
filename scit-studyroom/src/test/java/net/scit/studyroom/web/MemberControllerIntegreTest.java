package net.scit.studyroom.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import net.scit.studyroom.domain.Member;
import net.scit.studyroom.dto.MemberDTO;


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
	
	// BDDMockito 패턴 given - when - then
	// ObjectMapper : JavaObject <-> JSON
	// MockMvc : 서버에 배포하지않고, 스프링 MVC동작 테스트 가능
	@Test
	public void save_test() throws Exception {
		// given (테스트를 하기 위한 준비)
		MemberDTO memberDTO = new MemberDTO("test333","test333","test333");
		String content = new ObjectMapper().writeValueAsString(memberDTO); // JSON형태로 만들어줌

		// when (테스트 실행)
		ResultActions resultAction = mockMvc.perform(post("/member") // post요청을 전송
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content)
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		// then (검증)
		resultAction
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.passwd").value("test333"))
			.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void findAll_test() throws Exception {
		// given(findAll 이기 때문에 given생략)
		// when
		ResultActions resultAction = mockMvc.perform(get("/member")
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		// then
		resultAction
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[0].name").value("민국"))
			.andExpect(jsonPath("$", Matchers.hasSize(5)))
			.andExpect(jsonPath("$.[3].passwd").value("1234"))
			.andDo(MockMvcResultHandlers.print());		
	}
	
	@Test
	public void findById_test() throws Exception {
		// given
		String id = "mk";		
		// when
		ResultActions resultAction = mockMvc.perform(get("/member/{id}", id)
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		// then
		resultAction
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(id))
			.andExpect(jsonPath("$.name").value("민국"))
			.andDo(MockMvcResultHandlers.print());
	}
}
