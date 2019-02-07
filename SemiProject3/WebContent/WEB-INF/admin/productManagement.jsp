<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
   
         $(document).ready(function(){
           
          
            
         });
   
       function goDetail(code) {
                  
         var frm = document.prodFrm;
         frm.prodCode.value = code;
        //  frm.goBackURL.value = goBackURL;
         frm.method = "POST";
         frm.action = "prodEdit.do";
         frm.submit();
      }// end of goCgList(idx, goBackURL)-------
    
</script>
<style>
th{text-align:center;}
td{border-bottom: 1px solid lightgray;}
</style>
<section id="product">
      <div class="container" align="center">
         <h3 style="color:gray;"> 제품관리 </h3>
         <div align="center" style="padding: 1% 0;">이름 : <input type="text" id="keyword" /></div>
         <table id="product" class="outline product" style="text-align:center; margin-bottom: 3%; border: 1px solid lightgray;"> 
            <thead style="line-height: 300%; border-bottom: 3px double lightgray;"> 
               <tr style="text-align:center;align:center;">
                  <th class="th" style="width: 5%;">제품번호</th>
                  <th class="th">제품분류</th>
                  <th class="th">제품명</th>
                  <th class="th">메인이미지</th>
                  <th class="th">색상/사이즈 재고량</th>
                  <th class="th">가격</th>
                  <th class="th">누적판매량</th>
                  <th class="th">HIT/BEST/NEW</th>
                  
               </tr>
            </thead>
            <tbody style="border-bottom: 3px double lightgray;">
            <c:if test="${prodList == null }">
               <tr>
                  <td class="th" colspan="7">등록된 제품이 없습니다.</td>
               </tr>
            </c:if>
            <c:if test="${prodList != null }">   
                 <c:forEach items="${prodList }" var="prod">
                    <tr class="product" onClick="goDetail('${prod.pcode}');">
                     <td>${prod.product_idx }</td>
                     <td>${prod.category.cg2ndList.cg_1st_name }/${prod.category.cg2ndList.cg_2nd_name }</td>
                     <td>${prod.pname }</td>
                     <td><img src = "images/${prod.mainimg1 }" style="width:100px; height:120px;"/></td>
                     <td>
                     <c:forEach items="${prod.stockList}" var="stockMap">
                     ${stockMap.psize } / ${stockMap.color } / ${stockMap.pqty }<br/>
                     </c:forEach>
                     </td>
                     <td>${prod.price }</td>
                     <td>${prod.totalsqty }</td>
                     <td>${prod.pspec }</td>              
                    </tr>
                 </c:forEach>
               </c:if>
            </tbody>
         </table>
      </div>
   </section>
   <div id="blank">
      <form name="prodFrm">
          <input type="hidden" name="prodCode" />
       </form>
   </div> 
   
</body>