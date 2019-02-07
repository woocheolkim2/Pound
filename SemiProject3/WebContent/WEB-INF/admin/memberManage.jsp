<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">

	$(document).ready(function(){
		$("#keyword").keyup(function() {
			var k = $(this).val(); 
			$("table#member > tbody > tr").hide(); 
			var temp = $("table#member > tbody > tr > td:nth-child(even):contains('" + k + "')");
			console.log(temp);
            $(temp).parent().show();
        });
	});

	function showDetail(idx){
		
		var $target = event.target;
		console.log($target.nodeName + '이거랑'+$target.parentNode.nodeName);
		if($target.parentNode.nodeName == 'TD' ){
			event.stopPropagation(); 
            return;
		}
		
		var url = "showMemberDetail.do?idx="+idx;
		// 팝업창 띄우기
		window.open(url, "회원정보보기", "width=550px, height=600px, top=50px, left=100px");
	}
	function goDel(idx) {      
      var bool = confirm(idx+" 번 회원을 정말로 삭제 하시겠습니까?");
      
      if(bool) {
         var frm = document.idxFrm;
         frm.idx.value = idx;
         
         frm.method = "POST";
         frm.action = "memberDelete.do";
         frm.submit();
      }      
   }// end of function goDel(idx)--------------------------
   function goEnable(idx) {
      var bool = confirm(idx+" 번 회원을 정말로 휴면 해제(활성화) 하시겠습니까?");
      
      if(bool) {
         var frm = document.idxFrm;
         frm.idx.value = idx;
         
         frm.method = "POST";
         frm.action = "memberIdleClear.do";
         frm.submit();
      }
   }// end of function goEnable(idx)-------------
   
   
   /////////////////////////////////////////////////////////////
   
</script>
<section id="member">
		<div class="container">
			<h3>::: 회원전체 정보보기 :::</h3>
			<div align="center">
				이름 : <input type="text" id="keyword" />
			</div>
			<table id="member" class="outline members"> 
				<thead> 
	             	<tr>
						<th class="th">회원번호</th>
						<th class="th">회원명</th>
						<th class="th">아이디</th>
						<th class="th">이메일</th>
						<th class="th">연락처</th>
						<th class="th">마지막 로그인 날짜</th>
						<th class="th">관리</th>
					</tr>
				</thead>
				<tbody>
				<c:if test="${memberList == null }">
					<tr>
						<td class="th" colspan="7">가입된 회원이 없습니다.</td>
					</tr>
				</c:if>
				<c:if test="${memberList != null && membervo.userid ne 'admin' }">   
		        	<c:forEach var="membervo" items="${memberList}">  
		        	<c:if test="${membervo.requireLastLogin == true}">
	                 	<tr style="background-color: yellow;" id="registedMember" onClick="showDetail('${membervo.member_idx}')" >
		            </c:if>
		            <c:if test="${membervo.requireLastLogin == false}">
		             	<tr id="registedMember" onClick="showDetail('${membervo.member_idx}')" >
		            </c:if>
	                        <td class="td">${ membervo.member_idx}</td>
	                        <td class="td">${ membervo.username}</td>
	                        <td class="td">${ membervo.userid}</td>
	                        <td class="td">${ membervo.email}</td>
	                        <td class="td">${ membervo.allHp}</td>
	                        <td class="td">${ membervo.lastLoginDate}</td>
	                        <c:if test="${membervo.requireLastLogin eq false }">
	                       		<td><button type="button" onClick="goDel('${membervo.member_idx}');">삭제</button></td>
	                        </c:if>
	                        <c:if test="${membervo.requireLastLogin eq true }">
	                       		<td>
	                       			<button type="button" onClick="goEnable('${membervo.member_idx}');">활성화</button>
	                       			<button type="button" onClick="goDel('${membervo.member_idx}');" style="margin-left:20px;">삭제</button>
	                       		</td>
	                        </c:if>
	                      </tr> 
	                      
	               </c:forEach>
	            </c:if>
				</tbody>
			</table>
		</div>
	</section>
	<div id="blank">
		<form name="idxFrm">
	       <input type="hidden" name="idx" />
	    </form>
	</div> 
	
</body>
