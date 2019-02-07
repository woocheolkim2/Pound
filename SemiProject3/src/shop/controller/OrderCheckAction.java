package shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;

public class OrderCheckAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		super.setCglist(req);
		HttpSession session =req.getSession();
		String odrcode = (String)session.getAttribute("odrcode");
		//session.removeAttribute("odrcode");
		req.setAttribute("odrcode", odrcode);
		super.setViewPage("/WEB-INF/shop/orderEnd.jsp");
	}

}
