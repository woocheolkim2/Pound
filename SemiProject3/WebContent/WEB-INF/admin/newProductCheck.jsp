<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

 
<!DOCTYPE html>
<html>
<head>
   <meta charset="UTF-8">
   <title>신규 상품인지 검사합니다.</title>
   <link rel="stylesheet" type="text/css" href="${ctxPath}/css/style.css">
   <script type="text/javascript" src="${ ctxPath }/js/jquery-3.3.1.min.js"></script>
   <script type="text/javascript">
      $(document).ready(function(){
         $("#error").hide();
         
      });// end of $(document).ready();-------------------------------------
   
      function goCheck(){
        
         
         var pname = $("#pname").val();
         
         if(pname==""){
            $("#error").show();
            
            $("#pname").val("");
            $("#pname").focus();
            return;
         }
         else{
            $("#error").hide();
            var frm = document.frmPnameCheck;
            frm.method = "POST";
            frm.action = "prodDuplicateCheck.do";
            frm.submit();
         }
         
         
      }// end of goCheck();---------------------------------

      // **** !!! 팝업창에서 부모창으로 값 넘기기 jQuery를 사용한 방법
      function setNewPname(pname){
   
         $(opener.document).find("#pname").val(pname);

         self.close();
         
         
      };
      function setExistingPname(pname){
   
        $(opener.document).find("#duplicate").val("1");
         $(opener.document).find("#pname").val(pname);
       $(opener.document).find("#pcompany").removeClass("infoData");
       $(opener.document).find("#descProda").removeClass("infoData");
       $(opener.document).find("#priceProda").removeClass("infoData");
       
       $(opener.document).find("#mainImg1").removeClass("infoData");
       $(opener.document).find("#mainImg2").removeClass("infoData");
       
        $(opener.document).find("#pcompany").parent().parent().hide();
       $(opener.document).find("td.mainImgProd").parent().hide();
       $(opener.document).find("td.priceProd").parent().hide();
       $(opener.document).find("td.descProd").parent().hide();
       
       

      
         self.close();   
         
      } 
   </script>


</head>
<body style="background-color: #fff0f5;">
   <span style="font-size: 10pt; font-weight: bold">${requestScope.method}</span>
   
   <c:if   test="${requestScope.method == 'GET'}">
         <form name = "frmPnameCheck">
            <table style="width : 95%; hegiht : 100px;">
               <tr>
                  <td style="text-align: center;">
                     등록할 상품이름을 입력하세요 <br style="line-height: 200%;"/>
                     <input type="text" id="pname" name="pname" class = "box" size="20"/><br style="line-height: 300%;"/>
                     <span id="error" style="color:red; font-size: 10pt; font-weight: bold">상품이름을 입력하세요!!</span><br/>
                     <button type="button" class = "box" onclick="goCheck();">확인</button>
                  </td>
                  
               </tr>
            </table>
         </form>
   </c:if>
         

   <c:if   test="${requestScope.method == 'POST'}">
      <c:if test="${requestScope.isNew ==true}">
         <div align="center">
               <br style="line-height: 300%;"/>
               신규 상품입니다. [<span style="color: red; font-weight: bold">${requestScope.pname}</span>] 
               <br/><br/><br/>
               <button type="button" class = "box" onClick="setNewPname('${requestScope.pname}')">닫기 (이 상품명 사용하기)</button>
         </div>
      </c:if>
      
     <c:if test="${requestScope.isNew ==false}">
         <div align="center">
               [<span style="color: red; font-weight: bold">${requestScope.pname}</span>] 는 이미 존재하는 상품입니다.
               <br/>
               <form name = "frmPnameCheck">
                  <table style="width : 95%; hegiht : 100px;">
                     <tr>
                        <td style="text-align: center;">
                           새로운 제품을 등록하시려면 다시 상품명을 입력하세요<br style="line-height: 200%;"/>
                           <input type="text" id="userid" name="userid" class = "box" size="20"/><br style="line-height: 300%;"/>
                           <span id="error" style="color:red; font-size: 10pt; font-weight: bold">아이디를 입력하세요!!</span><br/>
                           <button type="button" class = "box" onclick="goCheck();">확인</button>
                        </td>
                     </tr>
                     
                     <tr>
                        <td style="text-align: center">
                           <br/><br/><br/>
                           <button  type="button" class = "box" onClick="setExistingPname('${requestScope.pname}')">닫기 (기존 상품을 추가 등록합니다. )</button>
                        </td>
                        
                     </tr>
                  </table>
               </form>
         </div>

      </c:if>   
   </c:if>

</body>
</html>