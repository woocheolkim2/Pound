<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String ctxPath = request.getContextPath();
%>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%= ctxPath %>/css/style.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript">
   
   $(document).ready(function(){
       
      var method = "${method}";
      // console.log("method:"+method);
      
      if(method == "GET"){
         $("#div_findResult").hide();   
      } else if(method == "POST") {
         $("#name").val("${name}");
         $("#mobile").val("${mobile}");
         $("#div_findResult").show();   
      }

      $("#btnFind").click(function(){
    	 var name = $("#name").val().trim();
    	 var moblide = $("#mobile").val().trim();
    	 if(name=="") {
    		 alert("이름을 입력해 주세요.");
    		 return;
    	 }
    	 else if(mobile=="") {
    		 alert("핸드폰번호를 입력해 주세요");
    		 return;
    	 }
    	 else{
	         var frm = document.idFindFrm;
	         frm.action = "<%= ctxPath%>/idFind.do";
	         frm.method = "POST";
	         frm.submit();
    	 }
      });
   });
</script>
<div style="width:40%; margin-left:30%;">
	<form name="idFindFrm" style="margin-top:20%;">
	<div class="row">
	   <div id="div_name" class="col-md-4">
	      <span style="color: black; font-size: 0.9em; font-weight: bold;">성명</span>&nbsp;
	      <input type="text" name="name" id="name" size="15" placeholder="${loginMember.username }" style="width:100%; margin-bottom:5%;" required />
	   </div>
	   <div id="div_mobile" class="col-md-4">
	        <span style="color: black; font-size: 0.9em; font-weight: bold;">휴대전화</span>&nbsp;
	      <input type="text" name="mobile" id="mobile" size="15" placeholder="-없이 입력하세요" style="width:100%; margin-bottom:10%;" required />
	   </div>
	   <div id="div_findResult" class="col-md-4">
	        ID : <span style="color: red; font-size: 16pt; font-weight: bold;">${userid}</span> 
	   </div>
	   <div id="div_btnFind" class="col-md-4">
	         <button type="button" class="btn btn-success" id="btnFind" style="width:100%; background-color:gray;border:none;">찾기</button>
	   </div>
	</div>
</form>
</div>