package shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import common.controller.AbstractController;
import member.model.MemberVO;
import shop.model.InterProductDAO;
import shop.model.ProductDAO;

public class LikeCntAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 로그인확인
		MemberVO member = super.getLoginMember(req);
		String pcode = req.getParameter("pcode");
		
		InterProductDAO pdao = new ProductDAO();
		// 좋아요 수 +1
		int n = 0;
		if(member!=null) n = pdao.likeCntUP(member.getUserid(),pcode);
		// 다시 해당 상품의 좋아요수 가져오기
		int likecnt = pdao.getLikeCnt(pcode);
		// json에 셋팅
		JSONObject jobj = new JSONObject();
		jobj.put("n", n);
		jobj.put("likecnt", likecnt);
		if(member!=null) jobj.put("login", "true");
		else jobj.put("login", "false");
		// jobj를 String으로 변환후 값반환
		String str_jobj = jobj.toString();
		System.out.println(str_jobj);
		req.setAttribute("str_jobj", str_jobj);
		super.setViewPage("/WEB-INF/json.jsp");
	}

}
