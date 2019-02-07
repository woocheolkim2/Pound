<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String ctxPath = request.getContextPath(); %>  
<jsp:include page="../header.jsp"/>  
<style type="text/css">
.input-md{height: 35px;}
.form-group {align-content: center;}
.mybtn{
      float:left; 
      vertical-align: middle;      
      border-radius: 5px; 
      font-size: 0.7em; 
      color:white; 
      margin-left: 3%; 
      padding: 1% 2%; 
      margin-top: 1%;
      border:none;
      background-color: gray;
}
</style>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script type="text/javascript" src="<%= ctxPath %>/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">

   $(document).ready(function(){
      var allcheck = false;
      var now = new Date();
      console.log(now.getFullYear());
      $(".error").hide();
      $("#error_passwd").hide();
      $("#name").focus();
      $("#userid").blur(function(){
         var userid = $("#userid").val();
         if(userid.length<5){
            alert("아이디는 5글자 이상으로 입력해 주세요");
            return;
         }
        // ajax를 통해 아이디 중복검사 blur이벤트 발생시 바로 출력해주기
         var form_data = {userid:$("#userid").val()};
         $.ajax({
            url:"idDuplicateCheck.do",
            type:"GET",
            data: form_data,
            dataType:"html",
            success:function(html){
               $("#idcheckajax").empty();
               $("#idcheckajax").append(html);
            },
            error:function(){
               //
            }
         });
      });  // end of $("#idcheck").click()-----------------
      
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
      
      $("#singlebutton").click(function(){
         var flag = false;
          $(".requiredInfo").each(function(){
             var data = $(this).val().trim(); 
             if(data==""){
                flag=true;
                return false;
             }
          });
          if(flag==true){
             alert("필수 정보를 입력해 주세요.");
             return;
          }
          // ------- 암호 유효성 체크
          var passwd= $("#pwd").val();  //-- 숫자/문자/특수문자/ 포함 형태의 8~15자리 이내의 암호 정규식 2
          var regExp_PW = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g; // 또는   var regExp_PW = new RegExp(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g);
          var bool_pwd = regExp_PW.test(passwd);
          var passwdCheck= $("#pwdcheck").val();  //-- 숫자/문자/특수문자/ 포함 형태의 8~15자리 이내의 암호 정규식 2
          var bool_pwd2 = regExp_PW.test(passwdCheck);
          if(!(bool_pwd||bool_pwd2)) {
             alert("암호는 영문자,숫자,특수기호가 혼합된 8~15 글자로만 입력가능합니다.");
             return;
          }
           // ------- 암호 확인 체크
          if(passwd != passwdCheck){
             alert("암호를 동일하게 입력해 주세요");
            return;
          }
          // ---   이메일 유효성 체크
          var email = $("#email").val();
          var regExp_email = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i; 
          var bool_email = regExp_email.test(email);
          if(!bool_email) {
             alert("이메일 형식에 맞게 압력해주세요");
             return;
          }
          // ------ 핸드폰번호 숫자 유효성 체크
          var hp2 = $("#hp2").val();
          var regExp_HP2a = /[1-9][0-9][0-9]/g; // /\d{3,4}/g; // 3글자가 숫자라면 들어오도록 검사해주는 정규표현식
          var bool1 = (hp2.length == 3) && regExp_HP2a.test(hp2);
          var regExp_HP2b = /[0-9][0-9][0-9][0-9]/g; // 숫자 4자리만 들어오도록 검사해주는
          var bool2 = (hp2.length == 4) && regExp_HP2b.test(hp2);
          var hp3 = $("#hp3").val();
          var regExp_HP3 = /\d{4}/g; // 숫자 3자리 또는 4자리만 들어오도록 검사해주는 
          var bool3 = regExp_HP3.test(hp3);
          if(!(bool1||bool2||!bool3)) {
             alert("핸드폰 번호는 숫자로만 입력해 주세요");
              return;
          }
         var userid = $("#userid").val();
         if(userid.length<5){
            alert("아이디는 5글자 이상 되어야 합니다.");
            return;
         }
          var isUSEuserid = $("#ajaxUSEuserid").val();
          if(isUSEuserid=="false"){
             alert("이미 사용중인 아이디 입니다. 다른아이디로 가입을 진행해 주세요.");
             return;
          }
          else goRegister();
      });
   });// end of $(document).ready(function()}-----------------------------------------------
   
   function goRegister() {  
      var frm = document.registerFrm;
      frm.method = "POST";
      frm.action = "registerEnd.do";
      frm.submit();
   }
</script> 

<fieldset>
<h2 style="font-weight: bold; color:gray; text-align: center;margin-top:2%;">Join</h2>
<div style="border:1px solid lightgray; max-width: 750px; min-width: 400px; padding: 1%; margin: 2% auto; border-radius: 10px;">
<!-- Text input-->
<p style="font-size: 0.8em;text-align:right;">*표시는 필수 입력 정보입니다.</p>
<form class="form-horizontal" name="registerFrm" style="margin: 1% 0; padding:0;">
   <div class="form-group">
     <label class="col-md-3 control-label" for="textinput">성명*</label>  
     <div class="col-md-8">
        <input type="text" name="username" id="name" class="form-control requiredInfo input-md" style="width:60%;" required />
     </div>
   </div>
   
   <!-- Text input-->
   <div class="form-group">
     <label class="col-md-3 control-label" for="textinput">아이디*</label>  
     <div class="col-md-8">
     <input type="text" name="userid" id="userid" placeholder="5글자 이상으로 입력해 주세요." class="form-control requiredInfo input-md" style="float:left; width:60%;"  required />&nbsp;&nbsp; 
     <div id="idcheckajax" style="width: 45%;float:left; margin-left: 2%; padding-top:2%;"></div>
     </div>
   </div>
   
   <!-- Password input-->
   <div class="form-group">
     <label class="col-md-3 control-label" for="passwordinput">비밀번호*</label>
     <div class="col-md-8">
       <input type="password" name="pwd" id="pwd" placeholder="영문자,숫자,특수기호가 혼합된 8~15글자로 입력해주세요." class="form-control requiredInfo input-md" style="width:60%;" required />
     </div>
   </div>
   
   <!-- Password input-->
   <div class="form-group">
     <label class="col-md-3 control-label" for="passwordinput">비밀번호 확인*</label>
     <div class="col-md-8">
       <input type="password" id="pwdcheck" placeholder="위의 암호와 동일하게 입력해 주세요." class="form-control requiredInfo input-md" style="width:60%;" required />
     </div>
   </div>
   
   <!-- Text input-->
   <div class="form-group">
     <label class="col-md-3 control-label" for="textinput">이메일*</label>  
     <div class="col-md-8">
     <input type="text" name="email" id="email" class="form-control requiredInfo input-md" style="width:60%;" placeholder="abc@gmail.com" />
     </div>
   </div>
   
   <!-- Text input-->
   <div class="form-group">
     <label class="col-md-3 control-label" for="textinput">전화번호*</label>  
     <div class="col-md-8">
     <select name="hp1" id="hp1"class="form-control" style="width: 30%; float: left; padding: 8px;">
                  <option value="010" selected>010</option>
                  <option value="011">011</option>
                  <option value="016">016</option>
                  <option value="017">017</option>
                  <option value="018">018</option>
                  <option value="019">019</option>
     </select>               
     <p style="float:left;padding-top:1%;">&nbsp;-&nbsp;</p><input type="text" id="hp2" name="hp2" size="6" maxlength="4" style="width: 29%; float: left; height: 35px;" class="form-control requiredInfo input-md">
     <p style="float:left;padding-top:1%; ">&nbsp;-&nbsp;</p><input type="text" id="hp3" name="hp3" size="6" maxlength="4" style="width: 29%; float: left; height: 35px;" class="form-control requiredInfo input-md">
     </div>
   </div>
   
   <!-- Text input-->
   <div class="form-group">
     <label class="col-md-3 control-label" for="textinput">우편번호*</label>  
     <div class="col-md-8">
     <input type="text" id="post1" name="post1" size="6" maxlength="3" class="form-control requiredInfo input-md" style="width: 30%; float: left; height: 35px;">
     <p style="float:left">&nbsp;-&nbsp;</p>
     <input type="text" id="post2" name="post2" size="6" maxlength="3" class="form-control requiredInfo input-md" style="width: 30%; float: left; height: 35px;"/>&nbsp;&nbsp;
     <!-- 우편번호 찾기 -->
     <input type="button" id="zipcodeSearch" class="mybtn" value="주소찾기" style="float:left; width:28%; height: 30px;" />
     </div>
   </div>
   
   <!-- Text input-->
   <div class="form-group">
     <label class="col-md-3 control-label" for="textinput">주소*</label>  
     <div class="col-md-8">
      <input type="text" id="addr1" class="form-control address requiredInfo input-md" name="addr1" size="50" maxlength="80" style="float:left; margin: 2% 0;"/>
      <input type="text" id="addr2" class="form-control address requiredInfo input-md" name="addr2" size="50" maxlength="80" style="float:left; margin: 2% 0;"/> 
     </div>
   </div>
   
   <!-- Text input-->
   <div class="form-group">
     <label class="col-md-3 control-label" for="textinput">생년월일*</label>  
     <div class="col-md-8">
     <input type="number" id="birthyyyy" name="birthyyyy" class="form-control input-md" min="1950" max="2050" step="1" value="1995" style="width:30%;float:left; margin-right: 1%;" required />
               
               <select id="birthmm" name="birthmm" class="form-control input-md" style="width:30%;float:left; margin-right: 1%;">
                  <option value ="01">01</option>
                  <option value ="02">02</option>
                  <option value ="03">03</option>
                  <option value ="04">04</option>
                  <option value ="05">05</option>
                  <option value ="06">06</option>
                  <option value ="07">07</option>
                  <option value ="08">08</option>
                  <option value ="09">09</option>
                  <option value ="10">10</option>
                  <option value ="11">11</option>
                  <option value ="12">12</option>
               </select> 
               
               <select id="birthdd" name="birthdd" class="form-control input-md" style="width:30%;float:left; margin-right: 1%;">
                  <option value ="01">01</option>
                  <option value ="02">02</option>
                  <option value ="03">03</option>
                  <option value ="04">04</option>
                  <option value ="05">05</option>
                  <option value ="06">06</option>
                  <option value ="07">07</option>
                  <option value ="08">08</option>
                  <option value ="09">09</option>
                  <option value ="10">10</option>
                  <option value ="11">11</option>
                  <option value ="12">12</option>
                  <option value ="13">13</option>
                  <option value ="14">14</option>
                  <option value ="15">15</option>
                  <option value ="16">16</option>
                  <option value ="17">17</option>
                  <option value ="18">18</option>
                  <option value ="19">19</option>
                  <option value ="20">20</option>
                  <option value ="21">21</option>
                  <option value ="22">22</option>
                  <option value ="23">23</option>
                  <option value ="24">24</option>
                  <option value ="25">25</option>
                  <option value ="26">26</option>
                  <option value ="27">27</option>
                  <option value ="28">28</option>
                  <option value ="29">29</option>
                  <option value ="30">30</option>
                  <option value ="31">31</option>
               </select> 
     </div>
   </div>
   
   <!-- Multiple Radios (inline) -->
   <div class="form-group">
     <label class="col-md-3 control-label" for="radios">성별*</label>
     <div class="col-md-8"> 
       <label class="radio-inline" for="radios">
         <input type="radio" name="gender" id="radios-0" value="1" checked="checked">
         남
       </label> 
       <label class="radio-inline" for="radios">
         <input type="radio" name="gender" id="radios-1" value="2">
         여
       </label>
     </div>
   </div>
</form>
</div>
</fieldset>

<!-- Button -->
<div class="input-md" style="margin: 1% 0;" align="center">
  <div class="col-md-8 col-md-offset-2" >
    <button id="singlebutton" name="singlebutton" class="btn btn-primary" style="align:center;background-color: black; border:none; width: 40%;" >가입하기</button>
  </div>
</div>
<jsp:include page="../footer.jsp" />