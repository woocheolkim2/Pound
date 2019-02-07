<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String ctxPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>${mvo.username}의 정보</title>
		<%-- Link CSS --%>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="<%= ctxPath %>/jquery-ui-1.11.4.custom/jquery-ui.min.css" />
		<link rel="stylesheet" type="text/css" href="<%= ctxPath %>/jquery-ui-1.11.4.custom/jquery-ui.min.css" /> 
		<link rel="stylesheet" type="text/css" href="<%= ctxPath %>/css/style.css" />
		
		<%-- Link JS --%> 
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> 
		<script type="text/javascript" src="<%= ctxPath %>/jquery-ui-1.11.4.custom/jquery-ui.min.js"></script>  
		
		
		<%-- My CSS --%>
		<style type="text/css">
			html, body{
				margin: 0;
				padding: 0;
			}
			h2{
				text-align:center;
			}
			button{
				display:block;
				width:auto;
				margin:0 auto;
			}
		</style>
	</head>
	<body>
		<h2>:::${mvo.username }님의 정보:::</h2>
		<div id="memberInfo">
			<p>1. 아이디 : ${mvo.userid }</p>
			<p>2. 성   명 : ${mvo.username }</p>
			<p>3. 이메일 : ${mvo.email }</p>
			<p>4. 연락처 : ${mvo.allHp }</p>
			<p>5. 주   소 : ${mvo.allAddr }</p>
			<p>6. 우편번호 : ${mvo.allPost }</p>
			<p>7. 성   별 : ${mvo.showGender }</p>
			<p>8. 포인트 : ${mvo.point }</p>
			<p>9. 가입날짜 : ${mvo.registerdate }</p>
			<p>10. 생   일 : ${mvo.allBirthday }</p>
			<button type="button" onClick="javascript:window.close();">닫기</button>  
		</div>
	</body>
</html>