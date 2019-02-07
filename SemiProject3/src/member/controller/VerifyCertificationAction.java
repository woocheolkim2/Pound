package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;

public class VerifyCertificationAction extends AbstractController {

   @Override
   public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
      
      String userid = req.getParameter("userid");
      String userCertificationCode = req.getParameter("userCertificationCode");
      
      HttpSession session = req.getSession();
      String certificationCode = (String)session.getAttribute("certificationCode");
      
      String msg = "";
      String loc = "";
      
      if(certificationCode.equals(userCertificationCode)) {
         msg = "인증성공 되었습니다.";
         loc = req.getContextPath()+"/pwdConfirm.do?userid="+userid;
      } else {
         msg = "발급된 인증코드가 아닙니다. 인증코드를 다시 발급받으세요!";
         loc = req.getContextPath()+"/pwdFind.do";
      }
      req.setAttribute("msg", msg);
      req.setAttribute("loc", loc);
      
      super.setRedirect(false);
      super.setViewPage("/WEB-INF/msg.jsp");
      
      session.removeAttribute("certificationCode");
      // 세션에 저장된 인증코드 삭제 
   }

}