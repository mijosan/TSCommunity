package com.dodge.board.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
public class Board {
	@Id
	@GeneratedValue
	private Long seq;
	
	private String title;
	private String content;
	private String writer;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	private Date createDate = new Date();
	
	@Column(updatable = false)
	private Long cnt = 0L;
	
	private Long likeCnt;
	private String filename;
	private String originalFileName;
	private String filesize;
}
