package com.gmail.fishondesert.domain;

import java.sql.Date;

import lombok.Data;

@Data
public class Board {
	private int bno; 
	private String title;
	private String content; 
	private	String regdate;
	private String updatedate;
	
	//출력을 위한 변수 
	private String dispdate;
	
	private String ip; 
	private String email;
	
	private String nickname;
}
