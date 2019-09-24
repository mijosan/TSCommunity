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
public class Comment {
	
	@Id
	private Long c_seq;
	
	private Long b_seq;
	
	@Column(columnDefinition="LONGTEXT")
	private String c_content;
	
	private String c_writer;
	
<<<<<<< HEAD

=======
>>>>>>> refs/remotes/origin/master
	@Column(updatable = false)
	private String c_createDate;

	private Long originNo;
	private Long groupOrd;
	private Long groupLayer;
	
}
