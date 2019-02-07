<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%  String ctxPath = request.getContextPath(); %>        
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%= ctxPath %>/css/style.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<script type="text/javascript">
   
   $(document).ready(function(){
         
      $("#btnFind").click(function(){
         var frm = document.pwdFindFrm;
         frm.method = "POST";
         frm.action = "<%= ctxPath %>/pwdFind.do";
         frm.submit();
      });
      
      var method = "${method}";
      var userid = "${userid}";
      var email = "${email}";
      var n = "${n}";
      
      if(method=="POST" && userid != "" && email != "") {
         $("#userid").val(userid);
         $("#email").val(email);
      }
      
      if(method=="POST" && n==1) {
         $("#div_btnFind").hide();
      }
      else if(method=="POST" && (n == -1 || n == 0)) {
         $("#div_btnFind").show();
      }      
      
      $("#btnConfirmCode").click(function(){
      
         var frm = document.verifyCertificationFrm;
         frm.userid.value = $("#userid").val();
         frm.userCertificationCode.value = $("#input_confirmCode").val();
         frm.action = "<%=ctxPath %>/verifyCertification.do";
         frm.method = "POST";
         frm.submit();
      });
   });
   
</script>
<div style="width:40%; margin-left:30%; margin-bottom:0;">
<form name="pwdFindFrm">
	<c:if test="${n==null}">
   <div style="padding-top:20%;"></div>
   </c:if>
   <c:if test="${method=='POST' && n ==-1 ||n==0 }">
   <div style="padding-top:5%;"></div>
   </c:if>
   <div id="div_userid" style="padding-top:2%;">
      <span style="color: black; font-size: 0.9em; font-weight: bold;">아이디</span>&nbsp;
      <input type="text" name="userid" id="userid" size="15" placeholder="ID" style="width:100%; margin-bottom:8%;" required />
   </div>
   
   <div id="div_email">
        <span style="color: black; font-size: 0.9em; font-weight: bold;">이메일</span>&nbsp;
      <input type="text" name="email" id="email" size="15" placeholder="abcd@def.com" style="width:100%; margin-bottom:8%;" required />
   </div>  
   <div id="div_btnFind">
         <button type="button" class="btn btn-success" id="btnFind" style="width:100%; border:none; background-color:gray;">찾기</button>
   </div>
   
   <div id="div_findResult">
         <c:if test="${n == 1}">
            <div id="pwdConfirmCodeDiv" style="width:100%;">
		                인증코드가 ${email}로 발송되었습니다.<br/> 인증코드를 입력해주세요
                <input type="text" name="input_confirmCode" id="input_confirmCode" style="width:100%;" required />
                <br/><br/>
                <button type="button" class="btn btn-info" id="btnConfirmCode" style="width:100%;margin-bottom:0;">인증하기</button>    
            </div>
         </c:if>
         
         <c:if test="${n == 0}">
              <span style="color: red;">사용자 정보가 없습니다.</span>
         </c:if>
         
         <c:if test="${n == -1}">
              <span style="color: red;">${sendFailmsg}</span>
         </c:if>
         
   </div>

</form>
</div>
<form name="verifyCertificationFrm">
   <input type="hidden" name="userid">
   <input type="hidden" name="userCertificationCode">
</form>


    