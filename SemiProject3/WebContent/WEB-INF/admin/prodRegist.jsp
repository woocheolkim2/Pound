<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
table#tblProdInput {
   border: solid gray 1px;
   border-collapse: collapse;
}

table#tblProdInput td {
   border: solid gray 1px;
   padding-left: 10px;
   padding-right: 10px;
   height: 50px;
}

.prodInputName {
   text-align: center;
   font-weight: bold;
}

.error {
   color: red;
   font-weight: bold;
   font-size: 9pt;
}
</style>


<script type="text/javascript">
   $(document).ready(function() {
      
	  $("#errorProd").hide();
	  
	  $("#displayList").hide();
      $(".error").hide();
      $("#displayPsizeList").hide();
      $("#displayColorList").hide();

      
      $(".spinnerPqty").spinner({
         spin : function(event, ui) {
            if (ui.value > 100) {
               $(this).spinner("value", 100);
               return false;
            }
            else if (ui.value < 1) {
               $(this).spinner("value", 1);
               return false;
            }
         }
      }); // end of $("#spinnerPqty").spinner();---------------

      $("#spinnerImgQty").spinner({
         spin : function(event, ui) {
            if (ui.value > 10) {
               $(this).spinner("value", 10);
               return false;
            } 
            else if (ui.value < 0) {
               $(this).spinner("value", 0);
               return false;
            }
         }
      }); // end of $("#spinnerImgQty").spinner();---------------

      $("#spinnerImgQty").bind("spinstop", function() {
      //==== 스피너는 이벤트가 "change" 가 아니라 "spinstop" 이다. ====//

         var html = "";

         var spinnerImgQtyVal = $("#spinnerImgQty").val();

         if (spinnerImgQtyVal == "0") {
            $("#divfileattach").empty();
            $("#attachCount").val("");
            return;
         } 
         else {
            for (var i = 0; i < parseInt(spinnerImgQtyVal); i++) {
               html += "<br/>";
               html += "<input type='file' name='attach"+i+"' class='btn btn-default' />";
            }// end of for------------- 

            $("#divfileattach").empty();
            $("#divfileattach").append(html);
            $("#attachCount").val(spinnerImgQtyVal);
         }
      });
      
      $("#addButton").bind("click", function() {
         //==== 스피너는 이벤트가 "change" 가 아니라 "spinstop" 이다. ====//

         var html = "";   
         
         
         html += "<br/>";
         html += "<input type='text' name='color' />&nbsp;<input type='text' name='psize' />&nbsp;"+
         		"<input class='spinnerPqty' name='pqty' value='수량' style='width: 30px; height: 20px;'>개"+
         		"&nbsp;<button type='button' class='removebtn'>삭제</button>"+
         		"<span class='error'>필수입력</span>";
         		
            /* "<select name='pspec' class='infoData' style='width: 300px;'> <option value=''>색상</option></select> <select   name='pspec' class='infoData' style='width: 300px;'> <option value=''>사이즈</option></select> <input id='spinnerPqty' name='pqty' value='수량' style='width: 30px; height: 20px;'><button type='button' class='removebtn'>삭제</button>"; */
         $("#colorsizeiqty").append(html); 
         
         $(".removebtn").click(function(){
            $(this).prev().prev().prev().prev().detach();
            $(this).prev().prev().prev().detach();
            $(this).prev().prev().detach();
            $(this).prev().detach();
            $(this).detach();
         });
         $(".spinnerPqty").spinner({
             spin : function(event, ui) {
            	 alert("asd");
                if (ui.value > 100) {
                   $(this).spinner("value", 100);
                   return false;
                }
                else if (ui.value < 1) {
                   $(this).spinner("value", 1);
                   return false;
                }
             }
          }); // end of $("#spinnerPqty").spinner();---------------
         $(".error").hide();
         
      });
      
      $("#btnRegister").bind("click", function() {
         var flag = false;   
         if($("#isNew").val==1){
            $(".infoData").not(".onlynew").each(function() {
               var val = $(this).val();
               if (val == "") {
                  $(this).next().show();
                  flag = true;
                  return false;
               }
            });
         }
         else{
            $(".infoData").each(function() {
               var val = $(this).val();
               if (val == "") {
                  $(this).next().show();
                  flag = true;
                  return false;
               }
            });  
         }
         if (flag == true) {
            event.preventDefault();
            return;
         } 
         else {
            alert("제발좀");
            var frm = document.prodInputFrm;
            frm.action="<%=request.getContextPath()%>/productRegisterEnd.do";
            frm.submit();
         }
      });
      
      // *** 제품명 중복확인 *** //
      $("#pname").keyup(function() {
    	  $("#isNew").val("0");
    	  $("#pcompany").addClass("infoData");
    	  $("#descProda").addClass("infoData");
    	  $("#priceProda").addClass("infoData");
    	  $("#mainImg1").addClass("infoData");
    	  $("#mainImg2").addClass("infoData");
    	  
    	  $("#pcompany").parent().parent().show();
    	  $("#descProda").parent().parent().show();
    	  $("#priceProda").parent().parent().show();
    	  $("#mainImg1").parent().parent().show();
         // 팝업창 띄우기
         /* var url = "prodDuplicateCheck.do";
         window.open(url, "prodCheck"
                 , "left=800px, top=200px, width=500px, height=320px"); */

          var form_data = {pname:$("#pname").val()
                		 	,cg_1st_code:$("#cg_1st_code").val()
                		 	,cg_2nd_code:$("#cg_2nd_code").val()};
           $.ajax({
        	  url : "prodDuplicateCheck.do",
        	  type : "GET",
        	  data : form_data,
        	  dataType : "JSON",
        	  success : function(data){
        		  if(data.length>0){
        			  var resultHTML = "";
            		  $.each(data,function(engthIndex,entry){
            			 
            			  var wordstr = entry.searchwordresult.trim();
							var index = wordstr.toLowerCase().indexOf( $("#pname").val().toLowerCase() );
							//  alert("index : " + index);
							var len = $("#pname").val().length;
							var result = "";
							
							result = "<span class='first' style='color:blue;'>" +wordstr.substr(0, index)+ "</span>" 
							+ "<span class='second' style='color:red; font-weight:bold;'>"+wordstr.substr(index, len)+ "</span>" + 
							"<span class='third' style='color:blue;'>" +wordstr.substr(index+len, wordstr.length - (index+len) )+ "</span>";  
							
							resultHTML += "<span style='cursor:pointer;'>"+ result +"</span>("+entry.fullCategory+")<br/>"; 
						});
						
						$("#displayList").html(resultHTML);
						$("#displayList").show();
						
						$("#errorProd").show();
						
					}
					else { // 검색된 데이터가 없는 경우
						$("#displayList").hide();
						$("#errorProd").hide();
						
					}
        	},// end of success
               error: function(request, status, error){
                 alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
             }
        	  
        	  
          });
                 
                 
      });
  	
		$("#displayList").click(function(event){
			
			var word = "";
			var $target = $(event.target);
			
			if($target.is(".first")) {
				word = $target.text() + $target.next().text() + $target.next().next().text();
			}
			else if($target.is(".second")) {
				word = $target.prev().text() + $target.text() + $target.next().text();
			}
			else if($target.is(".third")) {
				word = $target.prev().prev().text() + $target.prev().text() + $target.text();
			}
			
			$("#pname").val(word); // 텍스트박스에 검색된 결과의 문자열을 입력해준다.
			 $("#errorProd").hide();

			 $("#pcompany").removeClass("infoData");
	    	  $("#descProda").removeClass("infoData");
	    	  $("#priceProda").removeClass("infoData");
	    	  $("#mainImg1").removeClass("infoData");
	    	  $("#mainImg2").removeClass("infoData");
	    	  
	    	  $("#pcompany").parent().parent().hide();
	    	  $("#descProda").parent().parent().hide();
	    	  $("#priceProda").parent().parent().hide();
	    	  $("#mainImg1").parent().parent().hide();
	    	  $("#isNew").val("1");
	    	  
	    	  
	    	  
			$(this).hide();
			
			
		});
      
		$("#pcompany").keyup(function(event){
		
			
		 
			 $("#displayList").hide();
			 $("#errorProd").hide();
			 $("#displayList").hide();
		      $(".error").hide();

    	   $("#colorSelect").hide();
    	   $("#psizeSelect").hide();
    	   $("#colorText").show();
    	   $("#psizeText").show();
		 
		 
			 
		 })
		
		
      // *** 제품명 중복확인 *** //
     // $("#prodCheck").click(function() {
         
         // 팝업창 띄우기
         /* var url = "prodDuplicateCheck.do";
         window.open(url, "prodCheck"
                 , "left=800px, top=200px, width=500px, height=320px"); */
      //});
      
      $("#cg_1st_code").change(function(){
             
           <%--  var frm = document.prodInputFrm;
          
             frm.action="<%=request.getContextPath()%>/admin/get2ndCategory.do";
             frm.submit(); --%>
             $("#psizeSelect").empty();
             $("#colorSelect").empty();
             $("#pcompany").addClass("infoData");
	       	  $("#descProda").addClass("infoData");
	       	  $("#priceProda").addClass("infoData");
	       	  $("#mainImg1").addClass("infoData");
	       	  $("#mainImg2").addClass("infoData");
	       	  
	       	  $("#pcompany").parent().parent().show();
	       	  $("#descProda").parent().parent().show();
	       	  $("#priceProda").parent().parent().show();
	       	  $("#mainImg1").parent().parent().show();
             
             $("#pname").val("");
             var form_data = {cg_1st_code: $("#cg_1st_code").val()};
             
             $.ajax({
                
                url: "get2ndCategory.do",
                type : "GET",
                data : form_data,
                dataType :"html",
                success : function(html){
                   $("#cg_2nd_code").empty().html(html);
                },
                error: function(request, status, error){
                  alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
              }
               
             });
             
         });
      
      
      $("#cg_2nd_code").change(function(){
    	  $("#psizeSelect").empty();
          $("#colorSelect").empty();
    	  $("#pname").val("");
    	  $("#pcompany").addClass("infoData");
    	  $("#descProda").addClass("infoData");
    	  $("#priceProda").addClass("infoData");
    	  $("#mainImg1").addClass("infoData");
    	  $("#mainImg2").addClass("infoData");
    	  
    	  $("#pcompany").parent().parent().show();
    	  $("#descProda").parent().parent().show();
    	  $("#priceProda").parent().parent().show();
    	  $("#mainImg1").parent().parent().show();
      });
        
     
      
   }); // end of $(document).ready();--------------
</script>

<div align="center" style="margin-bottom: 20px;">

   <div style="border: solid green 2px; width: 200px; margin-top: 20px; padding-top: 10px; padding-bottom: 10px; border-left: hidden; border-right: hidden;">
      <span style="font-size: 15pt; font-weight: bold;">제품등록[ADMIN]</span>
   </div>
   <br />

   <form name="prodInputFrm" enctype="multipart/form-data">
   	  <input type="hidden" id="isNew" name="isNew" value="0">
      <table id="tblProdInput" style="width: 80%;">
            <tr>
               <td width="50%" class="prodInputName" style="padding-top: 10px;">대분류</td>
               <td align="left" style="padding-top: 10px;">
	               <select id="cg_1st_code" name="cg_1st_code" class="infoData" style="width: 300px;">
	                     <option value="" >:::선택하세요:::</option>
	                     
	                     <c:forEach items="${categoryList }" var="categoryVO" >
	                        <option value="${categoryVO.cg_1st_code }" > ${categoryVO.cg_1st_name } </option>
	                     </c:forEach>
	                     
	               </select> 
	                     <input type="hidden" name="index" />
	               <span class="error">필수입력</span>
               </td> 
                  <td width="50%" class="prodInputName" style="padding-top: 10px;">중분류</td>
                  <td align="left" style="padding-top: 10px;">
                  <select id="cg_2nd_code"  name="cg_2nd_code" class="infoData" style="width: 300px;">
                        <c:if test="${cg2ndList ==null}">
                           <option value="0">첫분류를 선택하세요</option>
                        </c:if>
                        
                  </select>
                
               <span class="error">필수입력</span>
               </td>
            </tr>
            <tr>
               <td width="25%" class="prodInputName">제품명</td>
               <td colspan="3" width="75%" align="left">
               <input type="text" style="width: 300px;" id="pname" name="pname"class="box infoData" />
               <span style="color:red;" id="errorProd"> 기존제품 등록을 원하시면 목록에서 선택해주세요!</span>
               <div id="displayList" style="max-height: 60px; overflow: auto; margin-top: 0.5%; border: 1px solid gray;"> </div>
               <%-- 
               	<button type="button" id="prodCheck">중복확인</button>
               --%>
           
               <span class="error">필수입력</span></td>
            </tr>
            <tr class="onlynew">
               <td width="25%" class="prodInputName">제조사</td>
               <td colspan="3" width="75%" align="left">
               <input type="text" style="width: 300px;" id="pcompany" name="pcompany" class="box infoData" />
               <span class="error">필수입력</span></td>
            </tr>
            
            <tr class="onlynew">
               <td width="25%" class="prodInputName mainImgProd">제품이미지</td>
               <td colspan="3" width="75%" align="left">
               <input type="file" id="mainImg1" name="mainImg1" class="infoData" />
               <span class="error">필수입력</span>
               <input type="file" id="mainImg2" name="mainImg2" class="infoData" />
               <span class="error">필수입력</span></td>
            </tr>
            <tr>
               <td width="25%" class="prodInputName" style="padding-left: 10px;">제품색상/사이즈/수량</td>
               <td id="colorsizeiqty"colspan="3" width="25%" align="left">
               <!-- <select   id="colorSelect" name="color" class="infoData" style="width: 200px;">
               </select> -->
               <input id="colorText" type="text" name="color" />
               
               <!-- <select   id="psizeSelect" name="psize" class="infoData" style="width: 200px; ">
               </select>  -->
               <input id="psizeText" type="text" name="psize" />
               
               <input class="spinnerPqty" name="pqty" value="수량" style="width: 30px; height: 20px;">개
               <span class="error">필수입력</span>
               <button type="button" id="addButton" >추가</button>
               </td>
            </tr>
            <tr class="onlynew">
               <td width="25%" class="prodInputName priceProd">제품정가</td>
               <td colspan="3" width="75%" align="left">
               <input type="text" style="width: 100px;" name="price" id="priceProda" class="box infoData" value=""/> 원 
               <span class="error">필수입력</span></td>
            </tr>
            <tr class="onlynew">
               <td width="25%" class="prodInputName descProd">제품설명</td>
               <td colspan="3" width="75%" align="left">
               <textarea id="descProda" name="pcontents" rows="5" cols="60"></textarea></td>
            </tr>

            <%-- ==== 첨부파일 타입 추가하기 ==== --%>
            <tr>
               <td width="25%" class="prodInputName" style="padding-bottom: 10px;">추가이미지파일(선택)</td>
               <td colspan="3"><label for="spinnerImgQty">파일갯수 : </label> 
               <input id="spinnerImgQty" value="0" style="width: 30px; height: 20px;">
                  <div id="divfileattach"></div> 
                  <input type="hidden" name="attachCount" id="attachCount" />
               </td>
            </tr>

            <tr style="height: 70px;">
               <td colspan="6" style="border-left: hidden; border-bottom: hidden; border-right: hidden; text-align:center;">
                  <button type="button" value="제품등록" id="btnRegister" style="width: 80px;" >제품등록</button> &nbsp; 
                  <input type="reset" value="취소" style="width: 80px;" />
                  </td>
               </tr>
         </table>
      </form>
   </div>

</body>