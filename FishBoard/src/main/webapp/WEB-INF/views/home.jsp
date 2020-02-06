<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@include file="include/header.jsp"%>
<section class="content">
	<div class="box">
		<div class="box-header with-border">
		
			<h3>메인페이지</h3>
		</div>
	</div>
</section>
<%@include file="include/footer.jsp"%>

<c:if test="${insert != null }">
	<link rel="stylesheet"
		href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script>
		$(function() {
			$("#dialog-confirm").dialog({
				resizable : false,
				height : "auto",
				width : 400,
				modal : true,
				buttons : {
					"닫기" : function() {
						$(this).dialog("close");
					},
				}
			});
		});
	</script>
</c:if>
<div id="dialog-confirm" title="회원가입" style="display: none">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 12px 12px 20px 0;"></span> 
			회원가입에 성공했습니다. 다시 로그인해주세요.
	</p>
</div>