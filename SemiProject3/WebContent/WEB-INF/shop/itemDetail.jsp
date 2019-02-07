<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../header.jsp" />

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="/resources/demos/external/jquery-mousewheel/jquery.mousewheel.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#size").change(function(){
			var form_data = {"pcode":"${item.pcode}","color":$("#color").val(),"size":$(this).val()};
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
			});
		});
		$("#color").bind("change",function(){
			var form_data ={"pcode":"${item.pcode}","color":$(this).val()};
			$.ajax({
				url:"sizeOfColor.do",
				type:"POST",
				data:form_data,
				dataType:"HTML",
				success:function(html){
					$("#size").empty().html(html);
				},
				error:function(){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				}
			});
		});
		$("#orderNow").click(function(){
			var color = $("#color").val();
			var size = $("#size").val();
			if(color=="0"||size=="0"){
				alert("필수 옵션을 선택하세요");
				return;
			}
			var member = "${loginMember.userid}";
			if(member==""){
				alert("로그인이 필요합니다.");
				location.href="login.do";
				return;
			}
			if(size=="soldout"){
				alert("선택하신 제품이 품절되어 주문이 불가합니다.");
				location.reload();
				return;
			}
			goOrder();
			
		});
		$("#addCart").click(function(){
			var color = $("#color").val();
			var size = $("#size").val();
			if(color=="0"||size=="0"){
				alert("필수 옵션을 선택하세요");
				return;
			}
			var member = "${loginMember.userid}";
			if(member==""){
				alert("로그인이 필요합니다.");
				location.href="login.do";
				return;
			}
			if(size=="soldout"){
				alert("선택하신 제품이 품절되어 주문이 불가합니다.");
				location.reload();
				return;
			}
			var form_data = {"pcode":"${item.pcode}","color":color,"psize":size};
			$.ajax({
				url:"cartCheck.do",
				type:"POST",
				data:form_data,
				dataType:"JSON",
				success:function(json){
					var bool = json.cartCheck;
					if(bool==true){
						var yn = confirm("이미 동일한 상품이 장바구니에 존재합니다. 추가하시겠습니까?");
						if(!yn) return;
						else addCart();
					}
					else addCart();
				},
				error:function(request, status, error){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				}
			});
		});
		$("#qnaBtn").click(function(){
			var yn = confirm("문의글을 등록 하시겠습니까?");
			if(!yn) return;
			var form_data = {"userid":"${loginMember.userid}"
							,"pcode":"${item.pcode}"
							,"content":$("#qnaContent").val()};
			$.ajax({
				url:"qnaInsert.do",
				type:"POST",
				data:form_data,
				dataType:"JSON",
				success:function(json){
					var n = json.n;
					if(n=="1"){
						alert("문의 등록이 성공 되었습니다.");
						$("#qnaContent").val("");
						location.reload();
						return;
					}
				},
				error:function(request, status, error){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				}
			});
		});
		$("#likeBtn").click(function(){
			var form_data = {"pcode":"${item.pcode}"};
			$.ajax({
				url: "likeCnt.do",
				type:"POST",
				data:form_data,
				dataType:"JSON",
				success:function(json){
					if(json.login!="true"){
						alert("로그인후 이용가능합니다.");
						location.href="login.do";
						return;
					}
					else if(json.n=="0"){
						confirm("이미 좋아요 하신 상품입니다. 취소하시겠습니까?");
						return;
					}
					else{
						alert("좋아요 상품에 추가 되었습니다.");
						$("#likeUP").text(json.likecnt);
						return;
					}
				},
				error:function(request, status, error){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				}
			});
		});
		$(".spinnerOqty").spinner({
	         spin : function(event, ui) {
	        	var color = $("#color").val();
	 			var size = $("#size").val();
	 			if(color=="0"||size=="0"){
	 				alert("옵션을 선택하세요");
	 				return false;
	 			}

				var pqty = $("#checkpqty").val();
	            if (ui.value > pqty) {
	            	alert("재고가 부족합니다 "+pqty+"개 이하로 주문하여 주십시오.");
	               $(this).spinner("value", pqty);
	               return false;
	            } 
	            else if (ui.value < 0) {
	               $(this).spinner("value", 0);
	               return false;
	            }
	         }
		}); // end of $("#spinnerImgQty").spinner();--------
	});
	function addCart(){
		var frm = document.orderForm;
		frm.action="cartAdd.do";
		frm.method="POST";
		frm.submit();
	}
	function goOrder(){
		var frm = document.orderForm;
		frm.action="order.do";
		frm.method="POST";
		frm.submit();
	}
</script>
<style>
tr>td{
	color:gray;
	line-height: 200%;
	text-align: center;
}
th{
	text-align: center;
}
select{
	background-color: white;
	border:1px solid lightgray;
}
#qnaBtn{
	margin-left: 3%; 
	align: right; 
	width: 97%; 
	border: none; 
	background-color: rgb(54,54,54); 
	height: 40px; 
	color: white;
	margin-top: 0;
}
</style>
<div style="max-width:1280px; margin: 0 auto;">
	<div class="row" style="margin: 3% auto; ">
		<form name="orderForm">
		<input type="hidden" name="postpcode" value="${item.pcode }">
		<input type="hidden" id="checkpqty">
		<div class="col-sm-10 col-sm-offset-1" style="border-top: 4px double gray;border-bottom: 4px double gray;padding: 2% 0;">
			<div class="col-sm-offset-1 col-sm-5" style="padding:0; overflow:hidden;">
				<img src="images/${item.mainimg1 }" width="100%"> 
			</div>
			<div class="col-sm-6">
				<h2 align="center" style=" padding-bottom: 3%;">${item.pname }</h2>
				<table id="itemInfo" style="padding: 3%; width:100%; margin-left: 0; width: 98%;">
					<tr style="height: 20px;border-bottom: 1px solid #e6e6e6;"><td colspan="2"> </td></tr>
					<tr style="height: 20px;"><td colspan="2"> </td></tr>
					<tr>
						<td>code : </td>
						<td>${item.pcode }</td>
					</tr>
					<tr>
						<td>price : </td>
						<td><fmt:formatNumber value="${item.price }"/> won</td>
					</tr>
					<tr style="height: 20px;border-bottom: 1px solid #e6e6e6;"><td colspan="2"> </td></tr>
					<tr style="height: 20px;"><td colspan="2"> </td></tr>
					<tr>
						<td>color : </td>
						<td>
							<select id="color" name="color" style="width: 60%;">
								<option value="0">==== 선택하세요 ====</option>
							<c:forEach items="${colorList }" var="color">
								<option value="${color }">${color }</option>
							</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td>size : </td>
						<td>
							<select id="size" name="size" style="width: 60%;">
								<option value="0">색상을 선택해 주세요.</option>
							</select>
						</td>
					</tr>
					<tr style="height: 20px;border-bottom: 1px solid #e6e6e6;"><td colspan="2"> </td></tr>
					<tr style="height: 20px;"><td colspan="2"> </td></tr>
					<tr>
						<td>주문수량  : </td>
						<td>
						<input id="spinnerOqty" class="spinnerOqty" name="oqty" value="1" readonly/> 개
						</td>
					</tr>
					<tr style="height: 20px;border-bottom: 1px solid #e6e6e6;"><td colspan="2"> </td></tr>
					<tr style="height: 20px;"><td colspan="2"> </td></tr>
					<tr>
						<td colspan="2" align="center" style="padding-left: 3%;">
							<button type="button" id="orderNow" class="btn btn-primary" style="background-color: black; color:white; border:none; width:40%; height: 40px;float:left;">바로구매</button>
							<button type="button" id="addCart" class="btn btn-primary" style="background-color: gray; color:white; border:none; width: 35%; margin-left: 3%;height: 40px;float:left;">장바구니</button>
							<button type="button" id="likeBtn" class="btn btn-primary" style="background-color: lightgray; color:white; border:none; width: 15%; margin-left: 3%;height: 40px;float:left;">
							<img src="images/icon/red_like.png" style="width: 15px;height:15px; margin-right: 15%;"><span id="likeUP">${likecnt}</span></button>
						</td>
					</tr>
				</table>
			</div>
			<div class="col-sm-offset-1">
			</div>
		</div>
		</form>
		<div style="width:80%; margin-left:10%;">
		<div class="row" align="center" id="imagesScroll">
			<div class="col-sm-12" style="border-bottom: 3px double gray; padding: 3% 0;">
				<c:if test="${imgList==null }">
					<div>상세 이미지 준비중입니다.</div>
				</c:if>
				<c:if test="${imgList !=null}">
					<c:forEach items="${imgList}" var="pImg">
						<img src="images/${pImg }" style="width:80%;">
						<br/>
					</c:forEach>
				</c:if>
			</div>
		</div>
		<div class="row" id="qnaScroll" align="center">
			<h2 style="margin-top: 3%;">Q&A</h2>
			<div class="col-sm-12" style="border-bottom: 3px double gray; padding: 3% 0;">
				<table style="width: 80%;text-align:center; border-bottom: 3px double lightgray;">
					<tr style="line-height: 250%;border-bottom: 3px double lightgray;">
						<th>글번호</th>
						<th>작성자</th>
						<th>내용</th>
						<th>작성일자</th>
					</tr>
					<c:if test="${memoList==null }">
						<tr>
							<td colspan="4" style="line-height:400%;">등록된 Q&A가 없습니다.</td>
						</tr>
					</c:if>
					<c:if test="${memoList !=null}">
						<c:forEach items="${memoList}" var="memo">
						<tr>
							<td>${memo.memo_idx }</td>
							<td>${memo.fk_userid }</td>
							<td>${memo.memo_content }</td>
							<td>${memo.memo_writedate }</td>
						</tr>
						</c:forEach>
					</c:if>
				</table>
				<c:if test="${loginMember!=null }">
				<form name="memoFrm">
				<div class="row" style="align: center; width: 82%;">
					<div class="col-sm-12" style="margin-top: 3%;">
						<%-- <h5>${loginMember.userid }</h5> --%>
						<textarea id="qnaContent" style="width: 100%; height: 80px; border: 1px solid lightgray; background-color: white;"></textarea>
					</div>
				</div>
				<div class="row" style="width: 100%; margin-top: 0.5%;">
					<div class="col-sm-3 col-sm-offset-8" style="padding-right: 1.9%;">
						<button type="button" id="qnaBtn"><img src="images/checked.png" style="width: 20px; height: 20px; margin-right: 15%;"/>문의 등록하기</button>
					</div>
				</div>
				</form>
				</c:if>
			</div>
		</div>
		<div class="row" id="reviewScroll" align="center" style="margin-bottom: 5%;">
			<h2 style="margin-top: 3%;">Review</h2>
			<div class="col-sm-12" style="border-bottom: 3px double gray; padding: 3% 0;">
				<table style="width: 80%;text-align:center;">
					<tr style="line-height: 250%; border-bottom: 3px double lightgray">
						<th>글번호</th>
						<th>작성자</th>
						<th>내용</th>
						<th>작성일자</th>
					</tr>
					<c:if test="${reviewList==null }">
						<tr>
							<td colspan="4">등록된 리뷰가 없습니다.</td>
						</tr>
					</c:if>
					<c:if test="${reviewList !=null}">
						<c:forEach items="${reviewList}" var="review">
						<tr>
							<td>${review.REVIEW_IDX}</td>
							<td>${review.FK_USERID }</td>
							<td>${review.REVIEW_CONTENT }</td>
							<td>${review.REVIEW_WRITEDATE }</td>
						</tr>
						</c:forEach>
					</c:if>
				</table>
				<c:if test="${loginMember!=null }">
				<div class="row" style="align: center; width: 82%;">
					<div class="col-sm-12" style="margin-top: 3%;">
						<%-- <h5>${loginMember.userid }</h5> --%>
						<textarea id="qnaContent" style="width: 100%; height: 80px; border: 1px solid lightgray; background-color: white;"></textarea>
					</div>
				</div>
				<div class="row" style="width: 100%; margin-top: 0.5%;">
					<div class="col-sm-3 col-sm-offset-8" style="padding-right: 1.9%;">
						<button type="button" id="qnaBtn"><img src="images/checked.png" style="width: 20px; height: 20px; margin-right: 15%;"/>문의 등록하기</button>
					</div>
				</div>
				</c:if>
			</div>
		</div>
	</div>
</div>
</div>
<jsp:include page="../footer.jsp" />