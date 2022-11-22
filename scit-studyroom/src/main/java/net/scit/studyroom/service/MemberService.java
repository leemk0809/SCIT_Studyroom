package net.scit.studyroom.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.scit.studyroom.domain.Member;
import net.scit.studyroom.domain.MemberRepository;
import net.scit.studyroom.dto.MemberDTO;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	
	public Member addMember(MemberDTO memberDTO) {
		
		Member member = Member.builder(memberDTO).build();
		
		//member = memberRepository.save(member);
		return member;
	}
}
