<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>
   $(document).ready(function(){
      $(".spinnerPqty").spinner({
          spin : function(event, ui) {
             if (ui.value > 100) {
                $(this).spinner("value", 100);
                return false;
             }
             else if (ui.value < 0) {
                $(this).spinner("value", 0);
                return false;
             }
          }
       }); // end of $("#spinnerPqty").spinner();---------------
       
       $("#addButton").bind("click", function() {
           //==== 스피너는 이벤트가 "change" 가 아니라 "spinstop" 이다. ====//

           var html = "";   
           html += "<br/>";
           html += "<td colspan='3' width='25%' align='left'><input type='text' name='color' /><input type='text' name='psize' /><input class='spinnerPqty' name='pqty' value='수량' style='width: 30px; height: 20px;'><button type='button' class='removebtn'>삭제</button></td><td></td></tr><tr>";
           $("#colorsizeiqty").append(html); 
           $(".removebtn").click(function(){
               $(this).prev().prev().prev().prev().detach();
               $(this).prev().prev().prev().detach();
               $(this).prev().prev().detach();
               $(this).prev().detach();
               $(this).detach();
            });
         });
   
       $("#editOk").click(function(){
         alert("제품 수정완료!!");
         var frm = document.detailForm;
         frm.method = "POST";
         frm.action = "prodEditEnd.do";
         frm.submit();
       });
       
      $("#editPname").click(function(){
         var frm = document.detailForm;
         frm.metho0d = "POST";
         frm.action = "prodEditEnd.do";
         frm.submit();
      });
      
   });
</script>
<style>
tr>td{
   color:gray;
   line-height: 200%;
   text-align:center;
   width: 25%;
}
select{
   background-color: white;
   border:1px solid lightgray;
}
.mybtn{
	border: none;
	background-color: lightgray;
	color: white;
	border-radius: 3%;
}
</style>

<div id="itemDetailBody" style="border-top: 1px solid #e6e6e6">
   <div class="row" style="margin: 3% 0; ">
      <form name="detailForm">
      <div class="col-sm-offset-2 col-sm-8" style="border-top: 4px double gray;border-bottom: 4px double gray;padding: 2% 0;">
         <div class="col-sm-offset-1 col-sm-5" style="padding:0; overflow:hidden; border-bottom: 1px solid #e6e6e6;">
            <img src="images/${item.mainimg1 }" width="480px">
         </div>
         <div class="col-sm-5">
            <h2 align="center" style=" padding-bottom: 3%;">${item.pname }</h2>
            <table id="itemInfo" style="padding: 3% 0; width:100%; margin-left: 0;">
               <tr><td colspan="4" style="border-bottom: solid 1px lightgray; height: 20px;"> </tr>
               <tr><td colspan="4" style="height: 20px;"> </tr>
               <tr style="padding-top:5%;">
                  <td style="width: 20%;">제품명 : </td>
                  <td colspan="2" style="padding-right: 6%;"><input id="pname" name="pname" type="text" value="${item.pname }"/></td>
                  <td><button type="button" id="editPname" class="mybtn" style="padding: 1% 15%;">변경</button></td>
               </tr>
               <tr>
                  <td>가격 : </td>
                  <td colspan="2"><input name="price" type="text" value="${item.price }"/> won</td>
                  <td><button type="button" id="editPrice" class="mybtn" style="padding: 1% 15%;">변경</button></td>
               </tr>
               <tr><td colspan="4" style="border-bottom: solid 1px lightgray; height: 20px;"> </tr>
               <tr><td colspan="4" style="height: 20px;"> </tr>
               <tr>
                  <td>사이즈</td>
                  <td>색상</td>
                  <td>수량</td>
                  <td></td>
               </tr>
               <c:forEach items="${stockList}" var="stock">
                    <tr class="product" >   
                       <td><span style="margin-right: 10%;">${stock.psize }</span><input type="hidden" name="psize" value="${stock.psize }"/></td>
	                     <td><span style="margin-right: 10%;">${stock.color}</span><input type="hidden" name="color" value="${stock.color}"/></td>
	                     <td><input class="spinnerPqty" name="pqty" value="${stock.pqty }" style="width: 30px; height: 20px;" /></td>
	                     <td><input type="hidden" name="pcode" value="${stock.pcode}" />
						 <button type="button" id="addButton" class="mybtn" style="padding: 1% 15%;">추가</button>
	                     </td>                                                                     
                    </tr>
               </c:forEach> 
               <tr id="colorsizeiqty">
               	  <td colspan="3" width="25%" align="left"></td>
               	  <td></td>
               </tr>
               <tr>
                  <td colspan="2" align="center" style="padding-left: 3%;margin-top:5%;">
                     <button type="button" id="editOk" class="btn btn-primary" style="background-color: black; color:white; border:none; width:90%; height: 40px; float:left;">제품수정</button>
                     <!-- <div id="likeBtn"><img src="images/icon/unlike.png" style="margin-left: 3%; width:30px; height:30px;float:left;padding-top: 0.5%;"/></div> -->
                  </td>
                  <td colspan="2" align="center">
                  	<button type="button" id="editDel" class="btn btn-primary" onClick="javascript:history.back();" style="background-color: gray; color:white; border:none; width: 90%; margin-left: 2%;height: 40px;float:left;">취소하기</button>
                  </td>
               </tr>
            </table>
         </div>
         <div class="col-sm-offset-1">
         </div>
      </div>
      </form>
   </div>
   <div class="row" align="center">
      <div class="col-sm-offset-2 col-sm-8" style="border: 1px solid gray; padding: 3% 0;">
         <c:if test="${pImgList==null }">
            <div>상세 이미지 준비중입니다.</div>
         </c:if>
         <c:if test="${pImgList !=null}">
            <c:forEach items="${pImgList}" var="pImg">
               <img src="images/${pImg.imageName }">
            </c:forEach>
         </c:if>
      </div>
   </div>
   <div class="row" align="center">
      <div class="col-sm-offset-2 col-sm-8" style="border: 1px solid gray; padding: 3% 0;">
         <table style="width:100%;border:solid;text-align:center;">
            <tr>
               <th>글번호</th>
               <th>작성자</th>
               <th>내용</th>
               <th>작성일자</th>
            </tr>
            <c:if test="${memoList==null }">
               <tr>
                  <td colspan="4">등록된 Q&A가 없습니다.</td>
               </tr>
            </c:if>
            <c:if test="${memoList !=null}">
               <c:forEach items="${memoList}" var="memo">
               <tr>
                  <td>1</td>
                  <td>2</td>
                  <td>3</td>
                  <td>4</td>
               </tr>
               </c:forEach>
            </c:if>
         </table>
      </div>
   </div>
</div>
<jsp:include page="../footer.jsp" />