package member.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.InterMemberDAO;
import member.model.MemberDAO;
import member.model.MemberVO;

public class LoginEndAction extends AbstractController { 
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(!"POST".equalsIgnoreCase(req.getMethod())){
			req.setAttribute("msg", "비정상적인 접근 입니다.");
			req.setAttribute("loc", "home.do");
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		String userid = req.getParameter("login_id");
		String pwd = req.getParameter("login_pw");
		String authObj = req.getParameter("authObj");
		String kakaores = req.getParameter("res");
		if(authObj!=null) {
			MemberVO kakaoMember = new MemberVO();
			kakaoMember.setKakao_auth(authObj);
			kakaoMember.setKakao_res(kakaores);
			/*
			{"access_token":"feiwBuaKUoA7tPQ5_8g13g556Ta59F2i84zbVgopdtYAAAFnoCtZQA","token_type":"bearer","refresh_token":"87dr4xPeHlz8RcJhSb-d4ek7g3HrtfpXfm2hagopdtYAAAFnoCtZPg","expires_in":7199,"scope":"profile","refresh_token_expires_in":2591999}
			{"id":980209626,"properties":{"nickname":"최현영"},"kakao_account":{"has_email":true,"has_age_range":true,"has_birthday":true,"has_gender":false}}
			*/
			HttpSession session = req.getSession();
			session.setAttribute("loginMember", kakaoMember);
			super.setViewPage("home.do");
			return;
		}
		InterMemberDAO memberdao = new MemberDAO();
		MemberVO loginMember = memberdao.loginMember(userid, pwd);
		if(loginMember==null) {
			req.setAttribute("msg", "아이디와 비밀번호를 확인학세요");
			req.setAttribute("loc", "login.do");
			
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		else {
			HttpSession session = req.getSession();
			session.setAttribute("loginMember", loginMember);
			Cookie cookie = new Cookie("saveid",req.getParameter("login_id"));
			if("on".equals(req.getParameter("saveid"))) cookie.setMaxAge(7*24*60*60);	
			else cookie.setMaxAge(0);
			cookie.setPath("/");
			List<String> recentViewList = new ArrayList<String>();
			session.setAttribute("recentViewList", recentViewList);
			if("admin".equalsIgnoreCase(userid)) {
				super.setRedirect(true);
				super.setViewPage("admin.do");
			}else {
				super.setRedirect(true);
				super.setViewPage("home.do");
			}
			
		}
	}
}
