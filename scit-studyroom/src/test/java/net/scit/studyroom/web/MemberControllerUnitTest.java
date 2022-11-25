package net.scit.studyroom.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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
	// ObjectMapper : JavaObject <-> JSON
	// MockMvc : 서버에 배포하지않고, 스프링 MVC동작 테스트 가능
	@Test
	public void save_test() throws Exception {
		// given (테스트를 하기 위한 준비)
		MemberDTO memberDTO = new MemberDTO("test333","test333","test333");
		String content = new ObjectMapper().writeValueAsString(memberDTO); // JSON형태로 만들어줌
		//when(memberService.addMember(memberDTO)).thenReturn(new Member("test333", "test333", "test333")); // given과 같으나 가독성 문제로 given으로 바꿈
		given(memberService.addMember(memberDTO)).willReturn(new Member("test333", "test333", "test333"));
		

		// when (테스트 실행)
		ResultActions resultAction = mockMvc.perform(post("/member") // post요청을 전송
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content)
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		// then (검증)
		resultAction
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value("test333"))
			.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void findAll_test() throws Exception {
		// given
		List<Member> memberList = new ArrayList<>();
		memberList.add(new Member("findAlltest1","findAlltest1","findAlltest1"));
		memberList.add(new Member("findAlltest2","findAlltest2","findAlltest2"));
		given(memberService.findAllMember()).willReturn(memberList);

		// when
		ResultActions resultAction = mockMvc.perform(get("/member")
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		// then
		resultAction
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", Matchers.hasSize(2)))
			.andExpect(jsonPath("$.[0].passwd").value("findAlltest1"))
			.andDo(MockMvcResultHandlers.print());		
	}
	
	@Test
	public void findById_test() throws Exception {
		// given
		String id = "findByIdTest";
		given(memberService.findMemberById("findByIdTest")).willReturn(new Member(id,"findByIdTest","findByIdTest"));
		
		// when
		ResultActions resultAction = mockMvc.perform(get("/member/{id}", id)
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		// then
		resultAction
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(id))
			.andExpect(jsonPath("$.name").value("findByIdTest"))
			.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void update_test() throws Exception {
		// given
		String id = "Anonymous1";
		MemberDTO memberDTO = new MemberDTO(id, "updateUnit", "updateUnit");
		String content = new ObjectMapper().writeValueAsString(memberDTO);
		given(memberService.updateMember(id, memberDTO)).willReturn(new Member(id, "updateUnit", "updateUnit"));
		
		// when
		ResultActions resultAction = mockMvc.perform(put("/member/{id}", id)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content)
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		// then
		resultAction
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value("updateUnit"))
			.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void daeleteById_test() throws Exception {
		// given
		String id = "Anonymous1";
		
		given(memberService.deleteMemberById(id)).willReturn("ok");
		
		// when
		ResultActions resultAction = mockMvc.perform(delete("/member/{id}", id)
				.accept(MediaType.TEXT_PLAIN));
		
		// then
		resultAction
			.andExpect(status().isOk())
			.andDo(MockMvcResultHandlers.print());
		
		MvcResult requestResult = resultAction.andReturn();	
		String result = requestResult.getResponse().getContentAsString();
		
		assertEquals("ok", result);
	}
}
