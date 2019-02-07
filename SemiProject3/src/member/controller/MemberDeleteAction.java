package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.MemberDAO;
import member.model.MemberVO;

public class MemberDeleteAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
		String method = req.getMethod();
		
		String msg = "";
		String loc = "";
		
		if( !"POST".equalsIgnoreCase(method) ) {
			msg = "비정상적인 접근입니다.";
			loc = "javascript:history.back();";
			
			req.setAttribute("msg", msg);
			req.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
			
			return;
		}
	
		MemberVO loginuser = super.getLoginMember(req);
		
		if(loginuser == null) return;
		else if( !"admin".equals(loginuser.getUserid()) ){
			msg = "관리자만 접근 할 수 있습니다.";
			loc = "javascript:history.back();";
			
			req.setAttribute("msg", msg);
			req.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
			
			return;
		}
		else {
			MemberVO member = super.getLoginMember(req);
			MemberDAO memberdao = new MemberDAO();
			int n = memberdao.deleteMember(String.valueOf(member.getMember_idx()));
			
			if(n==1) msg = "회원번호 " + member.getMember_idx() + "가 삭제되었습니다.";
			else msg = "회원번호 " + member.getMember_idx() + "가 삭제를 실패했습니다.";
			
			HttpSession session = req.getSession();
			session.removeAttribute("loginMember");
			req.setAttribute("msg", msg);
			req.setAttribute("loc", "logout.do");
			super.setViewPage("/WEB-INF/msg.jsp");
		}
		
	}// end of void execute(HttpServletRequest req, HttpServletResponse res)----------------

}
