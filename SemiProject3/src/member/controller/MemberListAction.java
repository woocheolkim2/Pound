package member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.MemberDAO;
import member.model.MemberVO;

public class MemberListAction extends AbstractController {

       @Override
         public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
          
            MemberVO loginuser = super.getLoginMember(req);
            
            if(!"admin".equals(loginuser.getUserid())){
              
               req.setAttribute("msg", "관리자만 접근 가능합니다.");
               req.setAttribute("loc", "javascript:history.back()");
               
               return;

            } 
         
            MemberDAO mdao = new MemberDAO();

            List<MemberVO> memberList = mdao.getAllMember();
               
            req.setAttribute("memberList", memberList);
            req.setAttribute("goPage", "memberManage.jsp");
            super.setRedirect(false);
            super.setViewPage("/WEB-INF/admin/index.jsp");
            
         }// end of execute(HttpServletRequest req, HttpServletResponse res)-------------------

   }
