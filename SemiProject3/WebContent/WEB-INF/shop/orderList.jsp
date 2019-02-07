<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<jsp:include page="../header.jsp" />
<script>
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
.oqtyEdit{
	border: 1px solid lightgray;
	background-color: white;
	color: gray;
}
</style>
<div id="indexPage" style="margin-bottom: 2%; width: 80%;">
	<div class="row">
		<div class="col-sm-12" style="margin-bottom:5%;">
			<h2 style="color: gray; text-align:center; margin: 3% 0;">Order</h2>
			<c:if test="${orderList == null }">
				<table style="width:100%; margin-top: 3%;">
					<tr style="padding: 5% 0;">
						<td colspan="8" align="center" style="line-height: 500%;">
							<img src="images/icon/bubble.png"><br/>
							<span>주문 목록이 존재하지 않습니다.</span>
						</td>
					</tr>
				</table>
			</c:if>
			<c:if test="${orderList != null }">
				<table style="width:100%; margin-top: 3%;">
					<thead>
						<tr style="text-align: center;border-top:3px double lightgray;">
							<th style="align:left;"><h5>주문번호</h5></th>
							<th>이미지</th>
							<th>상품명</th>
							<th>상품가격</th>
							<th>옵션</th>
							<th>구매수량</th>
							<th>총금액</th>
							<th>적립금</th>
							<th>배송정보</th>
							<th>주문총액</th>
						</tr>
					</thead>
					<tbody align="center" style="border-top: 3px double lightgray; border-bottom: 3px double lightgray; margin-bottom:5%;">
					<c:forEach items="${orderList }" var="orderList">
						<tr style="border-top: 2px solid lightgray;"><td colspan="9"></td></tr>
						<c:forEach items="${orderList.orderDetailList }" var="order" varStatus="status">
							<tr style="padding-left: 5%;">
								<c:if test="${status.index==0 }">
									<td rowspan="${fn:length(orderList.orderDetailList) }" style="width: 12%;"><a href="#">${orderList.odrcode }<br>[${orderList.orderdate }]</a></td>
								</c:if>
								<td style="width: 10%;"><img src="images/${order.pvo.mainimg1 }" style="width: 120px; height: 120px;"/></td>
								<td style="width: 13%; font-weight:bold;">${order.pvo.pname }</td>
								<td style="width: 10%;"><fmt:formatNumber value="${order.pvo.price }" pattern="###,###" />원</td>
								<td style="width: 10%;">${order.ocolor }/${order.osize }</td>
								<td style="width: 5%;">${order.oqty }</td>
								<td style="width: 10%;font-weight:bold;"><fmt:formatNumber value="${order.totalPrice }" pattern="###,###" />원</td>
								<td style="width: 10%; color: navy">${Math.round(order.pvo.price*order.oqty/100) } POINT</td>
								<td style="width: 10%;"><button id="partDeleteBtn">배송조회</button></td>
								<c:if test="${status.index==0 }">
									<td rowspan="${fn:length(orderList.orderDetailList) }" style="width: 12%;font-weight:bold;"><fmt:formatNumber value="${orderList.sumtotalprice }" pattern="###,###" />won</td>
								</c:if>
							</tr>
						</c:forEach>
					</c:forEach>					
					</tbody>
				</table>
			</c:if>
		</div>
	</div>
</div>
<jsp:include page="../footer.jsp" />