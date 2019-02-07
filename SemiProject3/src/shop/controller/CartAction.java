package shop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.MemberVO;
import shop.model.CartVO;
import shop.model.InterProductDAO;
import shop.model.ProductDAO;

public class CartAction extends AbstractController {
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
		InterProductDAO pdao = new ProductDAO();
		List<CartVO> cartList = pdao.getMyCart(member.getUserid());
		req.setAttribute("cartList", cartList);
		super.setViewPage("/WEB-INF/shop/cart.jsp");
	}
}
