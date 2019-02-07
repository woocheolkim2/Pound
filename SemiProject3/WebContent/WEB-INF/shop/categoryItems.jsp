<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../header.jsp" />
<section id="indexPage" style="margin-top: 2%;">
      <div class="container">
         <div class="row">
            <div class="col-sm-12 padding-right">
               <div class="features_items"><!--features_items-->
                  <h2 class="title text-center">${category.cg_1st_name }</h2>
                  <%-- <div style="margin-bottom: 2%;">
					  <div class="row">
						  <div class="col-md-12"  style="margin: 0 auto;">
							  <ul style="list-style-type: none;">
							  <c:forEach items="${category.cg2ndList }" var="cg2">
								  <li style="float:left;cursor:pointer; margin: 0 2%"><a>#${cg2.cg_2nd_name }</a></li>
							  </c:forEach>
							  </ul>
						  </div>
					  </div>
				  </div> --%>
                  <c:if test="${searchItems!=null }">
                  <form name="itemInfoFrm">
                  <input type="hidden" name="pcode">
                     <c:forEach var="item" items="${searchItems }">
				       <div class="col-sm-3" onClick="goItemDetail('${item.pcode}');">
				          <div class="product-image-wrapper">
				             <div class="single-products">
				                <div class="productinfo text-center">
				                   <img src="images/${item.mainimg1 }"  alt="" style="height: 320px;" />
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
				    </form>
                  </c:if>
               </div><!--features_items-->   
            </div>
         </div>
      </div>
   </section>
<jsp:include page="../footer.jsp" />