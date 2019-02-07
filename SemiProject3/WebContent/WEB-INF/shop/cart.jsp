<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../header.jsp" />
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="/resources/demos/external/jquery-mousewheel/jquery.mousewheel.js"></script>
<script>
	$(document).ready(function(){
		$("#allSelect").click(function(){
			$(".check").each(function(){
				var bool = $("#allSelect").prop("checked");
				$(this).prop("checked",bool);
			});
		});
		$(".oqtyEdit").spinner({
			spin : function(event, ui) {
				var pqty = $(this).parent().parent().find(".pqty").val();
				 if (ui.value > pqty) {
					 alert("재고가 부족합니다"+pqty+"개 이하로 입력해 주세요.")
	               $(this).spinner("value", pqty);
	               return false;
	             } 
	             else if (ui.value < 0) {
	               $(this).spinner("value", 0);
	               return false;
	             }
			}
		})
		$(".oqtyEdit").change(function(){
			alert();
			
			/* var form_data = {"pcode":"${item.pcode}","color":$("#color").val(),"size":$(this).val()};
			$.ajax({
				url:"getPqty.do",
				type:"POST",
				data:form_data,
				dataType:"JSON",
				success:function(json){
					$("#checkpqty").val(json.pqty);
				},
				error:function(){
					
				}
			}); */
		});
	});
	function goItemDetail(pcode){
		var frm = document.cartOrderForm;
		frm.pcode.value=pcode;
		frm.action="itemDetail.do";
		frm.submit();
	}
	function goDelete(cart_idx){
		var yn = confirm("장바구니에서 해당 상품을 삭제 하시겠습니까?");
		if(!yn) return;
		var form_data = {"cart_idx":cart_idx};
		$.ajax({
			url:"itemDelete.do",
			type:"POST",
			data:form_data,
			dataType:"JSON",
			success:function(json){
				var n = json.n;
				if(n=="1"){
					alert("삭제가 완료되었습니다.");
					return;
				}
			},
			error:function(){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
		});
	}
	function qtyEdit(cart_idx,status_idx){
		var yn = confirm("수량을 변경하시겠습니까?");
		if(!yn) return;
		var form_data = {"cart_idx":cart_idx,"oqty":$("#oqty"+status_idx).val()};
		$.ajax({
			url:"cartEdit.do",
			type:"POST",
			data:form_data,
			dataType:"JSON",
			success:function(json){
				var n = json.n;
				if(n=="1"){
					alert("수량이 변경되었습니다.");
					location.reload();
					return;
				}
			},
			error:function(){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
		});
	}
	function goOrder(){
		var cnt = 0;
		$(".check").each(function(){
			if($(this).prop("checked")==false){
				$(this).attr("disabled",true);
			}
			else cnt++;
		});
		if(cnt<1){
			alert("주문할 상품을 한개이상 선택하세요");
			return;
		}
		var frm = document.cartOrderForm;
		frm.action="order.do";
		frm.method="POST";
		frm.submit();
	}
</script>
<style>
.orderBtn{
	padding: 1% 1%;
	border-radius: 10px;
	margin: 0 2%;
	border:none;
	background-color: gray;
	color: white;
}
.deleteBtn{
	padding: 1% 1%;
	border-radius: 10px;
	margin: 0 2%;
	border: 1px solid gray;
	background-color: white;
	color: gray;
}
th{text-align: center;}
#partDeleteBtn{
	border: 1px solid gray;
	background-color: white;
	color: gray;
}
#editBtn{
	border: 1px solid lightgray;
	background-color: white;
	color: gray;
}
</style>
<c:set var="sumTotalPrice" value="0"/>
<c:set var="dvrfee" value="2500" />
<div id="indexPage" style="margin-bottom: 2%;">
	<div class="row">
		<div class="col-sm-12" style="margin-bottom:5%;">
		<h2 style="color: gray; text-align:center; margin: 3% 0;">cart</h2>
		<form name="cartOrderForm">
			<input type="hidden" name="pcode">
			<table style="width:100%; margin-top: 3%;">
				<thead>
					<tr style="text-align: center;border-top:3px double lightgray;">
						<th>
							<label for="allSelect" style="font-size: 0.8em;vertical-align: middle; margin-right: 1%;">전체선택</label><br/>
							<input name="" id="allSelect" type="checkbox" />
						</th>
						<th>Image</th>
						<th>상품명</th>
						<th>단가</th>
						<th>옵션</th>
						<th>주문수량</th>
						<th>합계</th>
						<th>삭제</th>
					</tr>
				</thead>
				<tbody align="center" style="border-top: 3px double lightgray; border-bottom: 3px double lightgray; margin-bottom:5%;">
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
								<td style="width: 5%;"><input type="checkbox" name="postCart" class="check" value="${cart.cart_idx }"></td>
								<td style="width: 15%;"><img onClick="goItemDetail('${cart.fk_pcode }');" src="images/${cart.product.mainimg1 }" style="width:200px;height:200px;"/></td>
								<td style="width: 15%; font-weight:bold;">${cart.product.pname }</td>
								<td style="width: 10%;"><fmt:formatNumber value="${cart.product.price }" pattern="###,###" />원</td>
								<td style="width: 10%;">${cart.ocolor } / ${cart.osize }</td>
								<td style="width: 10%;">
									<input type="hidden" class="pqty" value="${cart.pqty }">
									<input class="oqtyEdit" id="oqty${status.index }" value="${cart.oqty }" style="width: 39px; margin-bottom: 1%;" />
									<button type="button" id="editBtn"  onClick="qtyEdit(${cart.cart_idx},'${status.index }');">수량 변경</button>
								</td>
								<c:set var="totalPrice" value="${cart.product.price*cart.oqty }" />
								<td style="width:10%;font-weight:bold;"><fmt:formatNumber value="${totalPrice }" pattern="###,###" />원</td>
								<td style="width: 10%;"><button id="partDeleteBtn" onClick="goDelete('${cart.cart_idx}')">X</button></td>
							</tr>
						</c:forEach>
					</c:if>
					<tr style="border-top: 3px double lightgray; line-height: 300%;">
						<c:if test="${sumTotalPrice>100000 || sumTotalPrice==0}">
							<c:set var="dvrfee" value="0" />
						</c:if>
						<td colspan="5"></td>
						<td colspan="3" style="text-align:right;">
						상품금액 : <fmt:formatNumber value="${sumTotalPrice}" pattern="#,###"/> won 
						+ 배송비 : <fmt:formatNumber value="${dvrfee}" pattern="#,###"/> won = 
						<span style="font-weight: bold;">총금액 : 
						<fmt:formatNumber value="${sumTotalPrice + dvrfee }" pattern="#,###"/> won
						</span></td>
					</tr>
				</tbody>
			</table>
			<div style="margin-top:3%;">
					<button type="button" class="deleteBtn" style="width: 45%;">삭제하기</button>
					<button type="button" class="orderBtn" onClick="goOrder();" style="width: 45%;">주문하기</button>
				</div>
			</div>
		</form>
		</div>
	</div>
</div>
<jsp:include page="../footer.jsp" />