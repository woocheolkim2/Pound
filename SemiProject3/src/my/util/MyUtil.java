package my.util;

import java.util.HashMap;

import javax.servlet.http.*;
import member.model.MemberVO;

public class MyUtil {

 // *** ?������ �����ͱ��� ������ ���� URL�ּҸ� �˷��ִ� �޼ҵ� *** //
	public static String getCurrentURL(HttpServletRequest request) { 
	    	
	       String currentURL = request.getRequestURL().toString(); 
	       // http://localhost:9090/MyWeb/member/memberDetail.jsp
	       
	       String queryString = request.getQueryString();
	       // sizePerPage=3&currentShowPageNo=8&period=60&searchType=name&searchWord=���� 
	       
	       currentURL += "?"+queryString;
	       // http://localhost:9090/MyWeb/member/memberDetail.jsp?sizePerPage=3&currentShowPageNo=8&period=60&searchType=name&searchWord=���� 
	       
	       String ctxPath = request.getContextPath();
	       //  /MyWeb
	       
	       int beginIndex = currentURL.indexOf(ctxPath) + ctxPath.length();
	    		   //                               21  +  6
	       
	       currentURL = currentURL.substring(beginIndex+1);
	       
	       return currentURL;
	    	
	    }// end of String getCurrentURL(HttpServletRequest request)--------
		//�α��� ������ �˻��ؼ� �α��� ������ �α��� �� ����� ������ ����
	    //�α��� �������� null����
	public static HashMap<String,Object> getLoginCheck(HttpServletRequest req, HttpServletResponse res) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		// ���ǿ� ����� ���� �����´�.
		HttpSession session = req.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("member");
		if(loginuser==null) {
			map.put("loginuser", null);
			map.put("msg", "�����α��� �ϼ���!!");
			map.put("loc", "javascript:history.back()");
		}
		else {
			map.put("loginuser", loginuser);
		}
		return map;
	}
	//�˻��� �� ��¥ ������ ���Ե� �������� �����
	public static String getSearchPageBar(String url,int currentShowPageNo,int sizePerPage,int totalPage,int blockSize,String searchType,String searchWord, int period) {
		String pageBar="";
		// blockSize 1���� ���� ������ ������ ��ȣ�� ����
		int loop = 1; // 1���������Ͽ� 1�� ���� �̷�� ������ ��ȣ�� ����(������ 10��)
		int pageNo = ((currentShowPageNo-1)/blockSize) * blockSize+1;
		//���������
		if(pageNo!=1){
			pageBar+="&nbsp;<a href=\" "+url
					+"?currentShowPageNo="+(pageNo-1)
					+"&sizePerPage="+sizePerPage
					+"&searchType="+searchType
					+"&searchWord="+searchWord
					+"&period="+period+"\"> [����] </a>&nbsp;";
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
						+"&period="+period+"\">"+pageNo+"</a>&nbsp;"; // Get���
			}
			pageNo++;
			loop++;
		}
		//���������
		if(!(pageNo>totalPage)){
			pageBar+="&nbsp;<a href=\" "+url
					+"?currentShowPageNo="+pageNo
					+"&sizePerPage="+sizePerPage
					+"&searchType="+searchType
					+"&searchWord="+searchWord
					+"&period="+period+"\"> [����] </a>&nbsp;";
		}
		return pageBar;
	}
}
