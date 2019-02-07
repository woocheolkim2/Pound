package admin.controller;

import javax.servlet.http.*;

import common.controller.AbstractController;

public class AdminLoginAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		super.setViewPage("/WEB-INF/admin/index.jsp");
	}

}
