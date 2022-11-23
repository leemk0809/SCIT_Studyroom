package net.scit.studyroom.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.scit.studyroom.dto.MemberDTO;

@Entity
@Data
@Table(name = "study_member")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(builderMethodName = "MemberBuilder")
public class Member {
	@Id
	private String id;
	@Column(name="passwd", length=100, nullable=false)
	private String passwd;
	@Column(name="name", length=60, nullable=false)
	private String name;
	
	public static MemberBuilder builder(MemberDTO memberDTO) {
		return MemberBuilder()
				.id(memberDTO.getId())
				.name(memberDTO.getName())
				.passwd(memberDTO.getPasswd());
	}
}
