package admin.controller;

import javax.servlet.http.*;

import common.controller.AbstractController;
import member.model.*;

public class ShowMemberDetailAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String idx = req.getParameter("idx");	
		MemberVO loginuser = super.getLoginMember(req);
		
		if(!"admin".equalsIgnoreCase(loginuser.getUserid())) {
			req.setAttribute("msg", "관리자만 접근 가능합니다.");
			req.setAttribute("loc", "javascript:history.back();");
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		
		MemberDAO mdao = new MemberDAO();
		
		MemberVO mvo = mdao.getMemberByIdx(idx);
		
		req.setAttribute("mvo", mvo);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/admin/memberInfo.jsp");
	}

}
