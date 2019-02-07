package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;

public class LogoutAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		session.removeAttribute("loginMember");
		req.setAttribute("msg", "로그아웃 되었습니다.");
		req.setAttribute("loc", "home.do");
		super.setViewPage("/WEB-INF/msg.jsp");
	}

}
