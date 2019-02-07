<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>
$(document).ready(function(){
	$(".icontext").hide();
	$("#resultview").hide();
	$(".pspec").each(function(){
		if($(this).text()== "[normal]") $(this).hide();
	});
	$("#searchItemName").keydown(function(event){
		if(event.keyCode==13){
			var form_data = {searchItemName:$("#searchItemName").val()};
			$.ajax({
				url:"searchItems.do",
				type:"GET",
				data: form_data,
				dataType:"html",
				success:function(html){
					$("#showSearchWord").text($("#searchItemName").val());
					$("#resultview").show();
					$(".features_items").empty().append(html);
					$("#mainImg").hide();
					var resultsize = $("#searchNull").val();
					$("#resultsize").text(resultsize);
					var itemsize = $("#searchNull").val();
					if(itemsize==null){
						alert("검색하신 상품이 존재하지 않습니다.");
						location.reload();
						return;
					}
				},
				error:function(){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				}
			});
		}
	});
	 //<![CDATA[
    // // 사용할 앱의 JavaScript 키를 설정해 주세요.
    Kakao.init('c2d917f16da63c2b5383e5659f66e783');
    // // 카카오링크 버튼을 생성합니다. 처음 한번만 호출하면 됩니다.
});
function goKaKaoLink(index){
    Kakao.Link.createDefaultButton({
      container: '#kakao-link-btn'+index,
      objectType: 'commerce',
      content: {
        title: $("#pname"+index).val(),
        imageUrl: 'https://blogfiles.pstatic.net/MjAxODEyMTJfNTgg/MDAxNTQ0NTgzMjQ3Mzgx.N7c4ReF3YZKN1r0ZP4PNTtnAhDBaxu61nhR9nDddWZwg.oNw8TMpurkVgPGJi0pJdHoMGmuwKK3ffaGKh9br-Xk4g.JPEG.daiunii/TOKYO_%EB%B0%B0%EA%B2%BD%ED%99%94%EB%A9%B4.jpg',
        link: {
          mobileWebUrl: '192.168.50.25:9090/SemiProject/home.do',
          webUrl: '192.168.50.25:9090/SemiProject/home.do'
        }
      },
      commerce: {
        regularPrice: parseInt($("#price"+index).val()),
        discountPrice: parseInt($("#price"+index).val())*0.9,
        discountRate: 10
      },
      buttons: [
        {
          title: '구매하기',
          link: {
            mobileWebUrl: 'http://192.168.50.25:9090/SemiProject/itemDetail.do?pcode='+$("#pcode"+index).val(),
            webUrl: 'http://192.168.50.25:9090/SemiProject/itemDetail.do?pcode='+$("#pcode"+index).val()
          }
        },
        {
          title: '웹으로 가기',
          link: {
            mobileWebUrl: 'http://192.168.50.25:9090/SemiProject/home.do',
            webUrl: 'http://192.168.50.25:9090/SemiProject/home.do'
          }
        }
      ]
    });
}
//]]>
</script>
<style>
</style>
<div id="maxwidth">
<section><!--slider-->
      <div class="container homeImg" style="padding:0;">
         <div class="row" id="mainImg" style="width: 100%;padding:0;margin:0;">
             <div id="slider-carousel" class="carousel slide" data-ride="carousel">
                <ol class="carousel-indicators">
                   <li data-target="#slider-carousel" data-slide-to="0" class="active"></li>
                   <li data-target="#slider-carousel" data-slide-to="1"></li>
                   <li data-target="#slider-carousel" data-slide-to="2"></li>
                </ol>
                <div class="carousel-inner">
	         		<img src="/SemiProject/images/clothes_images/TOKYO_배경화면.jpg" style="width:100%;overflow:hidden; height: 20%;" alt="" />
                </div>
                <a href="#slider-carousel" class="left control-carousel hidden-xs" data-slide="prev">
                   <i class="fa fa-angle-left"></i>
                </a>
                <a href="#slider-carousel" class="right control-carousel hidden-xs" data-slide="next">
                   <i class="fa fa-angle-right"></i>
                </a>
             </div>
         </div>
      </div>
      <div id="resultview" style="font-size: 1.2em; text-align:center; margin-top: 2%;">검색어 [ 
      	<span id="showSearchWord" style="font-weight: bold; font-size: 1.3em;"></span> ]으로 검색한 결과입니다.
      	<br/><span style="font-size:0.8em; color: lightgray;">검색 결과 수 :<span id="resultsize"></span> 개</span>
      	</div>
      <div class="search_box" align="center" style="margin: 2% 0">
            <input type="text" id="searchItemName" placeholder="Search" size="20" style="width: 30%"/> 
      </div>
   </section><!--/slider-->
   <section id="indexPage">
      <div class="container">
            <form name="itemInfoFrm">
               <div class="row features_items"><!--features_items-->
                  <h2 class="title text-center">Items</h2>
                  <c:if test="${productList!=null }">
                  <input type="hidden" name="pcode">
                     <c:forEach var="product" items="${productList }" varStatus="status">
                     <div class="col-sm-3" style="height: 400px;">
                           <div class="product product-image-wrapper" style="margin: 1%; padding:0;">
                              <div class="single-products">
                                 <div class="productinfo text-center">
                                 	<div onClick="goItemDetail('${product.pcode}');">
                                    <img src="images/${product.mainimg1 }" onClick="goItemDetail('${product.pcode}');" alt=""/>
                                    <h2><span class="pspec" style="font-size: 0.6em;color: red; margin-right: 2%;">[${product.pspec }]</span>${product.pname }</h2>
                                    <p><fmt:formatNumber value="${product.price }" pattern="###,###" /> won </p>
 	                                </div>
								</div>
                              </div>
                              <div class="choose">
                                 <ul class="nav nav-pills nav-justified">
                                    <li><a href="#"><i class="fa fa-plus-square"></i>관심상품 등록</a></li>
                                    <li><a id="kakao-link-btn${status.index }" href="javascript:goKaKaoLink('${status.index }');"><i class="fa fa-plus-square"></i>공유하기</a>
									<input id="pname${status.index }" type="hidden" value="${product.pname }">
									<input id="price${status.index }" type="hidden" value="${product.price }">
									<input id="pcode${status.index }" type="hidden" value="${product.pcode }">
									</li>
                                 </ul>
                              </div>
                           </div>
                           </div>
                     </c:forEach>
                  </c:if>
               </div><!--features_items-->   
            </form>
         </div>
   </section>
</div>
<jsp:include page="footer.jsp" />

