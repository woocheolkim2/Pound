package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.MemberDAO;
import member.model.MemberVO;

public class MypageAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		super.setCglist(req);
		MemberVO loginMember = super.getLoginMember(req);	
		if(loginMember == null) return;
		else {
			int member_idx = loginMember.getMember_idx();
			String goBackURL = req.getParameter("goBackURL");
			
			MemberDAO memberdao = new MemberDAO();
			
			try {
				MemberVO membervo = new MemberVO();
				membervo = memberdao.memberDetail(member_idx);
				
				req.setAttribute("membervo", membervo);
				req.setAttribute("goBackURL", goBackURL);
				req.setAttribute("goPage", "showMyinfo.jsp");
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/member/mypage.jsp");
				
				return;				
			} catch(NumberFormatException e) {
				req.setAttribute("member_idx", member_idx);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/member/error1.jsp");
				
				return;
			}
		}
		
	}
}
