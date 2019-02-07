package admin.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import common.controller.AbstractController;
import shop.model.InterProductDAO;
import shop.model.ProductDAO;
import shop.model.ProductVO;

public class ProductRegisterEndAction extends AbstractController {

   @Override
   public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
	  String method = req.getMethod();
      System.out.println(method);
      if(!"POST".equals(method)) {
         super.setRedirect(false);
         super.setViewPage("javascript:history.back()");
         return;
      }
      HttpSession session = req.getSession();
      ServletContext svlCtx = session.getServletContext();
      String imagesDir = svlCtx.getRealPath("/images");
      System.out.println("==== 첨부되어지는 이미지 파일이 올라갈 절대경로  imagesDir : "+imagesDir);
      
      MultipartRequest mtreq = null;
      try {
         mtreq = new MultipartRequest(req, imagesDir, 10*1024*1024, "UTF-8", new DefaultFileRenamePolicy());
      } catch (IOException e) {
         e.printStackTrace();
         req.setAttribute("msg", "파일업로드 실패!  ==> 최대용량인 10MB를 초과하였습니다.");
         req.setAttribute("loc", "productRegister.do");
         super.setRedirect(false);
         super.setViewPage("/WEB-INF/msg.jsp");
         return;
      }// end of try~ catch~
      
      String registerNewProduct = mtreq.getParameter("duplicate");
      InterProductDAO pdao = new ProductDAO();
      String cg_1st_code = mtreq.getParameter("cg_1st_code");
      String cg_2nd_code = mtreq.getParameter("cg_2nd_code");
      
      String pname = mtreq.getParameter("pname");
      String pcode = pdao.getPcodeByPname(pname);
      
      String colorArr[] = mtreq.getParameterValues("color");
      String psizeArr[] = mtreq.getParameterValues("psize");
      String iqtyArr[] = mtreq.getParameterValues("pqty");
      
      for(int i=0;i<colorArr.length;i++) {
    	  if("".equals(colorArr[i])) colorArr[i] = "None";
    	  if("".equals(psizeArr[i])) psizeArr[i] = "Free";
      }
      
      String str_attachCount = mtreq.getParameter("attachCount");
      String pimgArr[] = null;
      if(!"".equals(str_attachCount)) {
         int attachCount = Integer.parseInt(str_attachCount);
         pimgArr = new String[attachCount];
         for(int i=0; i<attachCount; i++) {
            pimgArr[i] = mtreq.getFilesystemName("attach"+i);
         }// end of for--------------------------------
         
     }// end of if------------------
      ProductVO newProduct = null;
      if("1".equals(registerNewProduct)) {
         int n = pdao.registerExistingProduct(pcode,colorArr,psizeArr,iqtyArr,pimgArr);
         if(n==1) System.out.println("기존 등록 상품 처리 성공!");
         else System.out.println("실패");
      }
      
      else {
         String mainimg1 = mtreq.getFilesystemName("mainImg1");
         String mainimg2 = mtreq.getFilesystemName("mainImg2");
         String price = mtreq.getParameter("price");
         String pcompany = mtreq.getParameter("pcompany");
         String pcontents = mtreq.getParameter("pcontents");
         
         String str_product_idx = pdao.getProductIdxByPcode();
         if(str_product_idx.length()<3) {
            str_product_idx = "00"+str_product_idx;
            str_product_idx = (str_product_idx).substring(str_product_idx.length()-3);
         }
         int product_idx = Integer.parseInt(str_product_idx);
         newProduct = new ProductVO(product_idx
               , cg_1st_code, cg_2nd_code, cg_1st_code+cg_2nd_code+product_idx
               , pname, mainimg1, mainimg2
               , "null", Integer.parseInt(price), pcompany, 0,pcontents);
         int n = pdao.registerNewProduct(newProduct,colorArr,psizeArr,iqtyArr,pimgArr);
         if(n==1) System.out.println("새로운 상품 등록 성공!");
         else System.out.println("새로운 상품 등록 실패");  
      }
      req.setAttribute("msg", "제품등록 성공!!");
      req.setAttribute("loc", "prodRegist.do");
      super.setViewPage("/WEB-INF/msg.jsp");      
   }
   
}