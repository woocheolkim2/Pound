package shop.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.MemberVO;
import shop.model.CartVO;
import shop.model.InterProductDAO;
import shop.model.ProductDAO;

public class OrderAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		MemberVO member = super.getLoginMember(req);
		if(member==null) {
			super.setViewPage("login.do");
			return;
		}
		super.setCglist(req);
		InterProductDAO pdao = new ProductDAO();
		String postpcode = req.getParameter("postpcode");
		List<CartVO> cartList = new ArrayList<CartVO>();
		if(postpcode==null) {
			String[] cartArr = req.getParameterValues("postCart");
			// 체크된 cart_idx를 가져와서 해당 cart의 정보를 모두 List로 담아온다.
			for(String str : cartArr) cartList.add(pdao.getcartByIdx(str));
			// 주문 총액을 계산한다.
		}
		else {
			String color = req.getParameter("color");
			String psize = req.getParameter("size");
			String oqty = req.getParameter("oqty");
			int stock_idx = pdao.getStockIdx(postpcode, color, psize, true);
			CartVO cart = new CartVO();
			cart.setFk_pcode(postpcode);
			cart.setFk_stock_idx(stock_idx);
			cart.setOqty(Integer.parseInt(oqty));
			cart.setFk_userid(member.getUserid());
			cart.setOcolor(color);
			cart.setOsize(psize);
			cart.setProductByPcode(postpcode);
			cartList.add(cart);
		}
		int sumTotalPrice = 0;
		for(CartVO cart : cartList) sumTotalPrice += pdao.getProductByPcode(cart.getFk_pcode()).getPrice()*cart.getOqty();
		// 주문총액이  10만원 이하일 경우에만 2500원의 배송비를 부과한다.
		int dvrfee = 0;
		if(sumTotalPrice<100000) dvrfee= 2500;
		
		req.setAttribute("cartList", cartList);
		req.setAttribute("sumtotalprice", sumTotalPrice);
		req.setAttribute("dvrfee", dvrfee);
		super.setViewPage("/WEB-INF/shop/orderForm.jsp");
	}

}
