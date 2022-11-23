package net.scit.studyroom.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.scit.studyroom.domain.MemberRepository;

/**
 * 단위테스트 (Service와 관련된 애들만 메모리에 띄우면 됨)
 * MemberRepository => 가짜 객체로 만들 수 있음
 * 
 * @author minkuk
 *
 */
@ExtendWith(MockitoExtension.class)
public class MemberServiceUnitTest {

	@InjectMocks // MemberService객체가 만들어질 때 해당 파일에 @Mock으로 등록된 모든 애들을 주입받는다.
	private MemberService memberService;
	
	@Mock
	private MemberRepository memberRepository;
	
	
}
