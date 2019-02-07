package shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import common.controller.AbstractController;


public class SpentPointAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
		String str_spentpoint = req.getParameter("spentpoint");
		String str_sumtotalprice = req.getParameter("sumtotalprice");
		String dvrfee = req.getParameter("dvrfee");
		int spentpoint = Integer.parseInt(str_spentpoint);
		int sumtotalprice = Integer.parseInt(str_sumtotalprice);
		if(dvrfee==null) dvrfee="0";
		int n=0;
		JSONObject jobj = new JSONObject();
		String str_jobj="";
		if(spentpoint < 1000) {
			n=0;
			jobj.put("n", n);
			str_jobj = jobj.toString();
			req.setAttribute("str_jobj", str_jobj);
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/json.jsp");
			return;
		} else if(spentpoint>sumtotalprice){
			n=1;
			jobj.put("n", n);
			str_jobj = jobj.toString();
			req.setAttribute("str_jobj", str_jobj);
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/json.jsp");
			return;
		} else {
			n=2;
			int lastprice = sumtotalprice - spentpoint;
			jobj.put("n", n);
			jobj.put("dvrfee", dvrfee);
			jobj.put("lastprice", lastprice);
			jobj.put("sumtotalprice", sumtotalprice);
			jobj.put("spentpoint", spentpoint);
		}
		str_jobj = jobj.toString();
		req.setAttribute("str_jobj", str_jobj);
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/json.jsp");
		
	}

}
