<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spring MVC Board</title>
<meta
	content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no'
	name='viewport'>
<!-- Bootstrap 3.3.4 -->
<link
	href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.comrespond/1.4.2respond.min.js"></script>
    <![endif]-->

</head>
<!-- jQuery 2.1.4 -->
<script src="${pageContext.request.contextPath}/resources/jquery/jQuery-2.1.4.min.js"></script>
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
				<li role="presentation"><a href="/user/signUp">회원가입 </a></li>
									
			</ul>
		</section>
	</aside>

