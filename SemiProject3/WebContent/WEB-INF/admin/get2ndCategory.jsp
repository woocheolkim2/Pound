<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${category ==null}">
	<option value="0">첫분류를 선택하세요</option>
</c:if>

<c:if test="${category !=null}">
	<c:forEach items="${category.cg2ndList}" var="cg2">
	  	 <option value="${cg2.cg_2nd_code }">${cg2.cg_2nd_name }</option>
	</c:forEach>
</c:if> 