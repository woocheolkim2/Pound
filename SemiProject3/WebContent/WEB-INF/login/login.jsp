<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="../header.jsp" />
<script type="text/javascript">
   $(document).ready(function(){
       $("#login_pw").keydown(function(event){
            if(event.keyCode == 13){
              goLogin();
            } 
       });
    });// end of $(document).ready()--------------

   function goLogin(){
      var frm = document.loginFrm;
      frm.method="POST";
      frm.action="loginEnd.do";
      frm.submit();
   }
</script>
<style>
@moblie{
	body{max-width: 1920px;}
}
</style>
<center style="margin: 3% auto; min-width: 300px; max-width: 750px;">
<div class="row" style="margin:3%; padding: 5% 0; border: 3px double lightgray; border-radius: 20px;">
	<h2 style="margin-right: 5%;color: gray">Login</h2>
   <div class="col-md-8 col-md-offset-3" style="">
   <form class="form-horizontal" name="loginFrm">
   <!-- Text input-->
   <div class="form-group">
     <label class="col-md-8 control-label" for="ID">ID</label>  
     <div class="col-md-8">
     <input id="ID" name="login_id" type="text" class="form-control input-md" value="${cookieValue }" required>
       
     </div>
   </div>
   
   <!-- Password input-->
   <div class="form-group">
     <label class="col-md-8 control-label" for="login_pw">PW</label>
     <div class="col-md-8">
       <input id="login_pw" name="login_pw" type="password" placeholder="" class="form-control input-md" required>
       
     </div>
   </div>
   <div class="form-group" style="margin:0;">
   	<div class="col-md-8" >
   		<input type="checkbox" id="saveid" name="saveid" style="vertical-align: top;"><label for="saveid"> 아이디 저장</label>
   	</div>
   </div>
   <!-- Button -->
   <div class="form-group">
     <label class="col-md-8 control-label" for="loginbtn"></label>
     <div class="col-md-8">
       <button type="button" id="loginbtn" name="loginbtn" class="btn btn-primary" onclick="goLogin();" style="width: 100%; background-color:gray; border:none;">login</button>
     </div>
   </div>
   <div class="form-group">
     <div class="col-md-8">
       <a style="cursor: pointer;" data-toggle="modal" data-target="#userIdfind" data-dismiss="modal">아이디찾기
       </a> / <a style="cursor: pointer;" data-toggle="modal" data-target="#passwdFind" data-dismiss="modal">비밀번호찾기</a>
     </div>
   </div>
   <div class="form-group">
     <div class="col-md-8" align="center">
		   <a id="kakao-login-btn" style="width: 100%;"></a>
			<script type='text/javascript'>
			  //<![CDATA[
			    // 사용할 앱의 JavaScript 키를 설정해 주세요.
			    Kakao.init('c2d917f16da63c2b5383e5659f66e783');
			    // 카카오 로그인 버튼을 생성합니다.
			    Kakao.Auth.createLoginButton({
			      container: '#kakao-login-btn',
			      success: function(authObj) {
			        // 로그인 성공시, API를 호출합니다.
			        Kakao.API.request({
			          url: '/v2/user/me',
			          success: function(res) {
			        	var frm = document.kakaoLogin;
			        	frm.authObj.value= JSON.stringify(authObj);
			        	frm.res.value= JSON.stringify(res);
			            //{"authObj":{"access_token":"F2LOLpOSnWO8eYCQ3zm6JgJltyiRKR8nsfDWBgo8BNgAAAFnn_aLEw","token_type":"bearer","refresh_token":"RB6pfG-Wy7DsU8XfNNkNV78-WdjhP83S0IlO1Qo8BNgAAAFnn_aLEQ","expires_in":7199,"scope":"profile","refresh_token_expires_in":2591999},
			            //"res":{"id":980209626,"properties":{"nickname":"최현영"},"kakao_account":{"has_email":true,"has_age_range":true,"has_birthday":true,"has_gender":false}}}
			            frm.action="loginEnd.do";
			            frm.method="POST";
			            frm.submit();
			          },
			          fail: function(error) {
			            alert(JSON.stringify(error));
			          }
			        });
			      },
			      fail: function(err) {
			        alert(JSON.stringify(err));
			      }
			    });
			  //]]>
			</script>
		</div>
	</div>
   </form>
   </div>
</div>
</center>
<%-- ****** 아이디 찾기 Modal ****** --%>
<div class="modal fade" id="userIdfind" role="dialog">
  <div class="modal-dialog">
  
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close myclose" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">아이디 찾기</h4>
      </div>
      <div class="modal-body" style="height: 300px; width: 100%;">
        <div id="idFind">
           <iframe style="border: none; width: 100%; height: 280px;" src="<%= request.getContextPath()%>/idFind.do"></iframe>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default myclose" data-dismiss="modal" onClick="javascript:window.location.reload();">Close</button>
      </div>
    </div>
    
  </div>
</div>   


<%-- ****** 비밀번호 찾기 Modal ****** --%>
<div class="modal fade" id="passwdFind" role="dialog">
  <div class="modal-dialog">
  
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close myclose" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">비밀번호 찾기</h4>
      </div>
      <div class="modal-body" style="height: 300px; width: 100%;">
        <div id="pwFind">
           <iframe style="border: none; width: 100%; height: 280px;" src="<%=request.getContextPath()%>/pwdFind.do">  
           </iframe>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default myclose" data-dismiss="modal" onClick="javascript:window.location.reload();">Close</button>
      </div>
    </div>
    
  </div>
</div>
<form name="kakaoLogin">
	<input type="hidden" name="authObj">
	<input type="hidden" name="res">
</form>
<jsp:include page="../footer.jsp" />