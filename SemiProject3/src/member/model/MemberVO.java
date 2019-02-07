package member.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MemberVO {
   private int member_idx;               // 회원번호(시퀀스)
   private String userid;               // 회원아이디
   private String pwd;                  // 비밀번호
   private String username;            // 회원명
   private String email;               // 이메일
   
   private String hp1;                  // 휴대폰번호
   private String hp2;                  // 휴대폰번호(가운데)
   private String hp3;                  // 휴대폰번호(마지막)
   
   private String post1;               // 우편번호(앞)
   private String post2;               // 우편번호(뒤)
   
   private String addr1;               // 주소
   private String addr2;               // 상세주소
   
   private int gender;                  // 성별(남자:1 / 여자:2)
   
   private String birthday;            // 생년
   
   private int point;                  // 포인트
   
   private String registerdate;            // 가입일자
   private int status;                  // 회원탈퇴유무(1:가입중 2:탈퇴)
   
   private String lastLoginDate;         // 마지막으로 로그인 한 날짜시간 기록용
   private String lastPwdChangeDate;      // 마지막으로 암호를 변경한 날짜시간 기록용

   private String kakao_auth;
   private String kakao_res;
   
   private boolean requirePwdChange = false;
   // 마지막으로 암호를 변경한 날짜가 현재시각으로 부터 6개월이 지났으면 true
   // 마지막으로 암호를 변경한 날짜가 현재시각으로 부터 6개월이 지나지 않았으면 false 로 표시한다.
   
   private boolean requireLastLogin = false; // 휴면유무(휴면이 아니라면 false, 휴면이면 true)
   // 마지막으로 로그인 한 날짜시간이 현재시각으로 부터 1년이 지났으면 true(휴면으로 지정)
   // 마지막으로 로그인 한 날짜시간이 현재시각으로 부터 1년이 지나지 않았으면 false
   
   public MemberVO() {}
   public MemberVO(int member_idx, String userid, String pwd, String username,String post1,String post2, String addr1, String addr2,
         String email, String hp1, String hp2, String hp3,int gender,String birthday, int point, String lastLoginDate, String lastPwdChangeDate,
         int status, String registerdate) {
      this.member_idx = member_idx;
      this.userid = userid;
      this.pwd = pwd;
      this.username = username;
      this.post1 = post1;
      this.post2 = post2;
      this.addr1 = addr1;
      this.addr2 = addr2;
      this.email = email;
      this.hp1 = hp1;
      this.hp2 = hp2;
      this.hp3 = hp3;
      this.gender = gender;
      this.birthday = birthday;
      this.point = point;
      this.lastLoginDate = lastLoginDate;
      this.lastPwdChangeDate = lastPwdChangeDate;
      this.status = status;
      this.registerdate = registerdate;
   }

   public int getMember_idx() {
      return member_idx;
   }

   public void setMember_idx(int member_idx) {
      this.member_idx = member_idx;
   }

   public String getUserid() {
      return userid;
   }

   public void setUserid(String userid) {
      this.userid = userid;
   }

   public String getPwd() {
      return pwd;
   }

   public void setPwd(String pwd) {
      this.pwd = pwd;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPost1() {
      return post1;
   }
   public void setPost1(String post1) {
      this.post1 = post1;
   }
   public String getPost2() {
      return post2;
   }
   public void setPost2(String post2) {
      this.post2 = post2;
   }
   public String getAddr1() {
      return addr1;
   }

   public void setAddr1(String addr1) {
      this.addr1 = addr1;
   }

   public String getAddr2() {
      return addr2;
   }

   public void setAddr2(String addr2) {
      this.addr2 = addr2;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getHp1() {
      return hp1;
   }

   public void setHp1(String hp1) {
      this.hp1 = hp1;
   }

   public String getHp2() {
      return hp2;
   }

   public void setHp2(String hp2) {
      this.hp2 = hp2;
   }

   public String getHp3() {
      return hp3;
   }
   
   public String getBirthday() {
      return birthday;
   }
   public void setBirthday(String birthday) {
      this.birthday = birthday;
   }
   public void setGender(int gender) {
      this.gender = gender;
   }
   public void setHp3(String hp3) {
      this.hp3 = hp3;
   }

   public int getPoint() {
      return point;
   }

   public void setPoint(int point) {
      this.point = point;
   }

   public String getLastLoginDate() {
      return lastLoginDate;
   }

   public void setLastLoginDate(String lastLoginDate) {
      this.lastLoginDate = lastLoginDate;
   }

   public String getLastPwdChangeDate() {
      return lastPwdChangeDate;
   }

   public void setLastPwdChangeDate(String lastPwdChangeDate) {
      this.lastPwdChangeDate = lastPwdChangeDate;
   }

   public int getStatus() {
      return status;
   }

   public void setStatus(int status) {
      this.status = status;
   }
   public int getGender() {
      return gender;
   }
   
   public String getAllHp() {
      return hp1+"-"+hp2+"-"+hp3;
   }
   
   public String getAllPost() {
      return post1+"-"+post2;
   }
   
   public String getAllAddr() {
      return addr1+" "+addr2;
   }
   public int getShowAge() {
	   Date today = new Date();
	   SimpleDateFormat sfmt = new SimpleDateFormat("yyyy");
	   int year = Integer.parseInt(sfmt.format(today));
	   return  year-Integer.parseInt(birthday.substring(0,4))+1;
   }
   public String getShowGender() {
      if(gender == 1)
         return "남자";
      else 
         return "여자";
   }
   public String getRegisterdate() {
      return registerdate;
   }
   public void setRegisterdate(String registerdate) {
      this.registerdate = registerdate;
   }
   
   public boolean isRequirePwdChange() {
      return requirePwdChange;
   }
   public void setRequirePwdChange(boolean requirePwdChange) {
      this.requirePwdChange = requirePwdChange;
   }
   public boolean isRequireLastLogin() {
      return requireLastLogin;
   }
   public void setRequireLastLogin(boolean requireLastLogin) {
      this.requireLastLogin = requireLastLogin;
   }
	public String getKakao_auth() {
		return kakao_auth;
	}
	public void setKakao_auth(String kakao_auth) {
		this.kakao_auth = kakao_auth;
	}
	public String getKakao_res() {
		return kakao_res;
	}
	public void setKakao_res(String kakao_res) {
		this.kakao_res = kakao_res;
	}
	   
}