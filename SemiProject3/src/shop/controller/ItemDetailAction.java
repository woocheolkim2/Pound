package shop.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.MemberVO;
import shop.model.InterProductDAO;
import shop.model.ProductDAO;
import shop.model.ProductVO;

public class ItemDetailAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		super.setCglist(req);
		String pcode = req.getParameter("pcode");
		MemberVO member = super.getLoginMember(req);
		if(member!=null) {
			HttpSession session = req.getSession();
			List<String> recentViewList = (List<String>)session.getAttribute("recentViewList");
			boolean flag = false;
			for(String str:recentViewList) {
				if(pcode.equals(str)) flag = true;
			}
			if(!flag)recentViewList.add(pcode);
		}
		InterProductDAO pdao = new ProductDAO();
		ProductVO item = pdao.getProductByPcode(pcode);
		List<String> colorList = pdao.getColorList(pcode);
		List<String> imgList = pdao.getImgList(pcode);
		List<HashMap<String,String>> memoList = pdao.getProductMemoList(pcode);
		int likecnt = pdao.getLikeCnt(pcode);
		
		req.setAttribute("item", item);
		req.setAttribute("likecnt", likecnt);
		req.setAttribute("colorList", colorList);
		req.setAttribute("imgList", imgList);
		req.setAttribute("memoList", memoList);
		super.setViewPage("/WEB-INF/shop/itemDetail.jsp");
	}

}
