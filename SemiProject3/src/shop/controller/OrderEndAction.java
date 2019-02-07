package shop.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.controller.GoogleMail;
import member.model.InterMemberDAO;
import member.model.MemberDAO;
import member.model.MemberVO;
import shop.model.CartVO;
import shop.model.InterProductDAO;
import shop.model.ProductDAO;

public class OrderEndAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		super.setCglist(req);
		MemberVO member = super.getLoginMember(req);
		
		String sumTotalPrice = req.getParameter("sumTotalPrice");
		String dvrfee = req.getParameter("dvrfee");
		String[] cartArr = req.getParameterValues("cart_idx");
		// 배송자 정보 가져오기 추가 
		String recivername = req.getParameter("receivername");
		String reciverpost1 = req.getParameter("receiverpost1");
		String reciverpost2 = req.getParameter("receiverpost2");
		String reciveraddr1 = req.getParameter("receiveraddr1");
		String reciveraddr2 = req.getParameter("receiveraddr2");
		String reciverhp1 = req.getParameter("receiverhp1");
		String reciverhp2 = req.getParameter("receiverhp2");
		String reciverhp3 = req.getParameter("receiverhp3");
		String reciveremail = req.getParameter("receiveremail");
		String str_spetpoint = req.getParameter("spentpoint");
		// 사용한 포인트 
		int spentpoint = Integer.parseInt(str_spetpoint);
		
		InterProductDAO pdao = new ProductDAO();
		String odrcode = pdao.createOdrcode();
		List<CartVO> cartList = new ArrayList<CartVO>();
		System.out.println(cartArr[0]);
		
		// 사용한 포인트가 있을때 차감
		if(spentpoint != 0) {
			InterMemberDAO mdao = new MemberDAO();
			int n = mdao.updatePoint(spentpoint, member.getUserid());
		}
		
		if(cartArr.length==1&&"0".equals(cartArr[0])) {
			String pcode = req.getParameter("pcode");
			String color = req.getParameter("color");
			String psize = req.getParameter("size");
			String oqty = req.getParameter("oqty");
			int stock_idx = pdao.getStockIdx(pcode, color, psize, true);
			CartVO cart = new CartVO();
			cart.setFk_pcode(pcode);
			cart.setFk_stock_idx(stock_idx);
			cart.setOqty(Integer.parseInt(oqty));
			cart.setFk_userid(member.getUserid());
			cart.setOcolor(color);
			cart.setOsize(psize);
			cart.setProductByPcode(pcode);
			cartList.add(cart);
		}
		else for(String str :cartArr) cartList.add(pdao.getcartByIdx(str));
		int n = pdao.insertOrder(odrcode,member.getUserid(),Integer.parseInt(sumTotalPrice),cartList,Integer.parseInt(dvrfee),
				recivername, reciverpost1, reciverpost2, reciveraddr1, reciveraddr2, reciverhp1, reciverhp2, reciverhp3, reciveremail);
		System.out.println(n);
		// 1이 반환됨ㄴ 주문성공 0 이라면 실패
		if(n==1) {
			if(!"0".equals(cartArr[0])) {
				for(String str : cartArr) pdao.deleteCart(str);
			}
			GoogleMail mail = new GoogleMail();
			String emailContent = "<table style='width:80%; margin-top: 1%;border-top: 1px solid lightgray;'>"
							  	+ "<thead style='height:50px'><tr><th colspan='6' style='background-color: #efefef'>주문내역</th></tr>"
								+ "<tr style='text-align: center;border-top: 1px solid lightgray;'>"
								+ "<th>Image</th><th>상품명</th><th>단가</th><th>옵션</th><th>주문수량</th><th>합계</th></tr></thead>"
								+ "<tbody align='center' style='border-top: 1px solid lightgray; border-bottom: 1px solid lightgray; margin-bottom:5%;'>";
								
			for(CartVO cart : cartList) {
				emailContent += "<tr style='border-top: 1px solid lightgray;'>"
					+"<td style='width: 15%;'><img src='http://192.168.50.25:9090/SemiProject/images/"+cart.getProduct().getMainimg1()+"' style='width:150px;height:150px;'/></td>"
					+"<td style='width: 15%; font-weight:bold;' >"+cart.getProduct().getPname()+"</td>"
					+"<td style='width: 10%;'>"+cart.getProduct().getPrice()+"원</td>"
					+"<td style='width: 10%;'>"+cart.getOcolor()+" / "+cart.getOsize()+"</td>"
					+"<td style='width: 10%;'>"+cart.getOqty()+"</td>"
					+"<td style='width:10%;font-weight:bold;'>"+sumTotalPrice+"원</td>"
					+"</tr></c:forEach>";
			}
			emailContent +=" </tbody></table>";
			mail.sendmail_OrderFinish(member.getEmail(),member.getUsername(),emailContent);
		}
		
		HttpSession session = req.getSession();
		session.setAttribute("odrcode", odrcode);
		
		req.setAttribute("odrcode", odrcode);
		req.setAttribute("msg", "주문이 완료되었습니다.");
		req.setAttribute("loc", "orderCheck.do");
		super.setViewPage("/WEB-INF/msg.jsp");
	}

}

