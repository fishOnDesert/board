<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../include/header.jsp"%>
<section class="content">
	<form id="signUp" enctype="multipart/form-data" method="post">
		<p align="center">
			
		<table border="1" width="70%" height="80%" align="center">
				<tr>
					<td colspan="3" align="center">
						<h2>
							회원가입
						</h2>
					</td>
				</tr>
				
				<tr>
					<!-- rowspan : 행을 합치는 것 
					5개의 행을 합쳐서 만들기 -->
					<td rowspan="5" align="center">
						<p></p> 
						<img id="img" width="100" height="100" border="1" /> 
						<br />
						<br /> 
						<input type="file" id="image" name="image" accept=".jpg,.jpeg,.gif,.png" />
						<br />
					</td>
				</tr>
				
				<tr>
					<td bgcolor="#f5f5f5">
						<font size="2">&nbsp;&nbsp;&nbsp;&nbsp;이메일</font>
					</td>
					<td>&nbsp;&nbsp;&nbsp; 
						<!-- HTML5에서 input의 type을 추가된 형태로 설정하면 형식 검사도 수행함
						name : 서버에게 전달할 이름 
						id : 스크립트가 사용할 이름 -->
						<input type="email" name="email" id="email" size="30" maxlength=50 required="required" />
						<div id="emailDiv"></div>
					</td>
				</tr>
				
				<tr>
					<td width="17%" bgcolor="#f5f5f5">
						<font size="2">&nbsp;&nbsp;&nbsp;&nbsp;닉네임</font>
					</td>
					<td>&nbsp;&nbsp;&nbsp; 
						<!-- pattern은 정규식 패턴을 설정해서 유효성 검사 수행 
						title은 유효성 검사에 실패했을 때 보여지는 텍스트인데 브라우저에 잘 적용 안 됨 -->
						<input type="text" name="nickname"
							id="nickname" size="30" pattern="([a-z, A-Z, 가-힣]){2,}" required="required" title="닉네임은 문자 2자 이상입니다." />
						<div id="nicknameDiv"></div>
					</td>
				</tr>
				
				<tr>
					<td bgcolor="#f5f5f5">
						<font size="2">&nbsp;&nbsp;&nbsp;&nbsp;비밀번호</font></td>
					<td>&nbsp;&nbsp;&nbsp; 
						<input type="password" name="pw" id="pw" size="30" required="required" />
						<div id="pwDiv"></div>
					</td>
				</tr>
				<tr>
					<td bgcolor="#f5f5f5">
						<font size="2">&nbsp;&nbsp;&nbsp;&nbsp;비밀번호 확인</font></td>
					<td>&nbsp;&nbsp;&nbsp; 
						<input type="password" id="confirmpw" size="30" required="required" />
						<div id="confirmpwDiv"></div>
					</td>
				</tr>

				<tr>
					<td align="center" colspan="3">
						<p></p> 
							<!-- 부트스트랩에서는 버튼 class를 적용하면 색상이 변경됨 -->
							<input type="submit" value="회원가입" class="btn btn-warning" />
			
							<input type="button" value="메인으로" class="btn btn-success" onclick="javascript:window.location='/'">
						<p></p>
					</td>
				</tr>
				
			</table>

	</form>
</section>
<%@include file="../include/footer.jsp"%>

<script>

	

	//이미지 미리보기
	document.getElementById("image").addEventListener("change", function(e){
		//파일 선택 여부 확인
		if(this.files && this.files[0]){
			//파일 이름 가져오기
			var filename = this.files[0].name; 
			//파일의 내용을 읽는 객체 생성 
			var reader = new FileReader();
			reader.readAsDataURL(this.files[0])
			reader.addEventListener("load", function(e){
				//읽은 내용을 img 태그에 출력
				document.getElementById("img").src = e.target.result;
			})
		}
	})
	
	//email 중복검사
	//이메일 중복검사 통과여부를 저장할 변수를 생성 
	var emailcheck = false;
	var emailDiv = document.getElementById("emailDiv");
	//email 입력 란에서 포커스가 떠나면 
	document.getElementById("email").addEventListener("focusout", function(e){
		var email = document.getElementById("email").value;
		if(email.replace(/\s/gi, "").length > 0){
			$.ajax({
				url:"emailcheck",
				data:{"email":email.replace(/\s/gi, "")},
				dataType:"json",
				success:function(data){
					if(data.result == true){
						emailDiv.innerHTML = "사용 가능한 이메일입니다"
						emailDiv.style.color = "green"
						emailcheck = true
					}else{
						emailDiv.innerHTML = "사용 불가능한 이메일입니다"
						emailDiv.style.color = "red"
						emailcheck = false
					}
				}
			})
		}
	})

	//nickname 중복검사 
	var nicknamecheck = false;
	var nicknameDiv = document.getElementById("nicknameDiv");
	
	document.getElementById("nickname").addEventListener("focusout", function(e){
		var nickname = document.getElementById("nickname").value;
		if(nickname.replace(/\s/gi, "").length > 0){
			$.ajax({
				url:"nicknamecheck",
				data:{"nickname":nickname.replace(/\s/gi, "")}, 
				dataType:"json", 
				success:function(data){
					if(data.result == true){
						nicknameDiv.innerHTML = '사용 가능한 닉네임 입니다'
						nicknameDiv.style.color = 'green'
						nicknamecheck = true
					}else{
						nicknameDiv.innerHTML = '사용 불가능한 닉네임 입니다'
						nicknameDiv.style.color = 'red'
						nicknamecheck = false
					}
				}
			})			
		}
	})
	
	//비밀번호 유효성 검사
	var number = /[0-9]/; // 숫자
	var upper = /[A-Z]/; // 대문자
	var lower = /[a-z]/;
	var charMap = /[~!@#$%^&*()_+|<>?:{}]/;// 특수문자
	
	document.getElementById("pw").addEventListener("focusout", function(e){
		var pw = document.getElementById("pw").value;
		if(!number.test(pw) || !upper.test(pw) || !lower.test(pw) || !charMap.test(pw) || pw.length < 8) {
			document.getElementById("pwDiv").innerHTML = "비밀번호는 8자리 이상의 대소문자, 숫자, 특수문자로 구성하여야 합니다";
			document.getElementById("pwDiv").style.color='red'; 
			e.preventDefault();
		}else if(number.test(pw) || upper.test(pw) || lower.test(pw) || charMap.test(pw) || pw.length > 7){
			document.getElementById("pwDiv").innerHTML = "안전한 비밀번호 입니다";
			document.getElementById("pwDiv").style.color='green'; 
			e.preventDefault();
		}
	})

	//비밀번호 동일여부 검사  
	var pwconfirm = document.getElementById("confirmpw");

	pwconfirm.addEventListener("focusout", function(e){
		if(pw.value != pwconfirm.value){
			document.getElementById("confirmpwDiv").innerHTML = "비밀번호가 같지 않습니다";
			document.getElementById("confirmpwDiv").style.color='red'; 
			e.preventDefault();
		}else if(pw.value == pwconfirm.value){
			document.getElementById("confirmpwDiv").innerHTML = "동일한 비밀번호 입니다";
			document.getElementById("confirmpwDiv").style.color='green'; 
			e.preventDefault();
		}
	})
	
	
	
	
		
</script>
