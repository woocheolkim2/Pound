package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.MemberDAO;
import member.model.MemberVO;

public class MemberEditEndAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String method = req.getMethod();
		
		if(!"POST".equalsIgnoreCase(method)) {
			String msg = "비정상적인 경로로 들어왔습니다";
			String loc = "javascript:histroy.back()";
			
			req.setAttribute("msg", msg);
			req.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
			
			return;
		}
		
		MemberVO loginMember = super.getLoginMember(req);
		
		if(loginMember == null) {
			return;
		}
		else {

			MemberVO membervo = new MemberVO();	
			
			membervo.setUsername(req.getParameter("username"));
			membervo.setPwd(req.getParameter("pwd"));
			membervo.setEmail(req.getParameter("email"));
			membervo.setHp1(req.getParameter("hp1"));
			membervo.setHp2(req.getParameter("hp2"));
			membervo.setHp3(req.getParameter("hp3"));
			membervo.setPost1(req.getParameter("post1"));
			membervo.setPost2(req.getParameter("post2"));
			membervo.setAddr1(req.getParameter("addr1"));
			membervo.setAddr2(req.getParameter("addr2"));
			membervo.setMember_idx(loginMember.getMember_idx());
			
			MemberDAO memberdao = new MemberDAO();
			
			int n = memberdao.editMember(membervo);
			
			String msg = "";
			String loc = "";
			
			if(n==1) {				
				
				loginMember.setUsername(req.getParameter("name"));
				
				HttpSession session = req.getSession();
				session.setAttribute("loginMember", loginMember);
					
				msg = "회원정보 수정 성공!!";
				
				req.setAttribute("msg", "회원정보 수정이 완료되었습니다.");
				req.setAttribute("loc", "home.do");
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/msg.jsp");
				
			}else {
				
				msg = "회원정보 수정 실패!!";
				
				
				req.setAttribute("msg", msg);
				req.setAttribute("loc", loc);
				
				this.setRedirect(false);
				this.setViewPage("/WEB-INF/msg.jsp");
				
			}
		}
		

	}

}
