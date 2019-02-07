package admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import common.controller.AbstractController;
import shop.model.InterProductDAO;
import shop.model.ProductDAO;
import shop.model.ProductVO;

public class ProdDuplicateCheckAction extends AbstractController {

   @Override
   public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
	   JSONArray jsonArray = new JSONArray();
	   	String pname = req.getParameter("pname");
	   	String cg_1st_code = req.getParameter("cg_1st_code");
	   	String cg_2nd_code = req.getParameter("cg_2nd_code");
	   	String categoryCode = cg_1st_code+cg_2nd_code;
	   	
	   	if(!pname.trim().isEmpty()) {
		    InterProductDAO pdao = new ProductDAO();
	        List<ProductVO> prodList = pdao.getSearchedPname(pname,categoryCode);
       
			
			if(prodList != null && prodList.size() > 0) {
				for(ProductVO pvo : prodList) {
					
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("searchwordresult", pvo.getPname());
					jsonObj.put("fullCategory", pvo.getFullCategory());
					
					jsonArray.add(jsonObj);
				}
			}		
		}// end of if------------------------------
	
	   	String str_jsonArray = jsonArray.toString();		
	
		req.setAttribute("str_jsonArray", str_jsonArray);
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/admin/productNameJSON.jsp");

   }

}