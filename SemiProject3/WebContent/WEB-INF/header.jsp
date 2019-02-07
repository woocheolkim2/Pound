<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Home | Pound</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/font-awesome.min.css" rel="stylesheet">
    <link href="css/prettyPhoto.css" rel="stylesheet">
    <link href="css/price-range.css" rel="stylesheet">
    <link href="css/animate.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet">
    <link href="css/responsive.css" rel="stylesheet">
	<link href="css/customHY.css" rel="stylesheet">
    
    <!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->       
    <link rel="shortcut icon" href="images/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="images/ico/apple-touch-icon-57-precomposed.png">
    
    <script src="js/jquery-3.3.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/jquery.scrollUp.min.js"></script>
	<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
    <script>
    function down(){
    	$(document).scrollTop($(document).height());}
		function goItems(cg1stcode){
			var frm = document.categoryFrm;
			frm.cg1stcode.value=cg1stcode;
			frm.action="items.do";
			frm.method="GET";
			frm.submit();
			
		}
		function goItemDetail(pcode){
			var frm = document.itemInfoFrm;
			frm.pcode.value=pcode;
			frm.action="itemDetail.do";
			frm.method="GET";
			frm.submit();
		}
		function goCart(pcode){
			var frm = document.itemInfoFrm;
			frm.action="addCart.do";
			frm.method="POST";
			frm.submit();
		}

		function goAdminPage(){
			var frm = document.itemInfoFrm;
			frm.action="admin.do";
			frm.method="POST";
			frm.submit();
		}
	</script>
</head><!--/head-->
<header><!--header-->
	<div style="background-color: black; height: 30px;"><!--header_top-->
	</div><!--/header_top-->
     
    <div class="header-middle" ><!--header-middle-->
       <div class="container" style="border-bottom: 1px solid #e6e6e6;">
          <div class="row" style="margin: 0 auto; padding: 5px 0;">
             <div class="col-sm-8 col-sm-offset-4">
                <div class="shop-menu pull-right">
                   <ul class="nav navbar-nav">
                      <c:if test="${loginMember==null }">
                      	<li><a href="login.do"><i class="fa fa-lock"></i>Login</a></li>
                      <li><a href="register.do"><i class="fa fa-user"></i>Join</a></li>
                      </c:if>
                      <c:if test="${loginMember!=null && loginMember.userid != 'admin' }">
                      <li><a href="logout.do" onClick="javascript:kakao.Auth.logout();"><i class="fa fa-lock"></i>Logout</a></li>
                      <li><a href="mypage.do"><i class="fa fa-user"></i>Mypage</a></li>
                      </c:if>
                      <c:if test="${loginMember.userid == 'admin' }">
                      <li><a style="cursor: pointer;" onClick="goAdminPage();"><i class="fa fa-lock"></i>Admin</a></li>
                      <li><a style="cursor: pointer;" href="logout.do"><i class="fa fa-lock"></i>Logout</a></li>
                      </c:if>
                      <li><a href="orderList.do"><i class="fa fa-crosshairs"></i>Order</a></li>
                      <li><a href="cart.do"><i class="fa fa-shopping-cart"></i>Cart</a></li>
                   </ul>
                </div>
             </div>
          </div>
       </div>
    </div><!--/header-middle-->
    <div align="center">
       <a href="home.do"><img src="images/home/homeLogo.png" style="margin: 5% 0; width: 12%;" alt="" /></a>
    </div>
    <div class="header-bottom" style="border-top: 1px solid #e6e6e6; border-bottom: 1px solid #e6e6e6; margin:0 auto; padding:0;"><!--header-bottom-->
       <div class="container">
		<div class="mainmenu pull-left" style="padding: 1% 0; margin-left: 20%; width:100%;" >
			<ul class="nav navbar-nav collapse navbar-collapse" style="width:80%;">                        
				<li class="dropdown"><a href="#">MENU<i class="fa fa-angle-down"></i></a>
					<ul role="menu" class="sub-menu" style="left: -95px;">
					<c:forEach items="${cglist }" var="cg">
						<div class="verticalMenu">   
							<ul>
								<li><a style="cursor: pointer;" onClick="goItems('${cg.cg_1st_code}');">${cg.cg_1st_name}</a></li>
								<c:forEach items="${cg.cg2ndList }" var="cg2nd">
								<li><span style="font-size: 8pt; color: gray; cursor: pointer;">${cg2nd.cg_2nd_name }</span></li>
								</c:forEach>
							</ul>
						</div>
					</c:forEach>
					</ul>                                            
				</li>           
				<c:forEach var="cg" items="${cglist }">                      
				<li style="font-size:1.2em;"><a href="#" onClick="goItems('${cg.cg_1st_code}');">${cg.cg_1st_name }</a></li> 
				</c:forEach>
		   </ul>
		</div>
       </div>
    </div><!--/header-bottom-->
    <form name="categoryFrm">
    	<input type="hidden" name="cg1stcode">
</form>
</header><!--/header-->
<div id="quick">
		<ul>
        	<li class="hovercng" style="background-color: black;"><a href="#" >
	        	<img src="/SemiProject/images/icon/menu.png" class="iconimg" alt="메뉴">
	        	<%-- <span class="icontext">메뉴</span> --%>
	           	</a></li> 
	        <li class="hovercng"><a href="recentView.do" >
	        	<img src="/SemiProject/images/icon/musica-searcher.png" class="iconimg" alt="최근 본 상품으로 이동">
	        	<!-- <span class="icontext">최근본상품</span> -->
	           	</a></li> 
	        <li class="hovercng"><a href="cart.do" >
	         	<img src="/SemiProject/images/icon/shopping-cart-black-shape.png" class="iconimg" alt="장바구니로 이동">
	         	<!-- <span class="icontext">장바구니</span> -->
	           	</a></li> 
            <li class="hovercng"><a href="likeItem.do" >
            	<img src="/SemiProject/images/icon/favorite-heart-button.png" class="iconimg" alt="관심상품으로 이동">
	         	<!-- <span class="icontext">관심상품</span> -->
	           	</a></li> 
            <li class="hovercng"><a href="#">
            	<img src="/SemiProject/images/icon/support.png" class="iconimg" alt="">
	         	<!-- <span class="icontext">상담</span> -->
	           	</a></li>
	        <li class="hovercng"><a href="orderList.do">
            	<img src="/SemiProject/images/icon/delivery-truck.png" class="iconimg" alt="">
	         	<!-- <span class="icontext">배송</span> -->
	           	</a></li>
	        <li style="border:none;"><a href="#">
            	<img src="/SemiProject/images/icon/up-arrow.png" class="iconimg" alt="위로">
	         	<!-- <span class="icontext">위로</span> -->
	           	</a></li>
	        <li style="border:none;cursor:pointer"><a onClick="javascript:down();">
            	<img src="/SemiProject/images/icon/arrow-down-sign-to-navigate.png" class="iconimg" alt="아래로">
	         	<!-- <span class="icontext">아래로</span> -->
	           	</a></li>   	   	
         </ul>
	</div>