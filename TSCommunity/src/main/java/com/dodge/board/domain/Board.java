package com.dodge.board.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
public class Board {
	@Id
	private Long seq;
	
	private String title;
	
	@Column(columnDefinition="LONGTEXT")
	private String content;
	private String writer;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	private Date createDate = new Date();
	
	private Long cnt = 0L;
	
	private String fileName;
	private String originalFileName;
	private String fileSize;
	
	private Long originNo;
	private Long groupOrd;
	private Long groupLayer;
	
	private Long likeCnt;
	
	private String newDate;
	
	
}
