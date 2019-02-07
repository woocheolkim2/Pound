package member.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;

public class LoginAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(super.getLoginMember(req)!=null) {
			req.setAttribute("msg", "이미 로그인 되었습니다.");
			req.setAttribute("loc", "javascript:history.back();");
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		String cookieValue="";
		Cookie[] cookieArr = req.getCookies();
		if(cookieArr!=null){// 클라이언트 컴퓨터에서 보내온 쿠키가 있을경우
			for(Cookie cook : cookieArr){
				if("saveid".equals(cook.getName())){
					cookieValue = cook.getValue();
					break;
				}
			}
		}
		else cookieValue="";
		System.out.println(cookieValue);
		req.setAttribute("cookieValue", cookieValue);
		super.setCglist(req);
		super.setViewPage("/WEB-INF/login/login.jsp");
	}
	
}
