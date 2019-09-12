package com.dodge.board.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Recommendation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;
	
	private Long b_seq;
	
	private String id;
	
	private String re;
	
}
