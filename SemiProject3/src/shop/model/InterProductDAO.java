package shop.model;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface InterProductDAO {

   List<ProductVO> getAllProduct() throws SQLException;

   List<CategoryVO> getAllCategory() throws SQLException;

   String getProductIdxByPcode() throws SQLException;
   
   String getPcodeByPname(String pname) throws SQLException;
   
   int registerExistingProduct(String pcode, String[] colorArr,String[] psizeArr,String[] iqtyArr,String[] pimgArr) throws SQLException;
   
   int registerNewProduct(ProductVO newProduct,String[] colorArr, String[] psizeArr ,String[] iqtyArr,String[] pimgArr) throws SQLException;

   boolean productDuplicate(String pname) throws SQLException;
   
	List<ProductVO> searchItemsByName(String searchName) throws SQLException;
	
	List<ProductVO> getProductByCategory(String cg_1st_code) throws SQLException;
	
	ProductVO getProductByIdx(String idx, boolean call) throws SQLException;

	List<String> getImgList(String pcode) throws SQLException;

	List<HashMap<String, String>> getStockList(String pcode, boolean call) throws SQLException;

	ProductVO getProductByPcode(String pcode) throws SQLException;

	CategoryVO getOneCategory(String cg_1st_code, boolean call) throws SQLException;

	List<CartVO> getMyCart(String fk_userid) throws SQLException;

	List<HashMap<String, Object>> getSizeOfColor(String fk_pcode, String color) throws SQLException;

	List<String> getColorList(String pcode) throws SQLException;

	int addCart(String fk_userid, String fk_pcode, int fk_stock_idx, int oqty) throws SQLException;

	int getStockIdx(String pcode, String color, String psize, boolean call) throws SQLException;

	boolean checkDuplicateProduct(String fk_userid, int stock_idx) throws SQLException;

	int deleteCart(String cart_idx) throws SQLException;

	int editCartOqty(String cart_idx, int oqty) throws SQLException;

	List<OrderVO> getUserOrderList(String fk_userid) throws SQLException;

	int insertQna(String fk_userid, String pcode, String content) throws SQLException;

	List<HashMap<String, String>> getAllMemoList() throws SQLException;

	List<HashMap<String, String>> getProductMemoList(String pcode) throws SQLException;

	String createOdrcode() throws SQLException;

	CartVO getcartByIdx(String cart_idx) throws SQLException;

	int insertOrder(String odrcode, String fk_userid, int sumtotalprice, List<CartVO> cartList, int dvrfee, String recivername,
			String reciverpost1, String reciverpost2, String reciveraddr1, String reciveraddr2, String reciverhp1, String reciverhp2, 
			String reciverhp3, String reciveremail) throws SQLException;

	ProductVO getProductByPcode(String pcode, boolean call) throws SQLException;

	int likeCntUP(String fk_userid, String pcode) throws SQLException;

	int getLikeCnt(String pcode) throws SQLException;

	List<String> getLikeItem(String userid) throws SQLException;
	
	int getPqty(int stock_idx) throws SQLException;

	int updateProduct(String pcode, String[] psize, String[] color, String[] pqty, String pname, String price)
			throws SQLException;

	List<ProductVO> getSearchedPname(String pname, String categoryCode) throws SQLException;

}