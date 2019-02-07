package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.InterMemberDAO;
import member.model.MemberDAO;
import member.model.MemberVO;

public class RegisterEndAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		InterMemberDAO memberdao = new MemberDAO();
		MemberVO member = new MemberVO();
		member.setUserid(req.getParameter("userid"));
		member.setPwd(req.getParameter("pwd"));
		member.setUsername(req.getParameter("username"));
		member.setPost1(req.getParameter("post1"));
		member.setPost2(req.getParameter("post2"));
		member.setAddr1(req.getParameter("addr1"));
		member.setAddr2(req.getParameter("addr2"));
		member.setEmail(req.getParameter("email"));
		member.setHp1(req.getParameter("hp1"));
		member.setHp2(req.getParameter("hp2"));
		member.setHp3(req.getParameter("hp3"));
		member.setGender(Integer.parseInt(req.getParameter("gender")));
		member.setBirthday(req.getParameter("birthyyyy")+req.getParameter("birthmm")+req.getParameter("birthdd"));
		int n = memberdao.registerMember(member);
		if(n==1) {
			req.setAttribute("msg", "회원가입 성공!!");
			req.setAttribute("loc", "home.do");
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		else {
			req.setAttribute("msg", "회원가입 실패!!");
			req.setAttribute("loc", "javascript:history.back()");
			super.setViewPage("/WEB-INF/msg.jsp");
		}
	}

}
