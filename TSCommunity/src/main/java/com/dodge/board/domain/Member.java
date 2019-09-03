package com.dodge.board.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;

import lombok.Data;

@Data
@Entity
public class Member {
	@Id
	@Column(name="MEMBER_ID")
	private String id;
	
	private String password;
	
	@Column(updatable = false)
	private String email;
	
	@Enumerated(EnumType.STRING)
	@ColumnDefault("'ROLE_MEMBER'")
	@Column(insertable = false, updatable = false)
	private Role role;
}
