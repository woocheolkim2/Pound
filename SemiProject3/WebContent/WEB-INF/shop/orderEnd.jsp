<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="../header.jsp" />
<div align="center">
	<div style="border: 1px solid lightgray;margin:3% 0; width: 30%; padding: 3% 0;">
		<span style="font-weight: bold; font-size: 1.2em;">[${odrcode }]</span>의<br/> 주문이 완료되었습니다.
	</div>
</div>
<div align="center" style="width: 30%; margin: 0 auto;">
	<button type="button" style="border: 1px solid lightgray; width:200px; padding: 2% 1%; background-color: white;">홈으로 가기</button>
	<button type="button" onClick="javascript:location.href='orderList.do';" style="border:none; width:200px; padding: 2% 1%; background-color: gray; color: white;">주문내역 확인</button>
</div>
<jsp:include page="../footer.jsp" />