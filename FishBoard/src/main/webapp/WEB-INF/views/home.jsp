<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@include file="include/header.jsp"%>
<section class="content">
	<div class="box" align="center">
		
		
		<h3>메인페이지</h3>
		현재 위치:
		<!-- 주소 불러오는 영역 -->
		<div class="box-header with-border" id="addressdisp" align="center">
		</div>
		</div>
	</div>
</section>
<%@include file="include/footer.jsp"%>

<script>
	
<%-- 30초를 주기로 동작을 수행하는 타이머 생성 --%>
	setInterval(function() {
	<%-- 현재 위치 정보를 가져오기 위한 HTML5 API  --%>
	navigator.geolocation.getCurrentPosition(function(position) {
			var coords = position.coords;
			<%-- 위도와 경도를 하나의 문자열로 생성 --%>
			var param = coords.latitude + ":" + coords.longitude;
			<%-- jquery에서 ajax 요청 --%>
			$.ajax({
				url : "address",
				data : {
					"param" : param
				},
				dataType : "json",
				success : function(data) {
					document.getElementById("addressdisp").innerHTML = data.address
				}
			});
		});
	}, 10000);
</script>


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