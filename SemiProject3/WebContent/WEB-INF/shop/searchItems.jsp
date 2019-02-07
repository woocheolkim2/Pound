<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<input id="searchNull" type="hidden" value="${searchItemsSize }">
<h2 class="title text-center">Items</h2>
<input type="hidden" name="postpcode">
<c:forEach var="item" items="${searchItems }">
    <div class="col-sm-3" onClick="goItemDetail('${item.product_idx}');">
       <div class="product-image-wrapper">
          <div class="single-products">
             <div class="productinfo text-center">
                <img src="images/${item.mainimg1 }" alt="" style="height: 320px; width: 300px;" />
                <h2>${item.pname }</h2>
                <p><fmt:formatNumber value="${item.price }" pattern="###,###" /> won </p>
             </div>
          </div>
          <div class="choose">
             <ul class="nav nav-pills nav-justified">
                <li><a href="#"><i class="fa fa-plus-square"></i>관심상품 등록</a></li>
                <li><a href="#"><i class="fa fa-plus-square"></i>공유하기</a></li>
             </ul>
          </div>
       </div>
    </div>
</c:forEach>