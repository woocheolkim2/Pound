<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach items="${sizeList }" var="size" varStatus="status">
	<option value="0">==== 옵션을 선택하세요 ====</option>
	<c:if test="${size.pqty >0 }">
		<option class="pqty" value="${size.psize }">${size.psize }</option>
	</c:if>
	<c:if test="${size.pqty <=0 }">
		<option value="soldout" style="color: lightgray; text-decoration: line-throgh;">
			${size.psize } [품절]
		</option>
	</c:if>
</c:forEach>