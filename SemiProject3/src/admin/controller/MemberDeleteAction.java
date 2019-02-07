package admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.*;

public class MemberDeleteAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		 String msg = "";
	      String loc = "";
	      
	      if(!"POST".equalsIgnoreCase(req.getMethod())) {
	         msg = "비정상적인 경로로 들어왔습니다.";
	         loc = "javascript:history.back();";
	         
	         req.setAttribute("msg", msg);
	         req.setAttribute("loc", loc);
	         
	         super.setRedirect(false);
	         super.setViewPage("/WEB-INF/msg.jsp");
	         
	         return;
	      }
	      
	      MemberVO loginuser = super.getLoginMember(req);
	      
	      if(loginuser == null) {
	         msg = "먼저 로그인 하세요!!";
	         loc = "javascript:history.back();";
	         
	         req.setAttribute("msg", msg);
	         req.setAttribute("loc", loc);
	         
	         super.setRedirect(false);
	         super.setViewPage("/WEB-INF/msg.jsp");
	         
	         return;
	      }
	      else if(!"admin".equals(loginuser.getUserid())) {
	         msg = "관리자만 접근 할수 있습니다.";
	         loc = "javascript:history.back();";
	         
	         req.setAttribute("msg", msg);
	         req.setAttribute("loc", loc);
	         
	         super.setRedirect(false);
	         super.setViewPage("/WEB-INF/msg.jsp");
	         
	         return;
	      }
	      else {
	         String idx = req.getParameter("idx");
	         
	         MemberDAO mdao = new MemberDAO();
	         
	         int n = mdao.deleteMember(idx);
	         
	         if(n == 1) {
	            msg = "회원번호 " + idx + "가 삭제되었습니다.";
	            loc = "memberManagement.do";
	         }
	         else {
	            msg = "회원번호 " + idx + "가 삭제를 실패했습니다.";
	            loc = "javascript:history.back();";
	         }
	         
	         req.setAttribute("msg", msg);
	         req.setAttribute("loc", loc);
	         
	         super.setRedirect(false);
	         super.setViewPage("/WEB-INF/msg.jsp");
	      }
	      

		
	}

}
