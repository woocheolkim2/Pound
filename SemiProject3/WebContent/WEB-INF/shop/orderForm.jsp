<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script type="text/javascript" src="SemiProject/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript"></script>
<jsp:include page="../header.jsp" />
<script>
	$(document).ready(function(){
		$("#buyerzipcodeSearch").click(function(){
	         new daum.Postcode({
	           oncomplete: function(data) {
	               $("#buyerpost1").val(data.postcode1);  // 우편번호의 첫번째 값     예> 151
	               $("#buyerpost2").val(data.postcode2);  // 우편번호의 두번째 값     예> 019
	               $("#buyeraddr1").val(data.address);    // 큰주소                         예> 서울특별시 종로구 인사로 17 
	               $("#buyeraddr2").focus();
	           }
	        }).open();
	      });
		$("#buyerzipcodeSearch").click(function(){
	         new daum.Postcode({
	           oncomplete: function(data) {
	               $("#receiverpost1").val(data.postcode1);  // 우편번호의 첫번째 값     예> 151
	               $("#receiverpost2").val(data.postcode2);  // 우편번호의 두번째 값     예> 019
	               $("#receiveraddr1").val(data.address);    // 큰주소                         예> 서울특별시 종로구 인사로 17 
	               $("#receiveraddr2").focus();
	           }
	        }).open();
	      });
		$("#purchaseBtn").click(function(){
			var check = $("#agree").prop("checked");
			if(!check) {
				alert("결제 내용에 동의 하셔야 합니다.");
				return;
			}
			var url = "payment.do";
			var frm = document.orderFrm;
			frm.target="payment";
			frm.method="POST";
			window.open("","payment","top=100px,left=100px,width=810px,height=600px");
			frm.action = url;
			frm.submit();
		});
		$("#cancelBtn").click(function(){
			history.back();
		});
		$("#dif").click(function(){
			$("#receiver").find(":input").val("");
		});
	});
	function goInsertOrder(){
		var frm = document.orderFrm;
		frm.action="orderEnd.do";
		frm.method="POST";
		frm.target="_self";
		frm.submit();
	}
	function spentPoint(){
		var form_data = {"sumtotalprice":$("#sumTotalPrice").val(),"spentpoint":$("#spentpoint").val()};
		$.ajax({
			url:"spentPoint.do",
			type:"POST",
			data:form_data,
			dataType:"JSON",
			success:function(json){
				if(json.n==2){
					alert("포인트 사용!");
					var temp = parseInt(json.dvrfee)+parseInt(json.spentpoint);
					var add = temp.toString().replace(/\B(?=(\d{3})+(?!\d))/g,",");
					var total = json.sumtotalprice.toString().replace(/\B(?=(\d{3})+(?!\d))/g,",");
					var sumtotal = json.lastprice.toString().replace(/\B(?=(\d{3})+(?!\d))/g,",");

					$("#total").empty().html(total);
					$("#add").empty().html(add);
					$("#sumtotal").empty().html(sumtotal);
					$("#sumTotalPrice").val(json.lastprice);
					
				} else if(json.n==1){
					alert("결제금액보다 포인트사용이 더 많습니다.");
				} else if(json.n==0){
					alert("포인트는 1000원이상부터 사용가능합니다.");
				}
			},
			error:function(json){
				console.log($("#loginMemberPoint").val());
				console.log($("#spentpoint").val());
				if(parseInt($("#loginMemberPoint").val()) < parseInt($("#spentpoint").val())){
					alert("가지고 계시는 포인트가 부족합니다.");
				} else {
					alert("잘못입력하셨습니다.");
				}
			}
		});
	} 
</script>
<style>
.orderBtn{
	padding: 1%;
	border-radius: 10px;
	margin: 0 2%;
	border:none;
	background-color: gray;
	color: white;
}
.cencelBtn{
	padding: 1%;
	border-radius: 10px;
	margin: 0 2%;
	border: 1px solid gray;
	background-color: white;
	color: gray;
}
button{
	width: 90%;
	height: 50px;
}
th{
	text-align: center;
	line-height: 250%;
	font-size: 0.8em;
}
</style>
<div id="indexPage">
	<form name="orderFrm" style="margin: 2% 0;">
		<h4 align="center"> 주문서작성 </h4>
		<p align="center" style="color: gray">주문서를 꼼꼼하게 작성해주세요</p>
		<table style="width:100%; margin-top: 1%;border-top: 1px solid lightgray;">
				<thead>
					<tr>
						<th colspan="6" style="background-color: #efefef">주문내역</th>
					</tr>
					<tr style="text-align: center;border-top: 1px solid lightgray;">
						<th>Image</th>
						<th>상품명</th>
						<th>단가</th>
						<th>옵션</th>
						<th>주문수량</th>
						<th>합계</th>
					</tr>
				</thead>
				<tbody align="center" style="border-top: 1px solid lightgray; border-bottom: 1px solid lightgray; margin-bottom:5%;">
					<c:if test="${cartList == null }">
						<tr style="padding: 5% 0;">
							<td colspan="8" align="center" style="line-height: 500%;">
								<img src="images/icon/bubble.png" style="margin-top: 3%;">
								<p>장바구니가 비어있습니다.</p>
							</td>
						</tr>
					</c:if>
					<c:if test="${cartList != null }">
						<c:forEach items="${cartList }" var="cart" varStatus="status">
						<c:set var="sumTotalPrice" value="${sumTotalPrice+(cart.product.price*cart.oqty) }"/>
							<tr style="border-top: 1px solid lightgray;">
								<td style="width: 15%;"><img src="images/${cart.product.mainimg1 }" style="width:150px;height:150px;"/></td>
								<td style="width: 15%; font-weight:bold;" >${cart.product.pname }</td>
								<td style="width: 10%;"><fmt:formatNumber value="${cart.product.price }" pattern="###,###" />원</td>
								<td style="width: 10%;">${cart.ocolor } / ${cart.osize }</td>
								<td style="width: 10%;">${cart.oqty }</td>
								<c:set var="totalPrice" value="${cart.product.price*cart.oqty }" />
								<td style="width:10%;font-weight:bold;"><fmt:formatNumber value="${totalPrice }" pattern="###,###" />원</td>
							</tr>
						<input type="hidden" name="cart_idx" value="${cart.cart_idx }">
						<input type="hidden" name="pname" value="${cart.product.pname }">
						<input type="hidden" name="pcode" value="${cart.product.pcode }">
						<input type="hidden" name="size" value="${cart.osize }">
						<input type="hidden" name="color" value="${cart.ocolor }">
						<input type="hidden" name="oqty" value="${cart.oqty }">
						</c:forEach>
					</c:if>
					<tr style="border-top: 1px solid lightgray; line-height: 300%;">
						<c:if test="${sumTotalPrice>100000 || sumTotalPrice==0}">
							<c:set var="dvrfee" value="0" />
						</c:if>
						<td colspan="6" style="line-height: 200%;color:gray; font-weight:bold;">※상품의 옵션 및 수량 변경은 상품상세 또는 장바구니에서 가능합니다.</td>
					</tr>
					
				</tbody>
			</table>
			<div class="row" style="border-top: 1px solid black;border-bottom: 1px solid black; margin: 3% 0;">
				<h5 style="font-weight: bold; margin-top:2%;" align="left">주문 정보</h5>
				<table style="width: 100%; margin: 2% 0; text-align:center; ">
					<tr style="border-top: 1px solid lightgray;font-size:0.8em;border-bottom: 1px solid lightgray; height: 50px;">
						<td style="width: 15%;border-right: 1px solid lightgray;">주문 하시는분+</td>
						<td><input type="text" value="${loginMember.username }" class="form-control input-md" style="float:left; margin-left: 2%; border: 1px solid lightgray; width: 20%;"></td>
					</tr>
					<tr style="border-top: 1px solid lightgray;font-size:0.8em;border-bottom: 1px solid lightgray; height: 50px;">
						<td style="width: 15%;border-right: 1px solid lightgray;">주소+</td>
						<td style="padding: 1% 0;padding-left: 1.75%;">
						  <div style="float: left; width: 100%; margin-bottom: 1%;">	 
							  <input type="text" id="buyerpost1" name="post1" value="${loginMember.post1 }" size="6" maxlength="3" class="form-control requiredInfo input-md" style="width: 10%; float: left; height: 35px;">
							  <p style="float:left">&nbsp;-&nbsp;</p>
							  <input type="text" id="buyerpost2" name="post2" value="${loginMember.post2 }" size="6" maxlength="3" class="form-control requiredInfo input-md" style="width: 10%; float: left; height: 35px;"/>&nbsp;&nbsp;
							  <input type="button" id="buyerzipcodeSearch" class="mybtn" value="주소찾기" style="float:left; margin-left: 1.5%;border: 1px solid lightgray; color: gray; font-size:0.8em; background-color:white; width: 10%; height: 25px;margin-top: 0.3%;" />
						  </div>
						  <div style="float: left; width: 100%;">
							  <!-- 우편번호 찾기 -->
							  <input type="text" id="buyeraddr1" value="${loginMember.addr1 }" class="form-control address requiredInfo input-md" name="addr1" size="50" maxlength="60" style="width: 40%;margin-bottom: 1%;margin-top:0;"/>
	   						  <input type="text" id="buyeraddr2" value="${loginMember.addr2 }" class="form-control address requiredInfo input-md" name="addr2" size="50" maxlength="60" style="width: 40%;margin:0;"/> 
						  </div>
						</td>
					</tr>
					<tr style="border-top: 1px solid lightgray;font-size:0.8em;border-bottom: 1px solid lightgray; height: 50px;">
						<td style="width: 15%;border-right: 1px solid lightgray;">연락처+</td>
						<td>
							<select class="form-control input-md" style="float:left; margin-left:2%; border: 1px solid lightgray; width: 10%;">
								<option value="010" seleted>010</option>
							</select>
							<p style="float:left;">&nbsp;-&nbsp;</p>
							<input type="text" value="${loginMember.hp2}" class="form-control input-md" style="float:left; border: 1px solid lightgray; width: 10%;">
							<p style="float:left;">&nbsp;-&nbsp;</p>
							<input type="text" value="${loginMember.hp3}" class="form-control input-md" style="float:left; border: 1px solid lightgray; width: 10%;">
						</td>
					</tr>
					<tr style="border-top: 1px solid lightgray;font-size:0.8em;border-bottom: 1px solid lightgray; height: 50px;">
						<td style="width: 15%;border-right: 1px solid lightgray;">이메일+</td>
						<td><input type="text" value="${loginMember.email }" class="form-control input-md" style="float:left; margin-left: 2%; border: 1px solid lightgray; width: 20%;"></td>
					</tr>
				</table>
				<h5 style="font-weight: bold;" align="left">배송 정보</h5>
				<table id="receiver" style="width: 100%; margin: 2% 0; text-align:center; ">
					<tr style="border-top: 1px solid lightgray;font-size:0.8em;border-bottom: 1px solid lightgray; height: 50px;">
						<td style="width: 15%;border-right: 1px solid lightgray;">배송지 정보</td>
						<td style="float:left; margin-left: 2%; padding-top: 1%;">
							<input type="radio" name="select" id="same" checked><label for="same">주문자 정보와 동일</label>
							<input type="radio" name="select" id="dif"><label for="dif">새로운 배송지</label>
						</td>
					</tr>
					<tr style="border-top: 1px solid lightgray;font-size:0.8em;border-bottom: 1px solid lightgray; height: 50px;">
						<td style="width: 15%;border-right: 1px solid lightgray;">받으시는분+</td>
						<td><input type="text" name="receivername" value="${loginMember.username }" class="form-control input-md" style="float:left; margin-left: 2%; border: 1px solid lightgray; width: 20%;"></td>
					</tr>
					<tr style="border-top: 1px solid lightgray;font-size:0.8em;border-bottom: 1px solid lightgray; height: 50px;">
						<td style="width: 15%;border-right: 1px solid lightgray;">주소+</td>
						<td style="padding: 1% 0;padding-left: 1.75%;">
						  <div style="float: left; width: 100%; margin-bottom: 1%;">	 
							  <input type="text" id="receiverpost1" name="receiverpost1" value="${loginMember.post1}" name="post1" size="6" maxlength="3" class="form-control requiredInfo input-md" style="width: 10%; float: left; height: 35px;">
							  <p style="float:left">&nbsp;-&nbsp;</p>
							  <input type="text" id="receiverpost2" name="receiverpost2"value="${loginMember.post2}" name="post2" size="6" maxlength="3" class="form-control requiredInfo input-md" style="width: 10%; float: left; height: 35px;"/>&nbsp;&nbsp;
							  <input type="button" id="receiverzipcodeSearch"  class="mybtn" value="주소찾기" style="float:left; margin-left: 1.5%;border: 1px solid lightgray; color: gray; font-size:0.8em; background-color:white; width: 10%; height: 25px;margin-top: 0.3%;" />
						  </div>
						  <div style="float: left; width: 100%;">
							  <!-- 우편번호 찾기 -->
							  <input type="text" id="receiveraddr1" name="receiveraddr1" value="${loginMember.addr1}" class="form-control address requiredInfo input-md" name="addr1" size="50" maxlength="60" style="width: 40%;margin-bottom: 1%;margin-top:0;"/>
	   						  <input type="text" id="receiveraddr2" name="receiveraddr2" value="${loginMember.addr2 }" value="${loginMember.addr2}" class="form-control address requiredInfo input-md" name="addr2" size="50" maxlength="60" style="width: 40%;margin:0;"/> 
						  </div>
						</td>
					</tr>
					<tr style="border-top: 1px solid lightgray;font-size:0.8em;border-bottom: 1px solid lightgray; height: 50px;">
						<td style="width: 15%;border-right: 1px solid lightgray;">연락처+</td>
						<td>
							<select name="receiverhp1" class="form-control input-md" style="float:left; margin-left:2%; border: 1px solid lightgray; width: 10%;">
								<option value="010" seleted>010</option>
							</select>
							<p style="float:left;">&nbsp;-&nbsp;</p>
							<input type="text" name="receiverhp2" value="${loginMember.hp2}" class="form-control input-md" style="float:left; border: 1px solid lightgray; width: 10%;">
							<p style="float:left;">&nbsp;-&nbsp;</p>
							<input type="text" name="receiverhp3" value="${loginMember.hp3}" class="form-control input-md" style="float:left; border: 1px solid lightgray; width: 10%;">
						</td>
					</tr>
					<tr style="border-top: 1px solid lightgray;font-size:0.8em;border-bottom: 1px solid lightgray; height: 50px;">
						<td style="width: 15%;border-right: 1px solid lightgray;">이메일+</td>
						<td><input type="text" name="receiveremail" value="${loginMember.email }" class="form-control input-md" style="float:left; margin-left: 2%; border: 1px solid lightgray; width: 20%;"></td>
					</tr>
					<tr style="border-top: 1px solid lightgray;font-size:0.8em;border-bottom: 1px solid lightgray; height: 50px;">
						<td style="width: 15%;border-right: 1px solid lightgray;">배송메모</td>
						<td><textarea name="recivercontent" class="form-control input-md" style="float:left; margin-left: 2%; border: 1px solid lightgray; width: 60%; margin: 1% 0; margin-left: 1.7%;"></textarea></td>
					</tr>
				</table>
			</div>
			<div class="row" style="border-top: 1px solid black;border-bottom: 1px solid black; margin: 3% 0;">
				<h5 style="font-weight: bold; margin-top:2%;" align="left">POINT</h5>
				<table style="width: 100%; margin: 2% 0; text-align:center;">
					<tr style="border-top: 1px solid lightgray;border-bottom: 1px solid lightgray;font-size:0.8em;height: 50px;">
						<td style="width: 15%;border-right: 1px solid lightgray;">현재 보유 포인트</td>
						<td style="width: 35%;border-right: 1px solid lightgray;"><input type="text" id="loginMemberPoint" class="form-control input-md" style="width: 40%;margin-left: 2%;" value="${loginMember.point }" readonly></td>
						<td style="width: 15%;border-right: 1px solid lightgray;">사용 할 포인트</td>
						<td style="width: 35%;"><input id="spentpoint" name="spentpoint" class="form-control input-md" type="text" style="float:left;width: 40%;margin-left: 2%;">
						<input type="button" id="receiverzipcodeSearch" class="mybtn" value="적용하기" onclick="spentPoint();" style="float:left;width: 15%;margin-left: 2%;border: 1px solid lightgray; color: gray; font-size:0.8em; background-color:white;height: 25px; margin-top: 1%;" />
						</td>
					</tr>
				</table>
				<h5 style="font-weight: bold; margin-top:2%;" align="left">결제수단</h5>
				<div style="margin: 2% 0; padding:1%; border-top: 1px solid lightgray; border-bottom: 1px solid lightgray;">
					<input type="radio" name="pay" checked><label for="pay">신용카드</label>
				</div>
			</div>
			<h5 style="font-weight: bold;" align="left">결제 예정 금액</h5>
			<table style="width:100%; text-align: center; border-top: 1px solid gray; border-bottom: 1px solid gray;margin: 2% 0;">
				<thead style=" background-color: #eeeeee">
					<tr>
						<th>총 주문금액</th>
						<th>할인+부가결제금액</th>
						<th>총 결제금액</th>
					</tr>
				</thead>
				<tbody>
					<tr id="json" style="font-size: 2em; color: gray; font-weight: bold; line-height: 300%;">
						<td style="width: 30%:"><span id="total"><fmt:formatNumber value="${sumtotalprice }" pattern="###,###" /></span><span style="font-size: 0.8em">won</span></td>
						<td style="width: 30%:">+(-)<span id="add"><fmt:formatNumber  value="${dvrfee }" pattern="###,###" /></span><span style="font-size: 0.8em">won</span></td>
						<td style="width: 35%:">=<span id="sumtotal"><fmt:formatNumber value="${sumtotalprice+dvrfee }" pattern="###,###" /></span><span style="font-size: 0.8em">won</span></td>
					</tr>
				</tbody>
			</table>
			<div>
				<input id="agree" type="checkbox" style="margin-bottom: 3%;"><label for="agree">위 결제 내용에 동의합니다.</label>
			</div>
			<input type="hidden" id="sumTotalPrice" name="sumTotalPrice" value="${sumtotalprice }">
			<input type="hidden" id="dvrfee" name="dvrfee" value="${dvrfee }">
			<div class="row" style="margin-left: 2%;">
				<div class="col-sm-6">
					<button type="button" class="cencelBtn" id="cancelBtn">결제취소</button>
				</div>
				<div class="col-sm-6">
					<button type="button" class="orderBtn" id="purchaseBtn">결제하기</button>
				</div>
			</div>
	</form>
</div>
<jsp:include page="../footer.jsp" />