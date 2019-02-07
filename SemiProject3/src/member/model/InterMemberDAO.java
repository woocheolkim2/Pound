package member.model;

import java.sql.SQLException;
import java.util.List;

public interface InterMemberDAO {
//////////////////////////////////////////////우철라인 ///////////////////////////////////////////////////////
   MemberVO loginMember(String userid,String pwd) throws SQLException;
   boolean idDuplicateCheck(String userid) throws SQLException;
   int registerMember(MemberVO membervo) throws SQLException;
   
   
   // *** 총회원 명수 *** //
   int getTotalCount(String searchType, String searchWord, int period) throws SQLException;
   
   // *** 검색 및 날짜구간이 있는 페이징 처리를 한 탈퇴한 회원을 포함한 전체회원 목록 가져오는 추상 메소드 *** //
   List<MemberVO> getAllMember() throws SQLException;
   
   // ** IDX를 통한 회원 정보 가져오기 ***
   MemberVO getMemberByIdx(String idx) throws SQLException;
   
   // *** 관리자가 회원 한명 삭제 *** //
   int deleteMember(String idx) throws SQLException;
   
   // *** 관리자가 회원 휴면 해제 *** //
   int clearMember(String idx) throws SQLException;
   
   ////////////////////////////////////////////// 현영라인 /////////////////////////////////////////////////////// 
   
   // *** 회원정보 수정을 해주는 추상 메소드 ***
   int editMember(MemberVO membervo) throws SQLException;
   
   //*** 마이페이지에서 내 정보를 불러오는 추상 메소드 ***
   MemberVO memberDetail(int member_idx) throws SQLException;

   int editPwdUser(String userid, String pwd) throws SQLException;

   int isUserExists(String userid, String email) throws SQLException;

   String getUserid(String name, String mobile) throws SQLException;
   
   // 사용한 포인트 차감
   int updatePoint(int spentpoint, String userid) throws SQLException;
   
}