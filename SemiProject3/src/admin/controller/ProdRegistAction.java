package admin.controller;


import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.MemberVO; 
import shop.model.CategoryVO;
import shop.model.ProductDAO;

public class ProdRegistAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		 String msg = "";
	     String loc = "";
	      
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
	         loc = "login.do";
	         
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

		MemberVO loginuser = super.getLoginMember(req);
         if(!"admin".equals(loginuser.getUserid())){
            req.setAttribute("msg", "관리자만 접근 가능합니다.");
            req.setAttribute("loc", "javascript:history.back()");
            return;
         } 
         ProductDAO pdao = new ProductDAO(); 
         List<CategoryVO> categoryList = pdao.getAllCategory();
         
         req.setAttribute("categoryList", categoryList);
         req.setAttribute("goPage", "prodRegist.jsp");
         super.setRedirect(false);
         super.setViewPage("/WEB-INF/admin/index.jsp");
	}
}

