package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.MemberVO;

public class MemberEditAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		/*String method = req.getMethod();
		
		if(!"POST".equalsIgnoreCase(method)){
			req.setAttribute("msg", "비정상적인 접근 입니다.");
			req.setAttribute("loc", "home.do");
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}*/
		super.setCglist(req);
		req.setAttribute("goPage", "memberEdit.jsp");
		super.setViewPage("/WEB-INF/member/mypage.jsp");
		
		HttpSession session = req.getSession();
		MemberVO loginMember = (MemberVO)session.getAttribute("loginMember");
		if(loginMember==null) {
			req.setAttribute("msg", "로그인 하세요.");
			req.setAttribute("loc", "home.do");
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
				
	}

}
