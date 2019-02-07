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
    <link href="js/jquery-ui-1.12.1.custom/jquery-ui.min.css" rel="stylesheet">
    
    
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
	<script src="js/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
	
    <style type="text/css">
    	header:after{
    		clear:both;
    		content: '';
    		display:block;
    		margin-top:10px;
    		border:solid 1px #efefef;
    	}
    </style>
    <script type="text/javascript">
    	// J-Query
    	$(document).ready(function(){
    		$("header").next().css("margin-top", "40px");
    	}); 
    	
    	// Javascript
    	function goMemberManagement(){
			var frm = document.myform;
			frm.action="memberManagement.do";
			frm.method="POST";
			frm.submit();
		}
		
		function goProdRegist(){
			var frm = document.myform;
			frm.action="prodRegist.do";
			frm.method="POST";
			frm.submit();
		}
		
		function goProdList(){
			var frm = document.myform;
			frm.action="prodList.do";
			frm.method="POST";
			frm.submit();
		} 
    </script>
</head><!--/head-->
<body>
<form name="myform" method="post" action="admin.do">
	<header id="header" class="adminHeader" ><!--header-->
      <div align="center">
         <a style="cursor:pointer;" href="home.do"><img src="images/home/homeLogo.png" style="margin: 3% 0;" alt="" /></a>
      </div>
      <div class="adminMenues row"><!--header-bottom-->
         <div class="col-md-10 col-md-offset-2">
	       	<div class="col-md-2"><a style="cursor: pointer;" onClick="goMemberManagement();">회원관리</a></div>
	       	<div class="col-md-2"><a style="cursor: pointer;" onClick="goProdRegist();">제품등록</a></div>
	       	<div class="col-md-2"><a style="cursor: pointer;" onClick="goProdList();">제품관리</a></div>
	       	<div class="col-md-2"><a style="cursor: pointer;" href="">배송관리</a></div>
	       	<div class="col-md-2"><a style="cursor: pointer;" href="logout.do">로그아웃</a></div>
	     </div>
      </div><!--/header-bottom-->
	</header><!--/header-->
</form>
	<c:if test="${goPage != null}">
		<jsp:include page="${goPage}" />
	</c:if>


















