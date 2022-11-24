package net.scit.studyroom.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.scit.studyroom.domain.Member;
import net.scit.studyroom.domain.MemberRepository;
import net.scit.studyroom.dto.MemberDTO;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	
	@Transactional // 트랜잭션 처리
	public Member addMember(MemberDTO memberDTO) {
		Member member = Member.builder(memberDTO).build();
		member = memberRepository.save(member);
		return member;
	}
	
	@Transactional(readOnly=true) // readOnly=true : 성능향상
	public List<Member> findAllMember(){
		return memberRepository.findAll();
	}
	
	// Optional : NPE(Null Point Exception)가 발생하지 않도록 감싸주는 wrapper 클래스
	@Transactional(readOnly=true)
	public Member findMemberById(String id) {
		return memberRepository.findById(id) // return Optional<Member> 
				.orElseThrow(()->new IllegalArgumentException("id를 확인해주세요.")); // orElseThrow() : Optional 예외처리
	}
	
	@Transactional
	public Member updateMember(String id, MemberDTO memberDTO) {
		// update는 Dirty Checking방식으로 진행
		// Dirty Checking : 상태가 변화한 엔티티를 검사 -> 트랜잭션이 끝났을때 변화가 있다면, 변화한 모든 엔티티를 DB에 반영(flush 즉, 업데이트)
		//					, 단 영속성 컨텍스트가 관리하는 엔티티에만 적용 (JPA 영속성 컨텍스트 참고)
		Member memberEntity = memberRepository.findById(id)
				.orElseThrow(()->new IllegalArgumentException("id를 확인해주세요."));
		memberEntity.setName(memberDTO.getName());
		memberEntity.setPasswd(memberDTO.getPasswd());
		// Dirty Checking을 했기 때문에 update문을 따로 호출하지 않았어도 update됨(jpa hibernate가 update해줌)
		return memberEntity;
	}
	
	@Transactional
	public String deleteMemberById(String id) {
		memberRepository.deleteById(id); // return type이 void이지만, 오류가 터지면 자동으로 exception으로 올라감
		return "deleteMember ok";
	}
}
