package shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;

public class PaymentAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String sumTotalPrice = req.getParameter("sumTotalPrice");
		String[] pnameArr = req.getParameterValues("pname");
		String str_pname = pnameArr[0] + " 외"+(pnameArr.length-1)+"개";
		req.setAttribute("sumTotalPrice", Integer.parseInt(sumTotalPrice));
		req.setAttribute("name", str_pname);
		super.setViewPage("/WEB-INF/shop/paymentGateway.jsp");
	}

}
