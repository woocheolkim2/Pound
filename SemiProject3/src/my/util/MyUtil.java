package my.util;

import java.util.HashMap;

import javax.servlet.http.*;
import member.model.MemberVO;

public class MyUtil {

 // *** ?다음의 데이터까지 포함한 현재 URL주소를 알려주는 메소드 *** //
	public static String getCurrentURL(HttpServletRequest request) { 
	    	
	       String currentURL = request.getRequestURL().toString(); 
	       // http://localhost:9090/MyWeb/member/memberDetail.jsp
	       
	       String queryString = request.getQueryString();
	       // sizePerPage=3&currentShowPageNo=8&period=60&searchType=name&searchWord=유미 
	       
	       currentURL += "?"+queryString;
	       // http://localhost:9090/MyWeb/member/memberDetail.jsp?sizePerPage=3&currentShowPageNo=8&period=60&searchType=name&searchWord=유미 
	       
	       String ctxPath = request.getContextPath();
	       //  /MyWeb
	       
	       int beginIndex = currentURL.indexOf(ctxPath) + ctxPath.length();
	    		   //                               21  +  6
	       
	       currentURL = currentURL.substring(beginIndex+1);
	       
	       return currentURL;
	    	
	    }// end of String getCurrentURL(HttpServletRequest request)--------
		//로그인 유무를 검사해서 로그인 했으면 로그인 한 사용자 정보를 리턴
	    //로그인 안했으면 null리턴
	public static HashMap<String,Object> getLoginCheck(HttpServletRequest req, HttpServletResponse res) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		// 세션에 저장된 값을 가져온다.
		HttpSession session = req.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("member");
		if(loginuser==null) {
			map.put("loginuser", null);
			map.put("msg", "먼저로그인 하세요!!");
			map.put("loc", "javascript:history.back()");
		}
		else {
			map.put("loginuser", loginuser);
		}
		return map;
	}
	//검색어 및 날짜 구간이 포함된 페이지바 만들기
	public static String getSearchPageBar(String url,int currentShowPageNo,int sizePerPage,int totalPage,int blockSize,String searchType,String searchWord, int period) {
		String pageBar="";
		// blockSize 1개의 블럭당 보여줄 페이지 번호의 갯수
		int loop = 1; // 1부터증가하여 1개 블럭을 이루는 페이지 번호의 갯수(지금은 10개)
		int pageNo = ((currentShowPageNo-1)/blockSize) * blockSize+1;
		//이전만들기
		if(pageNo!=1){
			pageBar+="&nbsp;<a href=\" "+url
					+"?currentShowPageNo="+(pageNo-1)
					+"&sizePerPage="+sizePerPage
					+"&searchType="+searchType
					+"&searchWord="+searchWord
					+"&period="+period+"\"> [이전] </a>&nbsp;";
		}
		while(!(pageNo>totalPage||loop>blockSize)) {
			if(pageNo==currentShowPageNo) {
				pageBar+="&nbsp;<span style=\"color:red; font-size:13pt font-weight: bold;\">"+pageNo+"&nbsp;";
			}
			else {
				pageBar+="&nbsp;<a href=\" "+url
						+"?currentShowPageNo="+pageNo
						+"&sizePerPage="+sizePerPage
						+"&searchType="+searchType
						+"&searchWord="+searchWord
						+"&period="+period+"\">"+pageNo+"</a>&nbsp;"; // Get방식
			}
			pageNo++;
			loop++;
		}
		//다음만들기
		if(!(pageNo>totalPage)){
			pageBar+="&nbsp;<a href=\" "+url
					+"?currentShowPageNo="+pageNo
					+"&sizePerPage="+sizePerPage
					+"&searchType="+searchType
					+"&searchWord="+searchWord
					+"&period="+period+"\"> [다음] </a>&nbsp;";
		}
		return pageBar;
	}
}
