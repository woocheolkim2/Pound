package shop.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.MemberVO;
import shop.model.InterProductDAO;
import shop.model.ProductDAO;
import shop.model.ProductVO;

public class LikeItemAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		super.setCglist(req);
		MemberVO member = super.getLoginMember(req);
		if(member ==null) {
			req.setAttribute("msg", "로그인 후 이용 가능합니다.");
			req.setAttribute("loc", "login.do");
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		InterProductDAO pdao = new ProductDAO();
		List<String> pcodeList = pdao.getLikeItem(member.getUserid());
		List<ProductVO> productList = new ArrayList<ProductVO>();
		for(String pcode : pcodeList) productList.add(pdao.getProductByPcode(pcode));
		req.setAttribute("productList", productList);
		super.setViewPage("/WEB-INF/shop/likeItem.jsp");
	}
}
