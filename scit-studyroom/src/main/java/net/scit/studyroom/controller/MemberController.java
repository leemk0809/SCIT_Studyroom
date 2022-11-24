package net.scit.studyroom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.scit.studyroom.dto.MemberDTO;
import net.scit.studyroom.service.MemberService;

@RequiredArgsConstructor
@RestController
public class MemberController {

	private final MemberService memberService;
	
	// ResponseEntity : return 할 때, http status를 같이 보낼 수 있음.	
	// HttpStatus = 브라우저가 인식할 상태코드
	// @PostMapping vs @PutMapping : 멱등성을 가지는 여부, post는 멱등성을 가지지 않고, put은 가진다.
	// 멱등성(idempotent;ベキ等元) : 동일한 요청을 한번 보내는 것과 여러번 보내는 것이 같은 효과를 가지고, 서버의 상태도 동일하게 남는것
	// 멱등성 참고(https://developer.mozilla.org/ko/docs/Glossary/Idempotent)
	
	// @PostMapping : 여러번 호출하면 여러열을 가짐
	@PostMapping("/member")
	public ResponseEntity<?> save(@RequestBody MemberDTO memberDTO){
		return new ResponseEntity<>(memberService.addMember(memberDTO), HttpStatus.CREATED); // 201, 새로운 리소스 생성
	}
	
	// @GetMapping : 여러번 호출해도 클라이언트가 받는 응답은 동일, 데이터를 가져올때만 사용해야 함
	@GetMapping("/member")
	public ResponseEntity<?> findAll(){
		return new ResponseEntity<>(memberService.findAllMember(), HttpStatus.OK); // 200, OK
	}
	
	@GetMapping("/member/{id}")
	public ResponseEntity<?> findById(@PathVariable String id){
		return new ResponseEntity<>(memberService.findMemberById(id), HttpStatus.OK);
	}
	
	// @PutMapping : 여러번 호출해도 클라이언트가 받는 응답은 동일, 대상 리소스를 나타내는 데이터를 대체함
	@PutMapping("/member/{id}")
	public ResponseEntity<?> update(@PathVariable String id, @RequestBody MemberDTO memberDTO){
		return new ResponseEntity<>(memberService.updateMember(id, memberDTO), HttpStatus.OK);
	}
	
	// @DeleteMapping : 1번만 HttpSatus값 200을 보내고 그다음부턴 404를 보냄
	@DeleteMapping("/member/{id}")
	public ResponseEntity<?> deleteById(@PathVariable String id){
		return new ResponseEntity<>(memberService.deleteMemberById(id), HttpStatus.OK);
	}
	
}
