package admin.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.MemberVO;
import shop.model.InterProductDAO;
import shop.model.ProductDAO;
import shop.model.ProductVO;

public class ProdEditAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		 String msg = "";
	     String loc = "";
	      System.out.println("asdkjqhwkjwhq");
	     if(!"POST".equalsIgnoreCase(req.getMethod())) {
	         msg = "비정상적인 경로로 들어왔습니다.";
	         loc = "javascript:history.back();";
	         
	         req.setAttribute("msg", msg);
	         req.setAttribute("loc", loc);
	         
	         super.setRedirect(false);
	         super.setViewPage("/WEB-INF/msg.jsp");
	         
	         return;
	      }
	      
	      MemberVO loginMember = super.getLoginMember(req);
	      
	      if(loginMember == null) {
	         msg = "먼저 로그인 하세요!!";
	         loc = "javascript:history.back();";
	         
	         req.setAttribute("msg", msg);
	         req.setAttribute("loc", loc);
	         
	         super.setRedirect(false);
	         super.setViewPage("/WEB-INF/msg.jsp");
	         
	         return;
	      }
	      else if(!"admin".equals(loginMember.getUserid())) {
	         msg = "관리자만 접근 할수 있습니다.";
	         loc = "javascript:history.back();";
	         
	         req.setAttribute("msg", msg);
	         req.setAttribute("loc", loc);
	         
	         super.setRedirect(false);
	         super.setViewPage("/WEB-INF/msg.jsp");
	         
	         return;
	      }
	      else {
			super.setCglist(req);
			String pcode = req.getParameter("prodCode");
			
			InterProductDAO pdao = new ProductDAO();
			ProductVO item = pdao.getProductByPcode(pcode);
			List<HashMap<String,String>> stockList = pdao.getStockList(pcode,true);
			
			req.setAttribute("item", item);
			req.setAttribute("stockList", stockList);
			req.setAttribute("goPage", "ProductEdit.jsp");
	        super.setViewPage("/WEB-INF/admin/index.jsp");
		}
	}

}
