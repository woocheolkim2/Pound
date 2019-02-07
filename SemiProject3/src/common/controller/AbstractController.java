package common.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import member.model.MemberVO;
import shop.model.CategoryVO;
import shop.model.InterProductDAO;
import shop.model.ProductDAO;

public abstract class AbstractController implements Command {
	private boolean isRedirect =false;
	private String viewPage; 
	
	public boolean isRedirect() {
		return isRedirect;
	} 
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}

	public String getViewPage() {
		return viewPage;
	}

	public void setViewPage(String viewPage) {
		this.viewPage = viewPage;
	}
	public void setCglist(HttpServletRequest req) throws SQLException {
		InterProductDAO pdao = new ProductDAO();
		List<CategoryVO> cglist = pdao.getAllCategory();
		req.setAttribute("cglist", cglist);
	}
	public MemberVO getLoginMember(HttpServletRequest req) {
		HttpSession session = req.getSession();
		MemberVO member = (MemberVO)session.getAttribute("loginMember");
		if(member == null) {
			req.setAttribute("msg", "로그인 먼저 진행주세요.");
			req.setAttribute("loc", "login.do");
			setViewPage("/WEB-INF/msg.jsp");
			return null;
		}
		else return member;
	}
}
