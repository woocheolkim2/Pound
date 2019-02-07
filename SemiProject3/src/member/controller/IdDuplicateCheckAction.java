package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.MemberDAO;

public class IdDuplicateCheckAction extends AbstractController {

   @Override
   public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
      String userid = req.getParameter("userid");    
      System.out.println(userid);
      MemberDAO memberdao = new MemberDAO();
      boolean isUSEuserid = memberdao.idDuplicateCheck(userid); 
      req.setAttribute("isUSEuserid", isUSEuserid);
      super.setRedirect(false);
      super.setViewPage("/WEB-INF/member/idcheck.jsp"); 
   }
}