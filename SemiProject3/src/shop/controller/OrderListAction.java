package shop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.MemberVO;
import shop.model.InterProductDAO;
import shop.model.OrderVO;
import shop.model.ProductDAO;

public class OrderListAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		MemberVO member = super.getLoginMember(req);
		if(member==null) {
			req.setAttribute("msg", "로그인이 필요합니다.");
			req.setAttribute("loc", "login.do");
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		/*if(!"POST".equalsIgnoreCase(req.getMethod())) {
			req.setAttribute("msg", "비정상적인 접근입니다.");
			req.setAttribute("loc", "javascript:history.back();");
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}*/
		super.setCglist(req);
		InterProductDAO pdao = new ProductDAO();
		List<OrderVO> orderList = pdao.getUserOrderList(member.getUserid());
		for(OrderVO order : orderList) {
			
		}
		req.setAttribute("orderList", orderList);
		super.setViewPage("/WEB-INF/shop/orderList.jsp");
	}
}
