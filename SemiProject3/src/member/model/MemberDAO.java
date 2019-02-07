package member.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jdbc.util.AES256;
import jdbc.util.SHA256;
import my.util.MyKey;

public class MemberDAO implements InterMemberDAO {
	private DataSource ds = null; 
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	AES256 aes = null;
	
	public MemberDAO() {
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
	
	   @Override
	   public MemberVO loginMember(String userid,String pwd) throws SQLException {
	      MemberVO membervo = null;
	      try {
	         conn = ds.getConnection();
	         String sql2 = " select member_idx, userid, username, point, email, hp1, hp2, hp3, addr1, addr2, post1, post2, gender,"
	                     + " to_char(lastLoginDate,'yyyy-mm-dd') as lastLoginDate ,"
	                     + " to_char(lastPwdChangeDate,'yyyy-mm-dd') as lastPwdChangeDate "
	                     + " from spm_member ";
	         if("admin".equalsIgnoreCase(userid)) {
	            sql2 += " where status=7 and userid=? and pwd=? ";
	            pstmt = conn.prepareStatement(sql2);
	            pstmt.setString(1, userid);
	            pstmt.setString(2, SHA256.encrypt(pwd));
	            rs = pstmt.executeQuery();
	         }else {
	            sql2 += " where status=1 and userid=? and pwd=? ";
	            pstmt = conn.prepareStatement(sql2);
	            pstmt.setString(1, userid);
	            pstmt.setString(2, SHA256.encrypt(pwd));
	            rs = pstmt.executeQuery();
	         }
	         
	         if(rs.next()) { 
	               membervo = new MemberVO();
	               membervo.setMember_idx(rs.getInt("member_idx"));
	               membervo.setUserid(userid);
	               membervo.setUsername(rs.getString("username"));
	               membervo.setPoint(rs.getInt("POINT")); 
	               membervo.setEmail(aes.decrypt(rs.getString("email")));
	               membervo.setHp1(rs.getString("hp1"));
	               membervo.setHp2(aes.decrypt(rs.getString("hp2")));
	               membervo.setHp3(aes.decrypt(rs.getString("hp3")));
	               membervo.setAddr1(rs.getString("addr1"));
	               membervo.setAddr2(rs.getString("addr2"));
	               membervo.setPost1(rs.getString("post1"));
	               membervo.setPost2(rs.getString("post2"));
	               membervo.setGender(rs.getInt("gender"));
	               membervo.setLastLoginDate(rs.getString("lastLoginDate"));
	               membervo.setLastPwdChangeDate(rs.getString("lastPwdChangeDate"));  
	              
		           String sql1 = " update spm_member set lastLoginDate = sysdate where userid=? ";
		           pstmt = conn.prepareStatement(sql1);
		           pstmt.setString(1, userid);
		           pstmt.executeUpdate();
	         }
	      } catch (UnsupportedEncodingException | GeneralSecurityException e) {
	            e.printStackTrace();
	       } finally {close();}
	      return membervo;
	   }

	   @Override
	   public boolean idDuplicateCheck(String userid) throws SQLException {
	      conn = ds.getConnection();
	      try {
	         String sql = " select count(*) as cnt from spm_member where userid = ? ";  
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, userid);
	         rs = pstmt.executeQuery();
	         rs.next();
	         int cnt =rs.getInt("cnt");
	         if(cnt==1) return false;// Id�ߺ��ΰ��
	         else return true; //�ߺ��� �ƴѰ��
	      } finally {
	         close();
	      }
	   }

	
	@Override
	public int registerMember(MemberVO membervo) throws SQLException {
		int result = 0;		
		try {
			conn = ds.getConnection();
			String sql = " insert into spm_member(member_idx,userid,pwd,username,post1,post2,addr1,addr2,email,hp1,hp2,hp3,gender,birthday,point,lastLoginDate,lastPwdChangeDate,status) \n"+
						 " values(SPM_MEMBER_IDX_SEQ.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,default,default,default,default) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, membervo.getUserid());
			pstmt.setString(2, SHA256.encrypt(membervo.getPwd()));  
			pstmt.setString(3, membervo.getUsername());
			pstmt.setString(4, membervo.getPost1());
			pstmt.setString(5, membervo.getPost2());
			pstmt.setString(6, membervo.getAddr1());
			pstmt.setString(7, membervo.getAddr2());
			pstmt.setString(8, aes.encrypt(membervo.getEmail())); 
			pstmt.setString(9, membervo.getHp1());
			pstmt.setString(10, aes.encrypt(membervo.getHp2())); 
			pstmt.setString(11, aes.encrypt(membervo.getHp3()));  
			pstmt.setInt(12, membervo.getGender());
			pstmt.setString(13, membervo.getBirthday());
			result = pstmt.executeUpdate();
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {close();}
		return result;
	}// end of registerMember(MemberVO membervo)--------
	
	// *** 총회원 명수 *** //
	   @Override
	   public int getTotalCount(String searchType, String searchWord, int period) throws SQLException {
	      int count = 0;
	      try {
	         conn = ds.getConnection();
	         if("email".equals(searchType))
	            searchWord = aes.encrypt(searchWord);
	         	String sql = " select count(*) AS CNT from spm_member where 1=1 ";
	         if(period == -1) {
	            sql += " and " +searchType+ " like '%'|| ? ||'%' ";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, searchWord);
	         }
	         else {
	            sql += " and " +searchType+ " like '%'|| ? ||'%' "
	                +  " and to_date(to_char(sysdate, 'yyyy-mm-dd'), 'yyyy-mm-dd') - to_date(to_char(registerdate, 'yyyy-mm-dd'), 'yyyy-mm-dd') <= ? ";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, searchWord);
	            pstmt.setInt(2, period);
	         }
	         rs = pstmt.executeQuery();
	         rs.next();
	         count = rs.getInt("CNT");
	      }catch(UnsupportedEncodingException | GeneralSecurityException e) {
	         e.printStackTrace();
	      } finally {close();}
	      return count;
	   }// end of getTotalCount(String searchType, String searchWord, int period)---------
	
	@Override
	public int editMember(MemberVO membervo) throws SQLException {
		int result = 0;
	      try {
	         conn = ds.getConnection();
	         String sql = " update spm_member set pwd=?, email=?, hp1=?, hp2=?, hp3=?, post1=?, post2=?, addr1=?, addr2=? "
	                     + " where member_idx = ? ";
	            pstmt = conn.prepareStatement(sql);
	            	            
				pstmt.setString(1, SHA256.encrypt(membervo.getPwd()));  
				pstmt.setString(2, aes.encrypt(membervo.getEmail()));
				pstmt.setString(3, membervo.getHp1());
				pstmt.setString(4, aes.encrypt(membervo.getHp2())); 
				pstmt.setString(5, aes.encrypt(membervo.getHp3()));  
				pstmt.setString(6, membervo.getPost1());
				pstmt.setString(7, membervo.getPost2());
				pstmt.setString(8, membervo.getAddr1());
				pstmt.setString(9, membervo.getAddr2());
				pstmt.setInt(10, membervo.getMember_idx());
				
				result = pstmt.executeUpdate();
			} catch (UnsupportedEncodingException | GeneralSecurityException e) {
				e.printStackTrace();
			} finally {	close();}
			return result;
	}
	
	 //*** 마이페이지에서 내 정보를 불러오는 메소드 생성하기 ***
	@Override
	public MemberVO memberDetail(int member_idx) throws SQLException {
		MemberVO membervo = null;
		try {			
			conn = ds.getConnection(); // DBCP 객체 ds를 통해 톰캣의 context.xml 에 설정되어진 Connection 객체를 빌려오는 것이다.
			
			String sql = " select * "+
					 " from  "+
					 " ( "+
					 "     select rownum AS RNO " +
					 "          , MEMBER_IDX, USERID, PWD, USERNAME, ADDR1, ADDR2, EMAIL, HP1, HP2, HP3 " +
					 "          , POINT, LASTLOGINDATE, LASTPWDCHANGEDATE, STATUS, POST1, POST2, GENDER, BIRTHDAY, REGISTERDATE " +					 
					 "     from " +
					 "     ( " +
					 "         select MEMBER_IDX, USERID, PWD, USERNAME, ADDR1, ADDR2, EMAIL, HP1, HP2, HP3 " +
					 "              , POINT, LASTLOGINDATE, LASTPWDCHANGEDATE, STATUS, POST1, POST2, GENDER, BIRTHDAY, REGISTERDATE " +
					 "         from spm_member " +
    			     "         order by MEMBER_IDX desc " +
				     "     ) V " +
				     " ) T " +
				     "   where MEMBER_IDX = ?";
			
				pstmt = conn.prepareStatement(sql);	
				pstmt.setInt(1, member_idx);
			    rs = pstmt.executeQuery();
			
			    rs.next();
				int v_idx = rs.getInt("MEMBER_IDX");
				String userid = rs.getString("USERID");
				String username = rs.getString("USERNAME");
				String pwd = rs.getString("PWD");
				String email = aes.decrypt(rs.getString("EMAIL"));  // 이메일을 AES256 알고리즘으로 복호화
				String hp1 = rs.getString("HP1");
				String hp2 = aes.decrypt(rs.getString("HP2"));  // 휴대폰을 AES256 알고리즘으로 복호화
				String hp3 = aes.decrypt(rs.getString("HP3"));  // 휴대폰을 AES256 알고리즘으로 복호화
				String post1 = rs.getString("POST1");
				String post2 = rs.getString("POST2");
				String addr1 = rs.getString("ADDR1");
				String addr2 = rs.getString("ADDR2");
				int gender = rs.getInt("GENDER");
				String birthday = rs.getString("BIRTHDAY");				
				String registerdate = rs.getString("REGISTERDATE");
				int status = rs.getInt("STATUS");					
				int point = rs.getInt("POINT");
				String lastlogindate = rs.getString("lastlogindate");
				String lastpwdchangedate = rs.getString("lastpwdchangedate");
			
				membervo = new MemberVO(v_idx, userid, pwd, username, post1, post2, addr1, addr2,
				         email, hp1, hp2, hp3, gender, birthday, point, lastlogindate, lastpwdchangedate, status, registerdate);
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {close();}
		return membervo;
	}
	// 입력한 userid 받아와서 찾으려는 userid 조회하기 
	   @Override
	   public String getUserid(String name, String mobile) throws SQLException {
	      String userid = null;
	      try {
	         conn = ds.getConnection();
	         String sql = " select userid from spm_member where "
	         		    + " status = 1 and username = ? and trim(hp1)||trim(hp2)||trim(hp3) = ? ";
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, name);
	         String mobilenumber = mobile.substring(0, 3);// 앞에 3자리 빼고 
	         
	         if(mobile.length() == 10) {// 국번이 3자리일대 번호 총길이
	            mobilenumber += aes.encrypt(mobile.substring(3, 6));
	            mobilenumber += aes.encrypt(mobile.substring(6));
	         } 
	         else if(mobile.length() == 11) {// 국번이 4자리일대 번호 총길이
	            mobilenumber += aes.encrypt(mobile.substring(3, 7));
	            mobilenumber += aes.encrypt(mobile.substring(7));
	         }
	         pstmt.setString(2, mobilenumber);
	         rs = pstmt.executeQuery();
	         boolean isExists = rs.next();
	         if(isExists) userid = rs.getString("userid");
	      } catch (UnsupportedEncodingException | GeneralSecurityException e) {
	         e.printStackTrace();
	      } finally {close();}
	      return userid;      
	   }
	// 비밀번호 찾기 일치하는 아이디와 이메일이 있는지 검사해주는 메소드 
	   @Override
	   public int isUserExists(String userid, String email) throws SQLException {
	      int n = 0;
	      try {
	         conn = ds.getConnection();
	         String sql = "select count(*) as CNT from spm_member where "
	         		+ "status = 1 and userid = ? and email = ? ";
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, userid);
	         pstmt.setString(2, aes.encrypt(email));
	         rs = pstmt.executeQuery();
	         rs.next();
	         n = rs.getInt("CNT");
	                  
	      } catch (UnsupportedEncodingException | GeneralSecurityException e) {
	         e.printStackTrace();
	      } finally {close();}
	      return n;
	   }
	// 새 비밀번호 설정하기 
	   @Override
	   public int editPwdUser(String userid, String pwd) throws SQLException {
	      int result = 0;
	      try {
	         conn = ds.getConnection();
	         String sql = " update spm_member set pwd=?, lastLoginDate = sysdate where userid = ?";
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, SHA256.encrypt(pwd)); 
	         pstmt.setString(2, userid);
	         result = pstmt.executeUpdate();
	      } finally {close();}   
	      return result;
	   }
	   // *** 회원탈퇴 *** 
	   @Override
	   public int deleteMember(String idx) throws SQLException {
	      int result = 0;
	      try {
	         conn = ds.getConnection();
	         String sql = " delete spm_member where member_idx = ? ";
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, idx);
	         result = pstmt.executeUpdate();
	      } finally {close();}
	      return result;
	   }

   // 전체 회원 조회
   // ***** 페이징처리 안한 전체회원을 보여주는 메소드 생성하기 ***** //
	   @Override
   public List<MemberVO> getAllMember() throws SQLException {  
	   List<MemberVO> memberList = null;
	      try {
	         conn = ds.getConnection();         
	         String sql = "select member_idx, userid, pwd, username, addr1, addr2, email, hp1, hp2, hp3, point\n"+
						   "     , lastlogindate, lastpwdchangedate, status, post1, post2, gender, birthday, registerdate\n"+
						   "     , trunc(months_between(sysdate, lastLoginDate)) AS lastLoginDategap\n"+
						   "from spm_member\n"+
						   "where status = 1\n"+
						   "order by member_idx desc";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			int cnt = 0;
			while(rs.next()) {
				cnt++; 
			    if(cnt==1) memberList = new ArrayList<MemberVO>();
			    int member_idx = rs.getInt("member_idx");
				String userid = rs.getString("USERID");
				String username = rs.getString("username");
				String pwd = rs.getString("PWD");
				String email = aes.decrypt(rs.getString("EMAIL") );   // 이메일을 AES256 알고리즘으로 복호화
				String hp1 = rs.getString("HP1");
				String hp2 = aes.decrypt(rs.getString("HP2") );   // 휴대폰을 가운데자리 AES256 알고리즘으로 복호화
				String hp3 = aes.decrypt(rs.getString("HP3") );   // 휴대폰을 뒷자리 AES256 알고리즘으로 복호화
				String post1 = rs.getString("POST1");
				String post2 = rs.getString("POST2");
				String addr1 = rs.getString("ADDR1");
				String addr2 = rs.getString("ADDR2");
				int gender = rs.getInt("GENDER");
				String birthday = rs.getString("BIRTHDAY");
				int point = rs.getInt("POINT");
				String registerdate = rs.getString("registerdate");
				int status = rs.getInt("STATUS");
				String lastlogindate = rs.getString("lastlogindate");
				String lastpwdchangedate = rs.getString("lastpwdchangedate");
				int lastLoginDategap = rs.getInt("LASTLOGINDATEGAP");
				MemberVO membervo = new MemberVO(member_idx, userid, pwd, username, post1, post2, addr1, addr2,
												 email, hp1, hp2, hp3, gender, birthday, point, lastlogindate, lastpwdchangedate, status, registerdate);
				if(lastLoginDategap >= 12) membervo.setRequireLastLogin(true);
				memberList.add(membervo);
				}
		  } catch (UnsupportedEncodingException | GeneralSecurityException e) {
		     e.printStackTrace();
		  } finally {close();}
	      return memberList;
	   }

   @Override
   public MemberVO getMemberByIdx(String idx) throws SQLException {
	MemberVO mvo = null;
		try {
			conn = ds.getConnection();
			String sql = "select member_idx, userid, pwd, username, addr1, addr2, email, hp1, hp2, hp3, point\n"+
		               "     , lastlogindate, lastpwdchangedate, status, post1, post2, gender, birthday, registerdate\n"+
		               "from spm_member\n"+
		               "where status = 1 and member_idx = ? \n"+
		               "order by member_idx desc";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, idx);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
			  int member_idx = rs.getInt("member_idx");
	            String userid = rs.getString("USERID");
	            String username = rs.getString("username");
	            String pwd = rs.getString("PWD");
	            String email = aes.decrypt(rs.getString("EMAIL") );   // 이메일을 AES256 알고리즘으로 복호화
	            String hp1 = rs.getString("HP1");
	            String hp2 = aes.decrypt(rs.getString("HP2") );   // 휴대폰을 가운데자리 AES256 알고리즘으로 복호화
	            String hp3 = aes.decrypt(rs.getString("HP3") );   // 휴대폰을 뒷자리 AES256 알고리즘으로 복호화
	            String post1 = rs.getString("POST1");
	            String post2 = rs.getString("POST2");
	            String addr1 = rs.getString("ADDR1");
	            String addr2 = rs.getString("ADDR2");
	            int gender = rs.getInt("GENDER");
	            String birthday = rs.getString("BIRTHDAY");
	            int point = rs.getInt("POINT");
	            String registerdate = rs.getString("registerdate");
	            int status = rs.getInt("STATUS");
	            
	            String lastlogindate = rs.getString("lastlogindate");
	            String lastpwdchangedate = rs.getString("lastpwdchangedate");
				
				mvo = new MemberVO(member_idx, userid, pwd, username, post1, post2, addr1, addr2,
				         email, hp1, hp2, hp3, gender, birthday, point, lastlogindate, lastpwdchangedate, status, registerdate);
			}// end of while
		} catch (UnsupportedEncodingException | GeneralSecurityException e ) {
			e.printStackTrace();
		} finally {close();}
		return mvo;
   }
	@Override
	public int clearMember(String idx) throws SQLException {
		int result = 0;
	      try {
	         conn = ds.getConnection();
	         String sql = " update spm_member set lastLoginDate = sysdate"
	                  + " where member_idx = ? ";
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, idx);
	         result = pstmt.executeUpdate();
	      } finally {close();}
	      return result;
	}
	
	// 사용한 point 차감
	@Override
	public int updatePoint(int spentpoint, String userid) throws SQLException {
		int result=0;
		try {
			conn = ds.getConnection();
			String sql = " update spm_member set point = point - ? where userid = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, spentpoint);
			pstmt.setString(2, userid);
			result=pstmt.executeUpdate();
		} finally {close();}
		return result;
	}
}
