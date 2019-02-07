package admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.*;

public class MemberIdleClearAction extends AbstractController {

	@Override
	   public void execute(HttpServletRequest req, HttpServletResponse res)
	      throws Exception {
	      
	      String method = req.getMethod();
	      
	      String msg = "";
	      String loc = "";
	      
	      if( !"POST".equalsIgnoreCase(method) ) {
	         msg = "비정상적인 경로로 들어왔습니다!!";
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
	      else if( !"admin".equals(loginuser.getUserid()) ){
	         msg = "관리자가 아닙니다!!";
	         loc = "javascript:history.back();";
	         
	         req.setAttribute("msg", msg);
	         req.setAttribute("loc", loc);
	         
	         super.setRedirect(false);
	         super.setViewPage("/WEB-INF/msg.jsp");
	         
	         return;
	      }
	      else {
	         String idx = req.getParameter("idx");
	         
	         MemberDAO memberdao = new MemberDAO();
	         
	         int n = memberdao.clearMember(idx);
	         
	         if(n == 1) {
	            msg = "회원번호 " + idx + "의 휴면이 해제되었습니다.";
	            loc = "memberManagement.do";
	         }
	         else {
	            msg = "회원번호 " + idx + "의 휴면해제를 실패했습니다.";
	            loc = "javascript:history.back();";
	         }
	         
	         req.setAttribute("msg", msg);
	         req.setAttribute("loc", loc);
	         
	         super.setRedirect(false);
	         super.setViewPage("/WEB-INF/msg.jsp");
	      }
	      
	   }// end of void execute(HttpServletRequest req, HttpServletResponse res)-------



}
