<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../header.jsp" />
<div id="indexPage">
<div class="container" style="padding:0; width: 100%; margin: 3% 0;">
<form name="itemInfoFrm">
   <div class="row features_items"><!--features_items-->
      <h2 class="title text-center">Recent View</h2>
      <c:if test="${productList.size() < 1 }">
			<div align="center"> 최근 본 상품이 존재하지 않습니다. </div>
		</c:if>
      <c:if test="${productList!=null }">
      	<input type="hidden" name="pcode">
         <c:forEach var="product" items="${productList }">
               <div class="col-xs-3 product product-image-wrapper" style="float: left;height:30%;">
                  <div class="single-products">
                     <div class="productinfo text-center">
                     	<div onClick="goItemDetail('${product.pcode}');">
                        <img src="images/${product.mainimg1 }" onClick="goItemDetail('${product.pcode}');" alt="" style="width: 100%; height: 100%;"/>
                        <h2>${product.pname }</h2>
                        <p><fmt:formatNumber value="${product.price }" pattern="###,###" /> won </p>
                          </div>
						</div>
                      </div>
                      <div class="choose" style="height:10%;">
                        <ul class="nav nav-pills nav-justified">
                           <li><a href="#"><i class="fa fa-plus-square"></i>관심상품 등록</a></li>
                           <li><a id="kakao-link-btn" href="javascript:;"><i class="fa fa-plus-square"></i>공유하기</a>
							</li>
                        </ul>
                     </div>
                  </div>
            </c:forEach>
         </c:if>
      </div><!--features_items-->   
   </form>
</div>
</div>
<jsp:include page="../footer.jsp" />