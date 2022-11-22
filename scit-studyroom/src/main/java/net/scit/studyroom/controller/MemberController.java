package net.scit.studyroom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.scit.studyroom.dto.MemberDTO;
import net.scit.studyroom.service.MemberService;

@RequiredArgsConstructor
@RestController
public class MemberController {

	private final MemberService memberService;
	
	@PostMapping("/Member")
	public ResponseEntity<?> save(@RequestBody MemberDTO memberDTO){
		return new ResponseEntity<>(memberService.addMember(memberDTO), HttpStatus.OK); // 200, 브라우저가 인식할 상태코드
	}
}
