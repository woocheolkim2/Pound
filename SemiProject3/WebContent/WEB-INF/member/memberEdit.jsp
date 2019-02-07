<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String ctxPath = request.getContextPath(); %>  
<style type="text/css">
	form.form-horizontal div.form-group {align-content: center;}
	.mybtn{
	      float:left; 
	      vertical-align: middle; 
	      border: 0px solid gray; 
	       
	      border-radius: 5px; 
	      font-size: 0.7em; 
	      color:white; 
	      margin-left: 3%; 
	      padding: 1% 2%; 
	      margin-top: 2.2%;
	}
</style>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script type="text/javascript" src="<%= ctxPath %>/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">

   $(document).ready(function(){
      
      var now = new Date();
      
      console.log(now.getFullYear());
      
      $(".error").hide();
      $("#error_passwd").hide();
      $("#name").focus();      
                 
      $(".requiredInfo").each(function(){
         
         $(this).blur(function(){
            
            var data = $(this).val().trim();
            
            if(data == "") {
               // 입력하지 않거나 공백만 입력했을 경우
               $(this).parent().find(".error").show();
              
            }
            else {
               // 공백이 아닌 글자를 입력한 경우
               $(this).parent().find(".error").hide();
              
            }
         });// end of $(this).blur(function())----------------------
         
      });// end of $(".requiredInfo").each()-----------------------------------
      
      $("#userid").bind("keyup", function(){
         alert("아이디 중복확인을 하세요!!");
         $(this).val("");
      });  // end of  $("#userid").bind()---------------------------
      
      // *** ID 중복확인을 하기 위한 팝업창 띄우기 ***
      $("#idcheck").click(function(){
         
         // 팝업창 띄우기
         var url = "idDuplicateCheck.do";
         window.open(url, "idcheck",
                        "left=500px, top=100px, width=300px, height=300px");
         // 기본적으로 아무 조건없이 그냥 어떤 창을 띄우면 method는 GET 방식으로 움직인다.
      });  // end of $("#idcheck").click()-----------------
      
      $("#pwd").blur(function(){
         var passwd= $(this).val();
         
         //-- 숫자/문자/특수문자/ 포함 형태의 8~15자리 이내의 암호 정규식 2
         var regExp_PW = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g;
         // 또는      
         // var regExp_PW = new RegExp(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g);
         
         var bool = regExp_PW.test(passwd);
         
         if(!bool) {
            $("#error_passwd").show();
           
         }
         else {
            $("#error_passwd").hide();
            
         }
      });// end of $("#pwd").blur(function())-----------------------------------------------------
      
      
      
      $("#pwdcheck").blur(function(){
         var passwd = $("#pwd").val();
         var passwdCheck = $(this).val();
         
         if(passwd != passwdCheck) {
            $(this).parent().find(".error").show();
           
         }
         else {
            $(this).parent().find(".error").hide();
           
         }
      });// end of $("#pwdcheck").blur(function())------------------------------------------------
      
      
      
      $("#email").blur(function(){
         var email = $(this).val();
         var regExp_email = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i; 
         
         var bool = regExp_email.test(email);
         
         if(!bool) {
            $(this).parent().find(".error").show();
           
         }
         else {
            $(this).parent().find(".error").hide();
           
         }
      });// end of $("#email").blur(function())------------------------------------------------
      
      
      
      $("#hp2").blur(function(){
         var hp2 = $(this).val();
         
         var bool1 = false;
         var regExp_HP2a = /[1-9][0-9][0-9]/g; // /\d{3,4}/g;
         // 3글자가 숫자라면 들어오도록 검사해주는 정규표현식
         var bool1 = (hp2.length == 3) && regExp_HP2a.test(hp2);
         
         var bool2 = false;
         var regExp_HP2b = /[0-9][0-9][0-9][0-9]/g;
         // 숫자 4자리만 들어오도록 검사해주는
         var bool2 = (hp2.length == 4) && regExp_HP2b.test(hp2);
         
         if(!(bool1 || bool2)){
            $(this).parent().find(".error").show();
            
         }
         else {
            $(this).parent().find(".error").hide();
         
         }
         
      });// end of $("#hp2").blur(function())------------------------------------------------
      
      
      
      $("#hp3").blur(function(){
         var hp3 = $(this).val();
          var regExp_HP3 = /\d{4}/g;
          // 숫자 3자리 또는 4자리만 들어오도록 검사해주는 
          
          var bool = regExp_HP3.test(hp3);
          
          if(!bool){
             $(this).parent().find(".error").show();
            
          }
          else {
             $(this).parent().find(".error").hide();
            
          }
          
                  
      }); // end of $("#hp3").blur(function())------------------------------------------------
      
      $("#zipcodeSearch").click(function(){
         new daum.Postcode({
           oncomplete: function(data) {
               $("#post1").val(data.postcode1);  // 우편번호의 첫번째 값     예> 151
               $("#post2").val(data.postcode2);  // 우편번호의 두번째 값     예> 019
               $("#addr1").val(data.address);    // 큰주소                         예> 서울특별시 종로구 인사로 17 
               $("#addr2").focus();
           }
        }).open();
      });
      
      $(".address").blur(function(){
         var address = $(this).val().trim();
         
         if(address == "") {
           $(this).parent().find(".error").show();
             
         }
         else {
           $(this).parent().find(".error").hide();
             
         }
      });
      
      
   });// end of $(document).ready(function()}-----------------------------------------------
   
   function goRegister(event) {
            
      var frm = document.editFrm;
      frm.method = "POST";
      frm.action = "memberEditEnd.do";
      frm.submit();
   }
</script> 
<form class="form-horizontal" name="editFrm">
<fieldset> 
<div style="border:1px solid lightgray; width: 40%; padding: 2% 0; margin: 0 auto; border-radius:3%;">
<!-- Text input-->
<div class="form-group">
  <label class="col-md-4 control-label" for="textinput">성명</label>  
  <div class="col-md-4">
  <input type="text" name="username" id="name" class="form-control requiredInfo input-md" value="${loginMember.username}" style="width:70%;" readonly />
  </div>
</div>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-4 control-label" for="textinput">아이디</label>  
  <div class="col-md-4">
  <input type="text" name="userid" id="userid" class="form-control requiredInfo input-md" value="${loginMember.userid}" style="float:left; width:60%;" readonly />&nbsp;&nbsp; 
  </div>
</div>

<!-- Password input-->
<div class="form-group">
  <label class="col-md-4 control-label" for="passwordinput">비밀번호</label>
  <div class="col-md-4">
    <input type="password" name="pwd" id="pwd" class="form-control requiredInfo input-md" style="width:70%;" required />
    <span id="error_passwd">암호는 영문자,숫자,특수기호가 혼합된 8~15 글자로만 입력가능합니다.</span>
  </div>
</div>

<!-- Password input-->
<div class="form-group">
  <label class="col-md-4 control-label" for="passwordinput">비밀번호 확인</label>
  <div class="col-md-4">
    <input type="password" id="pwdcheck" class="form-control requiredInfo input-md" style="width:70%;" required />
    <span class="error">암호가 일치하지 않습니다.</span>
  </div>
</div>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-4 control-label" for="textinput">이메일</label>  
  <div class="col-md-4">
  <input type="text" name="email" id="email" class="form-control requiredInfo input-md" value="${loginMember.email}" style="width:70%;" placeholder="abc@gmail.com" />
  <span class="error">이메일 형식에 맞지 않습니다.</span>  
  </div>
</div>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-4 control-label" for="textinput">전화번호</label>  
  <div class="col-md-4">
  <select name="hp1" id="hp1"class="form-control" style="width: 30%; float: left; padding: 8px;">
               <option value="010" selected>010</option>
               <option value="011">011</option>
               <option value="016">016</option>
               <option value="017">017</option>
               <option value="018">018</option>
               <option value="019">019</option>
  </select>               
  <p style="float:left;padding-top:1%;">&nbsp;-&nbsp;</p><input type="text" id="hp2" name="hp2" value="${loginMember.hp2}" size="6" maxlength="4" style="width: 29%; float: left; height: 35px;" class="form-control requiredInfo input-md">
  <p style="float:left;padding-top:1%; ">&nbsp;-&nbsp;</p><input type="text" id="hp3" name="hp3" value="${loginMember.hp3}" size="6" maxlength="4" style="width: 29%; float: left; height: 35px;" class="form-control requiredInfo input-md">
  <span class="error error_hp">휴대폰 형식이 아닙니다.</span>
  </div>
</div>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-4 control-label" for="textinput">우편번호</label>  
  <div class="col-md-4">
  <input type="text" id="post1" name="post1" size="6" maxlength="3" class="form-control requiredInfo input-md" value="${loginMember.post1}" style="width: 30%; float: left; height: 35px;">
  <p style="float:left">&nbsp;-&nbsp;</p>
  <input type="text" id="post2" name="post2" size="6" maxlength="3" class="form-control requiredInfo input-md" value="${loginMember.post2}" style="width: 30%; float: left; height: 35px;"/>&nbsp;&nbsp;
  <!-- 우편번호 찾기 -->
  <input type="button" id="zipcodeSearch" class="mybtn" style="float:left; width:28%; height: 30px;" value="우편번호찾기" />
  <span class="error error_post">우편번호 형식이 아닙니다.</span>
  </div>
</div>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-4 control-label" for="textinput">주소</label>  
  <div class="col-md-4">
   <input type="text" id="addr1" class="form-control address requiredInfo input-md" name="addr1" value="${loginMember.addr1}" size="50" maxlength="80" style="float:left; margin: 2% 0;"/>
   <input type="text" id="addr2" class="form-control address requiredInfo input-md" name="addr2" value="${loginMember.addr2}" size="50" maxlength="80" style="float:left; margin: 2% 0;"/>
  <span class="error">주소를 입력하세요.</span>  
  </div>
</div>


</fieldset>
</form>

<!-- Button -->
<div class="input-md" style="height: 35px; margin: 2% 0; padding-left: 4%" align="center">
  <label class="col-md-4 control-label" for="singlebutton"></label>
  <div class="col-md-4" >
    <button id="singlebutton" name="singlebutton" class="btn btn-primary" style="align:center;background-color:gray; border:none; width: 40%;" onClick="goRegister(event);">수정</button>
    <button id="singlebutton" name="singlebutton" class="btn btn-primary" style="align:center;background-color:gray; border:none; width: 40%;" onClick="goCancel(event);">취소</button>
  </div>
</div>
</div>


         
         
         