package net.scit.studyroom.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import net.scit.studyroom.domain.Member;
import net.scit.studyroom.dto.MemberDTO;
import net.scit.studyroom.service.MemberService;

// 단위 테스트 (Controller, Filter, ControllerAdvice)

@WebMvcTest
@Slf4j
public class MemberControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private MemberService memberService;
	
	// BDDMockito 패턴 given - when - then
	@Test
	public void save_test() throws Exception {
		// given (테스트를 하기 위한 준비)
		MemberDTO memberDTO = new MemberDTO(null,"test333","test333");
		String content = new ObjectMapper().writeValueAsString(memberDTO);
		when(memberService.addMember(memberDTO)).thenReturn(new Member("test333", "test333", "test333"));
		
		// when (테스트 실행)
		ResultActions resultAction = mockMvc.perform(post("/member")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content)
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		// then (검증)
		resultAction
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.passwd").value("test333"))
			.andDo(MockMvcResultHandlers.print());
		
	}
}
