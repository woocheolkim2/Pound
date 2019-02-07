<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="member.model.*" %>    
    
<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<!------ Include the above in your HEAD tag ---------->

<jsp:include page="../header.jsp"/>  

<style type="text/css">
	
	#menu-bar .panel {
	margin-bottom: 0;
	background-color: rgba(0,0,0,0);
	border: none;
	border-radius: 0;
	-webkit-box-shadow: none;
	box-shadow: none;
	}
	
	#menu-bar li a {
		color: #fff;
		font-size: 16px;
		font-weight: bold;
	}
	
	#navbar {
		float: left;
		width: 300px;
		height: 100%;
	}
	
	.content {
		margin-left: 300px;
		min-height: 100%;
	}
	
	.container {
		margin-left: 25px;
	}

	div#navbar.sideNavBar{
		display:inline-block;
		position: fixed;
		top:36%;
		left:0; 
	}
</style>
<script>
	function goMemberDelete(){
		$("#deleteMemberbtn").click(function(){
			var confirm = confirm("정말로 회원탈퇴 하시겠습니까?");
			if(!confirm){
				alert("회원탈퇴를 취소하셨습니다.");
				return;
			}
			else{
				location.href="memberDelete.do";
			}
		});
	});
</script>
  <!-- Bootstrap core CSS -->
  <!-- <link href="css/bootstrap.min.css" rel="stylesheet"> -->

  <!-- Custom styles for this template -->
 <!--  <link href="styles.css" rel="stylesheet"> 글전체 css -->

  <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!--[if lt IE 9]>
  <script src="js/html5shiv.js"></script>
  <script src="js/respond.min.js"></script>
  <![endif]-->
</head>

<body>
      <div id="navbar" class="navbar navbar-inverse sideNavBar" style="height: 500px;">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle Navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
         
        </div>
        <div class="collapse navbar-collapse">
          <ul class="nav nav-stacked" id="menu-bar">
            <li><a href="#">Home</a></li>
            <li><a href="mypage.do">나의 정보 조회</a></li>
            <li><a href="memberEdit.do">나의 정보 수정 </a></li>
            <li><a href="cart.do">장바구니</a></li>
            <li><a href="orderList.do">주문 조회</a></li>
            <li><a id="deleteMemberbtn" onClick="goMemberDelete();">회원탈퇴</a></li> 
          </ul>
        </div>
  </div>
  <jsp:include page="${goPage }" />
  <script type="text/javascript">
    (function($) {
        var $window = $(window),
            $html = $('#menu-bar');

        $window.resize(function resize(){
            if ($window.width() < 768) {
                return $html.removeClass('nav-stacked');
            }
            $html.addClass('nav-stacked');
        }).trigger('resize');
    })(jQuery);
  </script>