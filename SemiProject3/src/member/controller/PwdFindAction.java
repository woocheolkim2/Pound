package member.controller;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.MemberDAO;

public class PwdFindAction extends AbstractController {

   @Override
   public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

      String method = req.getMethod();
      // GET 또는 POST
      
      if("POST".equalsIgnoreCase(method)) {
         // 비밀번호 찾기 모달창에서 찾기 버튼을 클릭했을 경우
         
         String userid = req.getParameter("userid");
         String email = req.getParameter("email");
         
         MemberDAO memberdao = new MemberDAO();
         
         int n = memberdao.isUserExists(userid, email);
         
         if(n == 1) {
            // 회원으로 존재하는 경우 
            GoogleMail mail = new GoogleMail();
            
            // 인증키를 랜덤하게 생성하도록 한다. 
            Random rnd = new Random();
            
            String certificationCode = "";
            // => certificationCode : "qwer05405"
            
            char randchar = ' ';
            for(int i=0; i<5; i++) {
               // min 부터 max 사이의 값으로 랜덤한 정수를 얻으려면 
               // int rndum = rnd.nextInt(max - min + 1)
               // 영문 소문자 'a' 부터 'z' 까지 중 랜덤하게 1개를 만든다.
               randchar = (char)(rnd.nextInt('z'-'a'+ 1)+'a');  
               certificationCode += randchar;
            }
            int randnum = 0;
            for(int i=0; i<7; i++) {
               randnum = rnd.nextInt(9-0+1)+0;
               certificationCode += randnum;
            }
            // 랜덤하게 생성한 인증코드를 비밀번호 착리를 하고자하는 사용자의 이메일로 보내준다.
            try {
               mail.sendmail(email, certificationCode);
               //req.setAttribute("certificationCode", certificationCode);
               HttpSession session =  req.getSession();
               session.setAttribute("certificationCode", certificationCode);
            } catch (Exception e) {
               e.printStackTrace();  
               n = -1;
               req.setAttribute("sendFailmsg", "메일발송을 실패하였습니다.");
            }
         }
         req.setAttribute("n", n);
         // n 이 0이면 존재하지 않은 userid 또는 eamil 인 경우
         // n 이 1이면 userid 와 eamil 이 존재 하면서 매일발송이 성공한 경우 
         // n 이 -1이면 userid 와 eamil 이 존재하는데  매일발송이 실패한 경우
         req.setAttribute("userid", userid);
         req.setAttribute("email", email);
      }
      req.setAttribute("method", method);
      
      super.setRedirect(false);
      super.setViewPage("/WEB-INF/login/pwdFind.jsp");
      
   }

}