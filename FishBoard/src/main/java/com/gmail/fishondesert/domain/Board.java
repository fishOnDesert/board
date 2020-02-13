package com.gmail.fishondesert.domain;

import lombok.Data;

@Data
public class Board {
	private int bno;
	private String title; 
	private String content; 
	private int readcnt; 
	//원래 자료형은 Date지만 사용을 편리하게 하기 위해 String으로 변환
	private String regdate; 
	private String updatedate;
	//출력을 위한 변수 
	private String dispdate;
	
	private String ip;
	private String email;
	
	private String nickname;
	
}
