package shop.model;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.*;
import javax.sql.DataSource;

import jdbc.util.AES256;
import my.util.MyKey;

public class ProductDAO implements InterProductDAO {
   private DataSource ds = null; 
   private Connection conn = null;
   private PreparedStatement pstmt = null;
   private ResultSet rs = null;
   private ResultSet rs2 = null;
   
   AES256 aes = null;
   
   public ProductDAO() {
      Context initContext;
      try {
         initContext = new InitialContext();
         Context envContext  = (Context)initContext.lookup("java:/comp/env");
         ds = (DataSource)envContext.lookup("jdbc/myoracle");
         String key = MyKey.key; 
         aes = new AES256(key);
      } catch (NamingException e) {
         e.printStackTrace();
      } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
      }
   }
   public void close() {
      try {
         if(rs!=null) {
            rs.close();
            rs = null;
         }
         if(rs2!=null) {
             rs2.close();
             rs2 = null;
          }
         if(pstmt!=null) {
            pstmt.close();
            pstmt = null;
         }
         if(conn!=null) {
            conn.close();
            conn = null;
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }
   // 모든 카테고리를 List<CategoryVO>로 반환 받는 메소드
   @Override 
   public List<CategoryVO> getAllCategory() throws SQLException{
      List<CategoryVO> cglist = null;
      try {
         conn = ds.getConnection();
         String sql = " select * from spm_category_1st ";
         pstmt = conn.prepareStatement(sql);
         rs = pstmt.executeQuery();
         int cnt=0;
         while(rs.next()) {
            cnt++;
            if(cnt==1) cglist = new ArrayList<CategoryVO>();
            CategoryVO cg = new CategoryVO();
            cg.setCg_1st_idx(rs.getInt("cg_1st_idx"));
            String cg_1st_code = rs.getString("cg_1st_code");
            cg.setCg_1st_name(rs.getString("cg_1st_name").toUpperCase());
            cg.setCg_1st_code(cg_1st_code);
            
            sql = " select * from spm_category_2nd where fk_cg_1st_code=? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cg_1st_code);
            rs2 = pstmt.executeQuery();
            List<HashMap<String,String>> hashcglist = null;
            int cnt2=0;
            while(rs2.next()) {
               cnt2++;
               if(cnt2==1) hashcglist = new ArrayList<HashMap<String,String>>();
               HashMap<String,String> hashcg = new HashMap<String,String>();
               hashcg.put("cg_2nd_idx", rs2.getString("cg_2nd_idx"));
               hashcg.put("cg_2nd_code", rs2.getString("cg_2nd_code"));
               hashcg.put("cg_2nd_name", rs2.getString("cg_2nd_name"));
               hashcglist.add(hashcg);
            }
            cg.setCg2ndList(hashcglist);
            cglist.add(cg);
         }
      } finally {close();}
      return cglist;
   }
   // 모든 상품을 List<ProductVO>로 반환 받는 메소드
   @Override
   public List<ProductVO> getAllProduct() throws SQLException {
      List<ProductVO> plist = null;
      try {
         conn = ds.getConnection();
         String sql = " select * from spm_product order by product_idx desc ";
         pstmt = conn.prepareStatement(sql);
         rs = pstmt.executeQuery();
         int cnt=0;
         while(rs.next()) {
            cnt++;
            if(cnt==1) plist = new ArrayList<ProductVO>();
            int product_idx = rs.getInt("product_idx");
            String fk_cg_1st_code = rs.getString("fk_cg_1st_code");
            String fk_cg_2nd_code = rs.getString("fk_cg_2nd_code");
            String pcode = rs.getString("pcode");
            String pname = rs.getString("pname");
            String mainimg1 = rs.getString("mainimg1");
            String mainimg2 = rs.getString("mainimg2");
            String pspec = rs.getString("pspec");
            int price = rs.getInt("price");
            ProductVO pvo = new ProductVO();
            pvo.setProduct_idx(product_idx);
            pvo.setFk_cg_1st_code(fk_cg_1st_code);
            pvo.setFk_cg_2nd_code(fk_cg_2nd_code);
            pvo.setPcode(pcode);
            pvo.setPname(pname);
            pvo.setMainimg1(mainimg1);
            pvo.setMainimg2(mainimg2);
            pvo.setPspec(pspec);
            pvo.setPrice(price);
            pvo.setCategory(getOneCategory(pcode,false));
            pvo.setStockList(getStockList(pcode,false));
            plist.add(pvo);
         }
      } finally {close();}
      return plist;
   }
   //////////////////////////////////////////////////////////////////////
   // product_idx_seq.nexval의 값을 채번해오는 메소드
   @Override
   public String getProductIdxByPcode() throws SQLException {
      String idx = "";
      try {
         conn = ds.getConnection();
         String sql = " select spm_product_idx_seq.nextval as SEQ from dual";
         pstmt = conn.prepareStatement(sql);
         rs = pstmt.executeQuery();
         if(rs.next()) {
            idx = rs.getString("SEQ");
         }
      }finally {close();}
      return idx;
   }// end of getProductIdxByPcode
   // 매개변수 pname을 통해  pcode 얻어 오는 메소드.
   @Override
   public String getPcodeByPname(String pname) throws SQLException {
	  String pcode = "";
      try {
         conn = ds.getConnection();
         String sql = "select pcode from spm_product where pname = ? ";
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, pname);
         rs= pstmt.executeQuery();
         if(rs.next()) pcode = rs.getString("pcode");
      }finally {close();}
      return pcode;
   }   
   // 기존에 있는 pcoded에서  있는 컬러,사이즈 이면  idx 값 새로운 거면  0 얻어오는 메소드
   // ture라면 DAO 외부에서 호출한 경우 false라면 DAO내에서 호출한 경우
   @Override
   public int getStockIdx(String pcode,String color, String psize, boolean call) throws SQLException{
      int idx = 0;
      if(call) conn = ds.getConnection();
      try {
         String sql = " select stock_idx as idx from spm_stock where fk_pcode = ? and color = ? and psize = ? ";
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, pcode);
         pstmt.setString(2, color);
         pstmt.setString(3, psize);
         rs= pstmt.executeQuery();
         if(rs.next()) idx = rs.getInt("idx");
      } finally {if(call) close();}
      return idx;
   }
   // 기존에 있는 제품을 등록하는 메소드
   @Override
   public int registerExistingProduct(String pcode, String[] colorArr, String[] psizeArr, String[] iqtyArr,
         String[] pimgArr) throws SQLException {
      int result = 0;
      String sql = "";
      try {
         conn = ds.getConnection();
         
         //stock 테이블
         int n1 = 0;
         for(int i =0 ; i<colorArr.length ; i++) {
            String color =  colorArr[i];
            String psize = psizeArr[i];
            String iqty = iqtyArr[i];

            int stock_idx =  getStockIdx(pcode, colorArr[i], psizeArr[i],false);
            if(stock_idx==0) {
               sql = " insert into spm_stock(stock_idx, fk_pcode, psize, color, pqty)  " + 
                     " values(spm_stock_idx_seq.nextval, ?, ?, ?, ?) ";
               pstmt= conn.prepareStatement(sql);
               pstmt.setString(1, pcode);
               pstmt.setString(2, psize);
               pstmt.setString(3, color);
               pstmt.setString(4, iqty);
               n1+=pstmt.executeUpdate();
            }
            else {
               sql = " update spm_stock set pqty = pqty + ? "
                     + " where stock_idx = ? ";
               pstmt = conn.prepareStatement(sql);
               pstmt.setString(1, iqty);
               pstmt.setInt(2, stock_idx);
               n1+=pstmt.executeUpdate();
            }
         }
         int n2 = 0;
         if(pimgArr != null) {
            for(int i= 0; i<pimgArr.length;i++) {
               String pimg = pimgArr[i];
               sql = "insert into spm_product_image(image_idx, fk_pcode, image)\r\n" + 
                      "values(spm_product_image_idx_seq.nextval, ?, ?)";
               pstmt = conn.prepareStatement(sql);
               pstmt.setString(1, pcode);
               pstmt.setString(2,pimg);
               n2+=pstmt.executeUpdate();
            }
         }
         int n3 = 0;
         for(int i= 0; i<colorArr.length;i++) {
              String color =  colorArr[i];
              String psize = psizeArr[i];
              String iqty = iqtyArr[i];

              int stock_idx = getStockIdx(pcode, color, psize, false);
              sql = "insert into spm_import(import_idx, fk_stock_idx, iqty, importdate)\r\n" + 
                    "values(spm_import_idx_seq.nextval, ?, ?, sysdate)";
              pstmt = conn.prepareStatement(sql);
              pstmt.setInt(1, stock_idx );
              pstmt.setString(2,iqty);
              n3+=pstmt.executeUpdate();
         }
         // n3=> colorArr.length 되어야 정상.
         if(n1==colorArr.length &&(pimgArr==null|| n2==pimgArr.length) && n3==colorArr.length) {
            result=1;
         }
      }finally {close();}
      return result ;
   }
   // 새로운 제품 등록하는 메소드
   @Override
   public int registerNewProduct(ProductVO newProduct, String[] colorArr, String[] psizeArr, String[] iqtyArr, String[] pimgArr)
         throws SQLException {
      int result = 0;
      try {
         conn = ds.getConnection();
         conn.setAutoCommit(false);
         String sql = " insert into spm_product(product_idx,  fk_cg_1st_code, fk_cg_2nd_code, pcode, pname\r\n" + 
               "                        , mainimg1, mainimg2, pspec, price, pcompany, totalsqty)\r\n" + 
               "values(?, ?, ?, ?, ?, ? , ? , ?, ? , ?, ?)";
         pstmt= conn.prepareStatement(sql);
         pstmt.setInt(1, newProduct.getProduct_idx());
         pstmt.setString(2, newProduct.getFk_cg_1st_code());
         pstmt.setString(3, newProduct.getFk_cg_2nd_code());
         pstmt.setString(4, newProduct.getPcode());
         pstmt.setString(5, newProduct.getPname());
         pstmt.setString(6, newProduct.getMainimg1());
         pstmt.setString(7, newProduct.getMainimg2());
         pstmt.setString(8,"normal");
         pstmt.setInt(9, newProduct.getPrice());
         pstmt.setString(10, newProduct.getPcompany());
         pstmt.setInt(11, 0);
         int n1 = pstmt.executeUpdate();
         //n1=>1되어야 정상
         int n2 = 0;
         for(int i =0 ; i<colorArr.length ; i++) {
            String pcode = newProduct.getPcode();
            String color =  colorArr[i];
            String psize = psizeArr[i];
            String iqty = iqtyArr[i];

            sql = " insert into spm_stock(stock_idx, fk_pcode, psize, color, pqty)\r\n" + 
                  "values(spm_stock_idx_seq.nextval, ?, ?, ?, ?)";
            pstmt= conn.prepareStatement(sql);
            pstmt.setString(1, pcode);
            pstmt.setString(2, psize );
            pstmt.setString(3, color);
            pstmt.setString(4, iqty);
            n2+=pstmt.executeUpdate();
         }
         // n2=> colorArr.length 되어야 정상
         int n3 = 0;
         if(pimgArr!=null) {
            for(int i= 0; i<pimgArr.length;i++) {
               String pcode = newProduct.getPcode();
               String pimg = pimgArr[i];
               sql = "insert into spm_product_image(image_idx, fk_pcode, image)\r\n" + 
                      "values(spm_product_image_idx_seq.nextval, ? , ?)";
               pstmt = conn.prepareStatement(sql);
               pstmt.setString(1, pcode);
               pstmt.setString(2,pimg);
               n3+=pstmt.executeUpdate();
            }
         }
         // n3=> pimgArr.legnth 되어야 정상   
         int n4 = 0;
         for(int i= 0; i<colorArr.length;i++) {
            String pcode = newProduct.getPcode();
            String color =  colorArr[i];
            String psize = psizeArr[i];
            String iqty = iqtyArr[i];

            int stock_idx = getStockIdx(pcode, color, psize,false);
            System.out.println(stock_idx);
            sql = "insert into spm_import(import_idx, fk_stock_idx, iqty, importdate) " + 
                  "values(spm_import_idx_seq.nextval, ?, ?, sysdate)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, stock_idx );
            pstmt.setString(2,iqty);
            n4+=pstmt.executeUpdate();
         }
         // n4=> colorArr.length 되어야 정상.
         
         if(n1==1 && n2==colorArr.length && (pimgArr==null ||n3==pimgArr.length) && n4==colorArr.length) {
            conn.commit();
        	result=1;
         }
         else conn.rollback();
      }finally {
    	  conn.setAutoCommit(true);
    	  close();
      }
      return result;
   }
   // *** 상품 중복 확인 해주는 메소드 생성하기 *** //
   @Override
   public boolean productDuplicate(String pname) throws SQLException {
      try {
         conn = ds.getConnection();
         String sql = " select count(*) AS CNT from spm_Product where pname = ? ";
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, pname);
         rs = pstmt.executeQuery();
         rs.next();
         int cnt = rs.getInt("CNT");
         if(cnt >= 1) return false; // 기존상품인경우 
         else return true;      // 신규상품인경우
      } finally {close();}
   }// end of boolean ProductDuplicate(String pname)-------------
   /////////////////////////////////////////////////////////////////HY
   // 매개변수 cg_1st_code를 통해 첫번째 카테고리의 정보와 두번째 카테고리의 정보 리스트를 CategoryVO로 반환받는 메소드 
   // call 이 true라면 Action에서 호출했을 경우이고 false라면 다른 메소드에서 DAO안에서 호출했을경우 
   @Override
   public CategoryVO getOneCategory(String cg_1st_code,boolean call) throws SQLException {
      CategoryVO cvo = null;
      try {
    	  if(call) conn = ds.getConnection();
          String sql = " select * from spm_category_1st where cg_1st_code = ? ";
          pstmt = conn.prepareStatement(sql);
          pstmt.setString(1, cg_1st_code);
          rs2 = pstmt.executeQuery();
          if(rs2.next()) {
          	cvo = new CategoryVO();
           	cvo.setCg_1st_idx(rs2.getInt("cg_1st_idx"));
           	cvo.setCg_1st_code(cg_1st_code);
           	cvo.setCg_1st_name(rs2.getString("cg_1st_name"));
           	sql = " select * from spm_category_2nd where fk_cg_1st_code = ? ";
           	pstmt = conn.prepareStatement(sql);
           	pstmt.setString(1, cg_1st_code);
           	rs2 = pstmt.executeQuery();
           	List<HashMap<String,String>> cg2ndList = null;
           	int cnt=0;
           	while(rs2.next()) {
           		cnt++;
           		if(cnt==1) cg2ndList = new ArrayList<HashMap<String,String>>();
           		HashMap<String,String> cg2ndhash = new HashMap<String,String>();
           		cg2ndhash.put("cg_2nd_idx", rs2.getString("cg_2nd_idx"));
           		cg2ndhash.put("cg_2nd_code", rs2.getString("cg_2nd_code"));
           		cg2ndhash.put("cg_2nd_name", rs2.getString("cg_2nd_name"));
           		cg2ndList.add(cg2ndhash);
           	}
           	cvo.setCg2ndList(cg2ndList);
         }
      } finally {if(call)close();}
         return cvo;
   }
   // IDX번호를 통해 하나의 해당 productVO를 가져오는 메소드
   //call 이 true라면 Action에서 호출했을 경우이고 false라면 다른 메소드에서 DAO안에서 호출했을경우
   @Override
   public ProductVO getProductByIdx(String idx,boolean call) throws SQLException {
   	  ProductVO product = null;
   	  if(call) conn = ds.getConnection();
   	  try {
   		  String sql = " select * from spm_product where product_idx = ? ";
   		  pstmt = conn.prepareStatement(sql);
   		  pstmt.setString(1, idx);
   		  rs2 = pstmt.executeQuery();
   		  if(rs2.next()) {
   			  product = new ProductVO();
   			  product.setProduct_idx(rs2.getInt("product_idx"));
   			  product.setFk_cg_1st_code(rs2.getString("fk_cg_1st_code"));
   			  product.setFk_cg_2nd_code(rs2.getString("fk_cg_2nd_code"));
   			  product.setPcode(rs2.getString("pcode"));
   			  product.setPname(rs2.getString("pname"));
   			  product.setPrice(rs2.getInt("price"));
   			  product.setPcompany(rs2.getString("pcompany"));
   			  product.setMainimg1(rs2.getString("mainimg1"));
   			  product.setMainimg2(rs2.getString("mainimg1"));
   			  product.setPspec(rs2.getString("pspec"));
   			  product.setTotalsqty(rs2.getInt("totalsqty"));
   		  }
   	  } finally {
   		  if(call) close();
   		  if(rs2!=null) rs2.close();
   	  }
   	  return product;
  }
// 매개변수 pcode를 통해 해당 ProductVO 객체를 반환받는 메소드
   @Override
   public ProductVO getProductByPcode(String pcode) throws SQLException {
   	  ProductVO product = null;
   	  try {
   		  conn = ds.getConnection();
   		  String sql = " select * from spm_product where pcode = ? order by product_idx ";
   		  pstmt = conn.prepareStatement(sql);
   		  pstmt.setString(1, pcode);
   		  rs = pstmt.executeQuery();
    		  if(rs.next()) {
   			  product = new ProductVO();
   			  product.setProduct_idx(rs.getInt("product_idx"));
   			  product.setFk_cg_1st_code(rs.getString("fk_cg_1st_code"));
   			  product.setFk_cg_2nd_code(rs.getString("fk_cg_2nd_code"));
   			  product.setPcode(rs.getString("pcode"));
   			  product.setPname(rs.getString("pname"));
   			  product.setPrice(rs.getInt("price"));
   			  product.setPcompany(rs.getString("pcompany"));
   			  product.setMainimg1(rs.getString("mainimg1"));
   			  product.setMainimg2(rs.getString("mainimg1"));
   			  product.setPspec(rs.getString("pspec"));
   			  product.setTotalsqty(rs.getInt("totalsqty"));
   			  product.setCategory(getOneCategory(pcode,false));
   			  product.setStockList(getStockList(pcode,false));
   		  }
   	  } finally {close();}
   	  return product;
   }
   // 매개변수 pcode를 통해 해당 ProductVO 객체를 반환받는 메소드
   @Override
   public ProductVO getProductByPcode(String pcode,boolean call) throws SQLException {
	   	  ProductVO product = null;
	   	  try {
	   		  if(call)conn = ds.getConnection();
	   		  String sql = " select * from spm_product where pcode = ? order by product_idx ";
	   		  pstmt = conn.prepareStatement(sql);
	   		  pstmt.setString(1, pcode);
	   		  rs = pstmt.executeQuery();
	    		  if(rs.next()) {
	   			  product = new ProductVO();
	   			  product.setProduct_idx(rs.getInt("product_idx"));
	   			  product.setFk_cg_1st_code(rs.getString("fk_cg_1st_code"));
	   			  product.setFk_cg_2nd_code(rs.getString("fk_cg_2nd_code"));
	   			  product.setPcode(rs.getString("pcode"));
	   			  product.setPname(rs.getString("pname"));
	   			  product.setPrice(rs.getInt("price"));
	   			  product.setPcompany(rs.getString("pcompany"));
	   			  product.setMainimg1(rs.getString("mainimg1"));
	   			  product.setMainimg2(rs.getString("mainimg1"));
	   			  product.setPspec(rs.getString("pspec"));
	   			  product.setTotalsqty(rs.getInt("totalsqty"));
	   			  product.setCategory(getOneCategory(pcode,false));
	   			  product.setStockList(getStockList(pcode,false));
	   		  }
	   	  } finally {if(call)close();}
	   	  return product;
	   }
   // 검색어와 일치하는 상품List를 반환하는 메소드
   @Override
   public List<ProductVO> searchItemsByName(String searchName) throws SQLException {
   	  List<ProductVO> searchItemsList = null;
   	  try {
   		  conn = ds.getConnection();
   		  String sql = " select * from spm_product where lower(pname) like lower('%'||?||'%') ";
   		  pstmt = conn.prepareStatement(sql);
   		  pstmt.setString(1, searchName);
   		  rs = pstmt.executeQuery();
   		  int cnt = 0;
   		  while(rs.next()) {
   			  cnt++;
   			  if(cnt==1) searchItemsList = new ArrayList<ProductVO>();
   			  ProductVO item = getProductByIdx(rs.getString("product_idx"),false);
   			  searchItemsList.add(item);
   		  }
   	  } finally {close();}
   	  return searchItemsList;
   }
   // 매개변수 cg_1st_code(첫번째 분류코드)를 통해 해당 분류에 포함되는 모든 상품을 List<ProductVO>로 반환받는 메소드
   @Override
   public List<ProductVO> getProductByCategory(String cg_1st_code) throws SQLException{
   	  List<ProductVO> plist = null;
   	  try {
   		  conn = ds.getConnection();
   		  String sql = " select * from spm_product where fk_cg_1st_code = ? ";
   		  pstmt = conn.prepareStatement(sql);
   		  pstmt.setString(1, cg_1st_code);
   		  rs = pstmt.executeQuery();
   		  int cnt=0;
   		  while(rs.next()) {
   			  cnt++;
   			  if(cnt==1) plist = new ArrayList<ProductVO>();
   			  ProductVO pvo = getProductByIdx(rs.getString("product_idx"),false);
   			  plist.add(pvo);
   		  }
   	  } finally {close();}
   	  return plist;
   }
   // 해당 상품에 존재하는 color를 매개변수 pcode를 통해 List로 가져온다.
   @Override
   public List<String> getColorList(String pcode) throws SQLException{
   	  List<String> colorList = new ArrayList<String>();
   	  try {
   		  conn = ds.getConnection();
   		  String sql = " select distinct color from spm_stock where fk_pcode=? ";
   		  pstmt = conn.prepareStatement(sql);
   		  pstmt.setString(1, pcode);
   		  rs = pstmt.executeQuery();
   		  while(rs.next()) {
   			colorList.add(rs.getString("color"));
   		  }
   	  } finally {close();}
   	  return colorList; 
   }
   // 매개변수 pcode를 통해 해당 상품(Product)의 상세 이미지를 모두 가져오는 메소드 
   @Override
   public List<String> getImgList(String pcode) throws SQLException {
   	  List<String> imgList = null;
   	  try {
   		  conn = ds.getConnection();
   		  String sql = " select * from spm_product_image where fk_pcode= ? ";
   		  pstmt = conn.prepareStatement(sql);
   		  pstmt.setString(1, pcode);
   		  rs = pstmt.executeQuery();
   		  int cnt=0;
   		  while(rs.next()) {
   			  cnt++;
   			  if(cnt==1) imgList = new ArrayList<String>();
   			  imgList.add(rs.getString("image"));
   		  }
   	  } finally {close();}
   	  return imgList; 
   }
   // 장바구니에 이미 돌일 상품이 있는지 확인하는 메소드 동일 상품이 있다면 ture를 반환한다.
   public boolean checkCartItem(String fk_userid,String pcode,String fk_stock_idx) throws SQLException {
	   boolean bool = false;
	   try {
		   conn = ds.getConnection();
		   String sql = " select count(*) as cnt from spm_cart where fk_userid=? and fk_pcode=? and fk_stock_idx ";
		   pstmt = conn.prepareStatement(sql);
		   pstmt.setString(1, fk_userid);
		   pstmt.setString(2, pcode);
		   pstmt.setString(3, fk_stock_idx);
		   rs = pstmt.executeQuery();
		   rs.next();
		   if(rs.getInt("cnt")!=0) bool = true;
	   } finally {close();}
	   return bool;
   }
   // 장바구니에 상품을 추가하는 메소드
   @Override
   public int addCart(String fk_userid,String fk_pcode,int fk_stock_idx,int oqty) throws SQLException {
   	  int result = 0;
   	  try {
   		  conn = ds.getConnection();
   		  String sql = " select cart_idx from spm_cart "
   		  			 + " where fk_userid = ? and fk_stock_idx = ? ";
   		  pstmt = conn.prepareStatement(sql);
   		  pstmt.setString(1, fk_userid);
   		  pstmt.setInt(2, fk_stock_idx);
   		  rs = pstmt.executeQuery();
   		  if(rs.next()) {
   			  int cart_idx = rs.getInt("cart_idx");
   			  sql = " update spm_cart set oqty = oqty + ? where cart_idx = ? ";
   			  pstmt = conn.prepareStatement(sql);
   			  pstmt.setInt(1, oqty);
   			  pstmt.setInt(2, cart_idx);
   			  result = pstmt.executeUpdate();
   		  }
   		  else {
	   		  sql = " insert into spm_cart(cart_idx,fk_userid,fk_pcode,fk_stock_idx,oqty) "
	   		  			 + " values(spm_cart_idx_seq.nextval,?,?,?,?) ";
	   		  pstmt = conn.prepareStatement(sql);
	   		  pstmt.setString(1, fk_userid);
	   		  pstmt.setString(2, fk_pcode);
	   		  pstmt.setInt(3, fk_stock_idx);
	   		  pstmt.setInt(4, oqty);
	   		  result = pstmt.executeUpdate();
   		  }
   	  } finally {close();}
   	  return result;
   }
   // 해당 상품의 재고를 조회하는 메소드
   // 회원 아이디를 통해 해당 회원의 장바구니 목록을 모두 불러오는 메소드
   @Override
   public List<CartVO> getMyCart(String fk_userid) throws SQLException {
	   List<CartVO> cartList = null;
		try {
			conn = ds.getConnection();
			String sql = " select * from spm_cart A join spm_product B on A.fk_pcode = B.pcode "
					   + " join spm_stock C on A.fk_stock_idx = C.stock_idx "
					   + " where A.fk_userid = ? order by cart_idx desc ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fk_userid);
			rs = pstmt.executeQuery();
			int cnt = 0;
			while(rs.next()) {
				cnt++;
				if(cnt==1) cartList = new ArrayList<CartVO>();
				CartVO cart = new CartVO();
				String fk_pcode = rs.getString("fk_pcode");
				cart.setCart_idx(rs.getInt("cart_idx"));
				cart.setFk_pcode(fk_pcode);
				cart.setFk_userid(rs.getString("fk_userid"));
				cart.setOqty(rs.getInt("oqty"));
				cart.setFk_stock_idx(rs.getInt("fk_stock_idx"));
				cart.setOsize(rs.getString("psize"));
				cart.setOcolor(rs.getString("color"));
				cart.setPqty(rs.getString("pqty"));
				cart.setProductByPcode(fk_pcode);
				cartList.add(cart);
			}
		} finally {close();}
		return cartList;
	}
	//call 이 true라면 Action에서 호출했을 경우이고 false라면 다른 메소드에서 DAO안에서 호출했을경우
   // 매개변수 pcode를 통해 해당상품의 컬러와 사이즈 종류를 모두 리스트로 가져오는 메소드
   @Override
   public List<HashMap<String,String>> getStockList(String pcode,boolean call) throws SQLException {
	   List<HashMap<String,String>> stockList = null;
	   try {
			if(call)conn = ds.getConnection();
			String sql = " select * from spm_stock where fk_pcode = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pcode);
			rs2 = pstmt.executeQuery();
			int cnt=0;
			while(rs2.next()) {
				cnt++;
				if(cnt==1) stockList = new ArrayList<HashMap<String,String>>();
				HashMap<String,String> stockHash = new HashMap<String,String>();
				stockHash.put("stock_idx", rs2.getString("stock_idx"));
				stockHash.put("fk_pcode", pcode);
				stockHash.put("color", rs2.getString("color"));
				stockHash.put("psize", rs2.getString("psize"));
				stockHash.put("pqty", rs2.getString("pqty"));
				stockList.add(stockHash);
			}
		} finally {if(call)close();}
		return stockList;
	}
    // 해당 상품의 해당컬러의 사이즈가 무엇이 존재하는지 List로 반환하는 메소드
    @Override
    public List<HashMap<String,Object>> getSizeOfColor(String fk_pcode,String color) throws SQLException{
    	List<HashMap<String,Object>> sizeList=null;
    	try {
    		conn = ds.getConnection();
    		String sql = " select * from spm_stock where fk_pcode = ? and color = ? ";
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, fk_pcode);
    		pstmt.setString(2, color);
    		rs = pstmt.executeQuery();
    		int cnt=0;
    		while(rs.next()) {
    			cnt++;
    			if(cnt==1) sizeList = new ArrayList<HashMap<String,Object>>();
    			HashMap<String,Object> hash = new HashMap<String,Object>();
    			hash.put("psize", rs.getString("psize"));
    			hash.put("pqty", rs.getInt("pqty"));    			
    			sizeList.add(hash);
    		}
    	} finally {close();}
    	return sizeList;
    }
   // 나의 장바구니에 동일한 상품이있는지 조회하는 메소드/ 동일한 상품이 존재한다면 true를 반환
    @Override
    public boolean checkDuplicateProduct(String fk_userid,int stock_idx) throws SQLException {
    	boolean bool = false;
	 	try {
	 		conn = ds.getConnection();
	 		String sql = " select count(*) as cnt from spm_cart where fk_userid = ? and fk_stock_idx = ? ";
	 		pstmt = conn.prepareStatement(sql);
	 		pstmt.setString(1, fk_userid);
	 		pstmt.setInt(2, stock_idx);
	 		rs = pstmt.executeQuery();
	 		if(rs.next()) if(rs.getInt("cnt")!=0) bool = true;	
	 	} finally {close();}
	 	return bool;
    }
    // 장바구니 삭제버튼 클릭시 delete하는 메소드
    @Override
    public int deleteCart(String cart_idx) throws SQLException{
    	int result =0;
    	try {
    		conn = ds.getConnection();
    		String sql = " delete spm_cart where cart_idx = ? ";
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, cart_idx);
    		result = pstmt.executeUpdate();
    	} finally {close();}
    	return result;
    }
    // 해당 장바구니의 주문수량을 수정하는 메소드
    @Override
    public int editCartOqty(String cart_idx,int oqty) throws SQLException {
    	int result = 0;
    	try{
    		conn = ds.getConnection();
    		String sql = " update spm_cart set oqty = ? where cart_idx = ? ";
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, oqty);
    		pstmt.setString(2, cart_idx);
    		result = pstmt.executeUpdate();
    	} finally {
    		close();}
    	return result;
    }
    // 해당 회원의 주문 목록을 모두 불러오는 메소드
    @Override
    public List<OrderVO> getUserOrderList(String fk_userid) throws SQLException {
    	List<OrderVO> orderList = null;
    	try{
    		conn = ds.getConnection();
    		String sql = " select * from spm_order where fk_userid = ? order by odrcode desc";
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, fk_userid);
    		rs = pstmt.executeQuery();
    		int cnt=0;
    		while(rs.next()) {
    			cnt++;
    			if(cnt==1) orderList = new ArrayList<OrderVO>();
    			OrderVO order = new OrderVO();
    			order.setOdrcode(rs.getString("odrcode"));
    			order.setFk_userid(fk_userid);
    			order.setSumtotalprice(rs.getInt("sumtotalprice"));
    			order.setOrderdate(rs.getString("orderdate"));
    			order.setDvrfee(rs.getInt("dvrfee"));

    			sql = " select * from spm_order_detail A join spm_stock B on A.fk_stock_idx = B.stock_idx "
    				+ " where fk_odrcode = ? order by order_detail_idx desc ";
    			pstmt = conn.prepareStatement(sql);
    			pstmt.setString(1, order.getOdrcode());
    			rs2 = pstmt.executeQuery();
    			List<OrderDetailVO> orderdetailList = null;
    			cnt =0;
    			while(rs2.next()) {
    				cnt++;
    				if(cnt==1) orderdetailList = new ArrayList<OrderDetailVO>();
    				OrderDetailVO orderdetail = new OrderDetailVO();
    				String fk_pcode = rs2.getString("fk_pcode");
    				orderdetail.setOrder_detail_idx(rs2.getInt("order_detail_idx"));
    				orderdetail.setFk_odrcode(rs2.getString("fk_odrcode"));
    				orderdetail.setFk_pcode(fk_pcode);
    				orderdetail.setFk_stock_idx(rs2.getInt("fk_stock_idx"));
    				orderdetail.setOqty(rs2.getInt("oqty"));
    				orderdetail.setTotalPrice(rs2.getInt("totalprice"));
    				orderdetail.setDvrdate(rs2.getString("dvrdate"));
    				orderdetail.setDvrstatus(rs2.getInt("dvrstatus"));
    				orderdetail.setOsize(rs2.getString("psize"));
    				orderdetail.setOcolor(rs2.getString("color"));
    				orderdetail.setPvoByPcode(fk_pcode);
    				orderdetailList.add(orderdetail);
    			}
    			order.setOrderDetailList(orderdetailList);
    			orderList.add(order);
    		}
    	} finally {close();}
    	return orderList;
    }
    // 문의글을 등록하는 메소드
    @Override
    public int insertQna(String fk_userid,String pcode,String content) throws SQLException{
    	int result = 0;
    	try {
    		conn = ds.getConnection();
    		String sql = " insert into spm_memo(MEMO_IDX,FK_USERID,FK_PCODE,MEMO_CONTENT,MEMO_WRITEDATE) "
    				   + " values(spm_memo_idx_seq.nextval,?,?,?,default) ";
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, fk_userid);
    		pstmt.setString(2, pcode);
    		pstmt.setString(3, content);
    		result = pstmt.executeUpdate();
    	} finally {close();}
    	return result;
    }
    // 모든 문의글을 조회하는 메소드
    @Override
    public List<HashMap<String,String>> getAllMemoList() throws SQLException {
    	List<HashMap<String,String>> memoList = null;
    	try {
    		conn = ds.getConnection();
    		String sql = " select * from spm_memo order by MEMO_IDX desc ";
    		pstmt = conn.prepareStatement(sql);
    		rs = pstmt.executeQuery();
    		int cnt=0;
    		while(rs.next()) {
    			cnt++;
    			if(cnt==1) memoList = new ArrayList<HashMap<String,String>>();
    			HashMap<String,String> memo = new HashMap<String,String>();
    			memo.put("memo_idx", rs.getString("memo_idx"));
    			memo.put("fk_pcode", rs.getString("fk_pcode"));
    			memo.put("fk_userid", rs.getString("fk_userid"));
    			memo.put("memo_content", rs.getString("memo_content"));
    			memo.put("memo_writedate", rs.getString("memo_writedate"));
    			memoList.add(memo);
    		}
    	} finally{close();}
    	return memoList;
    }
    // 해당 상품의 문의글을 조회하는 메소드
    @Override
    public List<HashMap<String,String>> getProductMemoList(String pcode) throws SQLException {
    	List<HashMap<String,String>> memoList = null;
    	try {
    		conn = ds.getConnection();
    		String sql = " select * from spm_memo where fk_pcode = ? order by MEMO_IDX desc ";
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, pcode);
    		rs = pstmt.executeQuery();
    		int cnt=0;
    		while(rs.next()) {
    			cnt++;
    			if(cnt==1) memoList = new ArrayList<HashMap<String,String>>();
    			HashMap<String,String> memo = new HashMap<String,String>();
    			memo.put("memo_idx", rs.getString("memo_idx"));
    			memo.put("fk_pcode", rs.getString("fk_pcode"));
    			memo.put("fk_userid", rs.getString("fk_userid"));
    			memo.put("memo_content", rs.getString("memo_content"));
    			memo.put("memo_writedate", rs.getString("memo_writedate"));
    			memoList.add(memo);
    		}
    	} finally{close();}
    	return memoList;
    }
    // 주문 정보를 insert 하는 메소드
    @Override
    public int insertOrder(String odrcode,String fk_userid,int sumtotalprice,List<CartVO> cartList,int dvrfee,
    		String recivername,String reciverpost1, String reciverpost2, String reciveraddr1, String reciveraddr2, 
    		String reciverhp1, String reciverhp2, String reciverhp3, String reciveremail) throws SQLException {
    	int result = 0;
    	try {
    		conn = ds.getConnection();
    		conn.setAutoCommit(false);
    		String sql = " insert into spm_order(odrcode, fk_userid, sumtotalprice, orderdate, dvrfee, recivername, reciverpost1, "
    				   + "reciverpost2, reciveraddr1, reciveraddr2, reciveremail, reciverhp1, reciverhp2, reciverhp3) "
    				   + " values(?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?) ";
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, odrcode);
    		pstmt.setString(2, fk_userid);
    		pstmt.setInt(3, sumtotalprice);
    		pstmt.setInt(4, dvrfee);
    		pstmt.setString(5, recivername);
    		pstmt.setString(6, reciverpost1);
    		pstmt.setString(7, reciverpost2);
    		pstmt.setString(8, reciveraddr1);
    		pstmt.setString(9, reciveraddr2);
    		pstmt.setString(10, reciveremail);
    		pstmt.setString(11, reciverhp1);
    		pstmt.setString(12, reciverhp2);
    		pstmt.setString(13, reciverhp3);
    		
    		result += pstmt.executeUpdate();
    		
    		for(CartVO cart : cartList) {
    			sql = " insert into spm_order_detail(order_detail_idx,fk_odrcode,fk_pcode,fk_stock_idx,oqty,totalprice,dvrdate,dvrstatus) "
 					   + " values(spm_order_detail_idx_seq.nextval,?,?,?,?,?,sysdate+3,default) ";
	 			pstmt = conn.prepareStatement(sql);
	 			pstmt.setString(1, odrcode);
	 			pstmt.setString(2, cart.getFk_pcode());
	 			pstmt.setInt(3, cart.getFk_stock_idx());
	 			pstmt.setInt(4, cart.getOqty());
	 			pstmt.setInt(5, cart.getProduct().getPrice()*cart.getOqty());
	 			result += pstmt.executeUpdate();
    		
	    		sql = " update spm_stock set pqty = pqty - ? where stock_idx = ? ";
	    		pstmt = conn.prepareStatement(sql);
	    		pstmt.setInt(1, cart.getOqty());
	    		pstmt.setInt(2, cart.getFk_stock_idx());
	    		result += pstmt.executeUpdate();
	    		
	    		sql = " update spm_product set totalsqty = totalsqty + ? where pcode = ? ";
	    		pstmt = conn.prepareStatement(sql);
	    		pstmt.setInt(1, cart.getOqty());
	    		pstmt.setString(2, cart.getFk_pcode());
	    		result += pstmt.executeUpdate();
    		}
    		
    		sql = " update spm_member set point = point + ? where userid = ? ";
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, Math.round(sumtotalprice/100));
    		pstmt.setString(2, fk_userid);
    		result += pstmt.executeUpdate();
    		
    		if(result == cartList.size()*3+2){
    			conn.commit();
    			conn.setAutoCommit(true);
    			result = 1;
    		}
    		else result = 0;
    	} finally {close();}
    	return result;
    }
    // cart_idx로 하나의 장바구니 VO를 가져오는 메소드
    @Override
    public CartVO getcartByIdx(String cart_idx) throws SQLException {
    	CartVO cart = new CartVO();
    	try {
    		conn= ds.getConnection();
    		String sql = " select * from spm_cart A join spm_product B on A.fk_pcode = B.pcode " + 
    					 " join spm_stock C on A.fk_stock_idx = C.stock_idx " + 
    					 " where cart_idx = ? order by cart_idx desc ";
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, cart_idx);
    		rs = pstmt.executeQuery();
    		rs.next();
    		String fk_pcode = rs.getString("fk_pcode");
    		cart.setCart_idx(Integer.parseInt(cart_idx));
    		cart.setFk_userid(rs.getString("fk_userid"));
    		cart.setFk_pcode(fk_pcode);
    		cart.setFk_stock_idx(rs.getInt("fk_stock_idx"));
    		cart.setOcolor(rs.getString("color"));
    		cart.setOsize(rs.getString("psize"));
    		cart.setOqty(rs.getInt("oqty"));
    		cart.setProductByPcode(fk_pcode);
    	} finally {close();}
    	return cart;
    }
    //주문번호를 만드는 메소드
    @Override
    public String createOdrcode() throws SQLException {
    	String odrcode = "";
    	try {
    		conn = ds.getConnection();
    		String sql = " select 'S'||to_char(sysdate,'yymmdd')||'-'||spm_order_idx_seq.nextval as odrcode from dual ";
    		pstmt = conn.prepareStatement(sql);
    		rs = pstmt.executeQuery();
    		rs.next();
    		odrcode = rs.getString("odrcode");
    	} finally {close();}
    	return odrcode;
    }
    // 찜하기 눌렀을경우 좋아요수 1 올리기
    @Override
    public int likeCntUP(String fk_userid,String fk_pcode) throws SQLException {
    	int result =0;
    	try {
    		conn = ds.getConnection();
    		String sql =" select count(*) as cnt from spm_like where fk_userid = ? and fk_pcode = ? ";
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, fk_userid);
    		pstmt.setString(2,fk_pcode);
    		rs = pstmt.executeQuery();
    		rs.next();
    		int cnt = rs.getInt("cnt");
    		if(cnt==0) {
	    		sql =" insert into spm_like(like_idx,fk_userid,fk_pcode) values(spm_like_seq.nextval,?,?) ";
	    		pstmt = conn.prepareStatement(sql);
	    		pstmt.setString(1, fk_userid);
	    		pstmt.setString(2, fk_pcode);
	    		result = pstmt.executeUpdate();
    		}
    		else result = 0;
    	} finally {close();}
    	return result;
    }
    // 해당 상품의 좋아요 수 가져오기
    @Override
    public int getLikeCnt(String pcode) throws SQLException {
    	int result =0;
    	try {
    		conn = ds.getConnection();
    		String sql =" select count(*) as cnt from spm_like where fk_pcode = ? ";
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, pcode);
    		rs = pstmt.executeQuery();
    		rs.next();
    		result = rs.getInt("cnt");
    	}finally {close();}
    	return result;
    }
    // 내가 좋아요한 게시물 모아보기
    @Override
    public List<String> getLikeItem(String userid) throws SQLException{
    	List<String> plist = new ArrayList<String>();
    	try {
    		conn = ds.getConnection();
    		String sql = " select * from spm_like where fk_userid = ? ";
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, userid);
    		rs = pstmt.executeQuery();
    		while(rs.next()) plist.add(rs.getString("fk_pcode"));
    	} finally {close();}
    	return plist;
    }
    // 재고량 가져오는 메소드
    @Override
    public int getPqty(int stock_idx) throws SQLException{
    	int result =0;
    	try {
    		conn = ds.getConnection();
    		String sql = " select * from spm_stock where stock_idx = ? ";
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, stock_idx);
    		rs = pstmt.executeQuery();
    		if(rs.next()) result = rs.getInt("pqty");
    	} finally {close();}
    	return result;
    }
    @Override
	public List<ProductVO> getSearchedPname(String pname, String categoryCode) throws SQLException {
		
		List<ProductVO> prodList = null;
		
		 try {
	         conn = ds.getConnection();
	         String sql =  "   select  case when length(pname) > 15 then substr(pname,1,15) "  
						+ "           else pname end as pname "
						+ "		,product_idx,fk_cg_1st_code,fk_cg_2nd_code,pcode,mainimg1,mainimg2,pspec,price "
						+ "		from spm_product "
	         		+ "			where lower(pname) like '%'|| lower(?) || '%' and pcode like '"+categoryCode+"'||'%'" + 
	         		"			order by product_idx desc ";
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, pname);
	         rs = pstmt.executeQuery();
	         
	         int cnt=0;
	         while(rs.next()) {
	            cnt++;
	            if(cnt==1) {
	            	 prodList = new ArrayList<ProductVO>();
	            }
	            int product_idx = rs.getInt("product_idx");
	            String fk_cg_1st_code = rs.getString("fk_cg_1st_code");
	            String fk_cg_2nd_code = rs.getString("fk_cg_2nd_code");
	            String pcode = rs.getString("pcode");
	            String v_pname = rs.getString("pname");
	            String mainimg1 = rs.getString("mainimg1");
	            String mainimg2 = rs.getString("mainimg2");
	            String pspec = rs.getString("pspec");
	            int price = rs.getInt("price");
	            ProductVO pvo = new ProductVO();
	            pvo.setProduct_idx(product_idx);
	            pvo.setFk_cg_1st_code(fk_cg_1st_code);
	            pvo.setFk_cg_2nd_code(fk_cg_2nd_code);
	            pvo.setPcode(pcode);
	            pvo.setPname(v_pname);
	            pvo.setMainimg1(mainimg1);
	            pvo.setMainimg2(mainimg2);
	            pvo.setPspec(pspec);
	            pvo.setPrice(price);
	            
	            sql =" select cg_1st_name from spm_category_1st where cg_1st_code = ?";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, fk_cg_1st_code);
	            rs2 = pstmt.executeQuery();
	            rs2.next();
	            String fullCategory = rs2.getString("cg_1st_name");
	            
	            sql =" select cg_2nd_name from spm_category_2nd where cg_2nd_code = ?";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, fk_cg_2nd_code);
	            rs2 = pstmt.executeQuery();
	            rs2.next();
	            fullCategory +=" >> "+ rs2.getString("cg_2nd_name");
	            
	            List<HashMap<String,String>> stockList = new ArrayList<HashMap<String,String>>();
	            
	            sql = "select psize, color, pqty from spm_stock where fk_pcode = ?";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, pcode);
	            
	            rs2 = pstmt.executeQuery();
	            
	            while(rs2.next()) {
	            	String psize = rs2.getString("psize");
	            	String color = rs2.getString("color");
	            	String pqty = rs2.getString("pqty");
	            	
	            	HashMap<String,String> stockMap = new HashMap<String,String>();
	            	stockMap.put("psize", psize);
	            	stockMap.put("color", color);
	            	stockMap.put("pqty", pqty);
	            	
	            	stockList.add(stockMap);
	            }            
	            
	            pvo.setFullCategory(fullCategory);
	            pvo.setStockList(stockList);
	            prodList.add(pvo);
	         }
	       
	      } finally {close();}
				
		return prodList;
	}
	@Override
	public int updateProduct(String pcode, String[] psize, String[] color, String[] pqty, String pname, String price)
			throws SQLException {
		int result = 0;			
		
		try {
			 conn = ds.getConnection();
			 conn.setAutoCommit(false);
			 String sql = "";
			 for(int i=0; i<psize.length; i++) {					 
				 
				 if(Integer.parseInt(pqty[i]) == 0) {
					
					// 제품 비우기(spm_stock 테이블)	
					sql = " delete spm_stock "
						+ " where fk_pcode = ? and psize = ? and color = ? ";	 
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, pcode);
					pstmt.setString(2, psize[i]);
					pstmt.setString(3, color[i]);
					
					result = pstmt.executeUpdate();						
					
				 }
				 
				 else if(Integer.parseInt(pqty[i]) != 0 ) {
					// 기존상품 수량 수정하기 
					sql = " update spm_stock set pqty = ? "
						+ " where fk_pcode = ? and psize = ? and color = ? ";	
											
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, pqty[i]);
					pstmt.setString(2, pcode);
					pstmt.setString(3, psize[i]);
					pstmt.setString(4, color[i]);
					 
					result = pstmt.executeUpdate();
					
					
					// 제품 이름 / 수량 수정하기
					sql = " update spm_product set pname = ?, price = ? "
						+ " where pcode = ? ";
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, pname);
					pstmt.setString(2, price);
					pstmt.setString(3, pcode);
					result = pstmt.executeUpdate();					
				 	}			
				}
			if(result != 0) conn.commit();		 
		} finally {close();}
		return result;
	}
}