<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<input type="hidden" id="ajaxUSEuserid" value="${isUSEuserid }">
<c:if test="${isUSEuserid == false }">
	<span style="font-weight: bold; color: #ff7174;">사용중인 아이디 입니다.</span>
</c:if>
<c:if test="${isUSEuserid == true }">
	<span style="font-weight: bold; color: blue;">이 아이디는 사용 가능 합니다.</span>
</c:if>