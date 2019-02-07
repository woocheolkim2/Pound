package shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import common.controller.AbstractController;
import shop.model.InterProductDAO;
import shop.model.ProductDAO;

public class QnaInsertAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String userid = req.getParameter("userid");
		String pcode = req.getParameter("pcode");
		String content = req.getParameter("content");
		System.out.println(userid+pcode+content);
		InterProductDAO pdao = new ProductDAO();
		int n = pdao.insertQna(userid,pcode,content);
		JSONObject jobj = new JSONObject();
		jobj.put("n", n);
		String str_jobj = jobj.toString();
		req.setAttribute("str_jobj", str_jobj);
		super.setViewPage("/WEB-INF/json.jsp");
	}

}
