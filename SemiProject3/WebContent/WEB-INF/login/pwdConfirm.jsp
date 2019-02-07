<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<%
    String ctxPath = request.getContextPath();
    //    /MyMVC
%>    
    
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%= ctxPath %>/css/style.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


<style>
   #div_pwd {
      width: 70%;
      height: 15%;
      margin-bottom: 5%;
      margin-left: 10%;
      position: relative;
   }
   
   #div_pwd2 {
      width: 70%;
      height: 15%;
      margin-bottom: 5%;
      margin-left: 10%;
      position: relative;
   }
   
   #div_findResult {
      width: 70%;
      height: 15%;
      margin-bottom: 5%;
      margin-left: 10%;      
      position: relative;
   }
   
   #div_btnUpdate {
      width: 70%;
      height: 15%;
      margin-bottom: 5%;
      margin-left: 10%;
      position: relative;
   }
   
</style>

<script type="text/javascript">
   
   $(document).ready(function(){
      
      $("#btnUpdate").click(function(event){
         
         var pwd = $("#pwd").val();
         var pwd2 = $("#pwd2").val();
         
         var regexp_passwd = new RegExp(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g); 
         /* 암호는 숫자,영문자,특수문자가 포함된 형태의 8~15글자 이하만 허락해주는 정규표현식 객체 생성 */
         
         var bool = regexp_passwd.test(pwd);
         /* 암호 정규표현식 검사를 하는 것 
               정규표현식에 만족하면 리턴값은 true,
               정규표현식에 틀리면 리턴값은 false */
               
         if(!bool) {
            alert("암호는 8글자 이상 15글자 이하에 영문자, 숫자, 특수기호가 혼합되어야 합니다."); 
            $("#pwd").val("");
            $("#pwd2").val("");
            event.preventDefault();
            return;
         }   
         else if(pwd != pwd2) {
            alert("암호가 일치하지 않습니다.");
            $("#pwd").val("");
            $("#pwd2").val("");
            event.preventDefault();
            return;
         }
         else {
        	 var frm = document.pwdConfirmFrm;
            frm.method = "POST";
            frm.action = "<%= ctxPath %>/pwdConfirm.do";
            frm.submit();   
         }
      });
            
   });
   
</script>

<div style="width:50%; margin-left:25%;">
	<form name="pwdConfirmFrm" style="margin-top:20%;">
		<div class="row">
		
	   <c:if test="${!method.equals('POST') && n!=1 }">
	   <div id="div_pwd" class="col-md-4">
	      <span style="color: black; font-size: 0.9em; font-weight: bold;">새암호</span><br/> 
	      <input type="password" name="pwd" id="pwd" size="15" placeholder="PASSWORD" style="width:100%; margin-bottom:5%;" required />
	   </div>
	   
	   <div id="div_pwd2" class="col-md-4">
	        <span style="color: black; font-size: 0.9em; font-weight: bold;">새암호확인</span><br/>
	      <input type="password" name="pwd2" id="pwd2" size="15" placeholder="PASSWORD" style="width:100%; margin-bottom: 10%;" required />
	   </div>
	   
	   <input type="hidden" name="userid" id="userid" value="${userid}"/>
	   </c:if>
	   <c:if test="${method.equals('POST') && n==1 }">
	         <div id="div_confirmResult" class="col-md-4">
	            <p style="font-weight: bold;">${userid}님의 암호가 새로이 변경되었습니다.<br/></p>
	         </div>
	   </c:if>
	   
	   <c:if test="${method.equals('GET')}">
	      <div id="div_btnUpdate" class="col-md-4">
	            <button type="button" class="btn btn-success" style="width:100%; background-color:gray;border:none;" id="btnUpdate">암호변경하기</button>
	      </div>      
	   </c:if>   
	   </div>
	</form>
</div>
    