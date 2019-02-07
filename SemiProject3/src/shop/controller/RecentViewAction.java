package shop.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.MemberVO;
import shop.model.InterProductDAO;
import shop.model.ProductDAO;
import shop.model.ProductVO;

public class RecentViewAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		super.setCglist(req);
		MemberVO member = super.getLoginMember(req);
		if(member==null) {
			req.setAttribute("msg", "로그인이 필요합니다.");
			req.setAttribute("loc", "login.do");
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		HttpSession session = req.getSession();
		List<String> recentViewList = (List<String>)session.getAttribute("recentViewList");
		InterProductDAO pdao = new ProductDAO();
		List<ProductVO> productList = new ArrayList<ProductVO>();
		for(int i=recentViewList.size()-1;i>=0;i--) productList.add(pdao.getProductByPcode(recentViewList.get(i)));
		System.out.println(productList.size());
		req.setAttribute("productList", productList);
		super.setViewPage("/WEB-INF/shop/recentview.jsp");
	}

}
