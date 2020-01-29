<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<!-- jstl의 core 기능을 사용하기 위한 설정 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spring MVC Board</title>
<!-- 모바일에서 접속했을 때 화면의 너비를 디바이스의 크기만큼으로 출력하기 위한 설정
스크롤바 생기기 않고 화면 크기에 맞게 나옴 -->
<meta
	content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no'
	name='viewport'>
	
<!-- 부트스트랩을 사용하기 위한 설정 -->
<link href="/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet"
	type="text/css" />
<!-- IE9 이하 버전에서 접속했을 때 HTML5의 semantic 태그를 인식하도록 하는 설정
조금 후부터는 이 설정을 안 해도 됨 -->
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.comrespond/1.4.2respond.min.js"></script>
<![endif ]-->
</head>
<script src="/resources/jquery/jquery.min.js"></script>
<body class="skin-blue sidebar-mini">
	<div class="wrapper">
		<header class="main-header">
			<div class="page-header">
				<h1>Spring MVC 게시판</h1>
			</div>
		</header>
	</div>
	<aside class="main-sidebar">
		<section class="sidebar">
			<ul class="nav nav-tabs">
				<li role="presentation" class="active"><a href="/">메인</a></li> <!-- 어느 페이지에서든 /로 쓰면 메인으로 감 -->
				<li role="presentation"><a href="/board/list">목록보기 </a></li>
					
				<c:if test="${user == null }">
					<li role="presentation"><a href="/user/login">로그인 </a></li>
					<li role="presentation"><a href="/user/register">회원가입 </a></li>
				</c:if>
				<c:if test="${user != null }">
					<li role="presentation"><a href="/board/register">게시물 쓰기 </a></li>
		
					<div align="right">
							<span class="badge"> 
								<img src="/userimage/${user.image}" width="30px" />
							</span> 
							Welcome! ${user.nickname}&nbsp;&nbsp; 
							<a href="user/logout"> Logout </a>
							<a>&nbsp;&nbsp;</a>
						</div>
				</c:if>
				
			</ul>
		</section>
	</aside>
	<div>