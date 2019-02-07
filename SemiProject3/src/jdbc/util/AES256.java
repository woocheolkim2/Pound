/*
	 ?? �??��?��?�� ??
	 - ?��?��(Cryptography) : ?��?�� 불�??��?�� ?��?���? �??��?��거나 ?��?�� ?��?��?��?�� 메시�?�? ?��?�� �??��?�� ?��?���? �??��?��?�� 기술
	 - ?���?(Plaintext)    : ?��?�� �??��?�� ?��?��?�� 메시�?
	 - ?��?���?(Ciphertext) : ?��?�� 불�??��?�� ?��?��?�� 메시�?
	 - ?��?��?��(Encryption) : ?��문을 ?��?��문으�? �??��?��?�� 과정
	 - 복호?��(Decryption) : ?��?��문을 ?��문으�? �??��?��?�� 과정
	 - ??�??�� ?��?��(?��?�� 비�??�� ?��?��) : ?��?��?��?��?? 복호?��?���? 같�? ?��?��
	 - 비�?�??�� ?��?��(?��?�� 공개?�� ?��?��) : ?��?��?��?��?? 복호?��?���? ?���? ?��?��
	
	
	?? ?��방향 ?��?��?�� ?��고리�?(AES-256 Advanced Encryption Standard) ??
	
	  ?��방향 ?��?��?�� ?��고리즘�? ?��문에?�� ?��?��문으�?, ?��?��문에?�� ?��문으�? �??��?��?�� ?��?��?�� �? 복호?���? ?��루어�??�� ?��고리�? ?��?��. 
	  많이 ?��?��?��?�� ?��고리즘�? AES-256 ?��?��?��. 
	  주로 ?���?, 주소, ?��?���? ?�� 복호?�� ?��?��?�� ?��?��?�� ?��보�?? ?�� ?��고리즘을 ?��?��?��?�� ?��?��문으�? �?리한?��.
	 
	 ?��?��??  Java �? ?��?��?�� 구현  ?��?��??
	 == 개발?�� �?비단�? ==
	  기본?���? ?��공하?�� JDK�? ?��치하�? ?��?�� ?��고리즘을 만들 ?�� ?��?�� API�? ?��공되�?�?, 
	 AES-128 보다 ?��?���? ?�� ?��?? ?��계인 AES-256?�� 구현?���? ?��?��?��?�� 별도?�� ?��?��브러�? ?��?�� ?��?��?�� ?��?��?��?��.
	 ?��?��?��?�� ?��?��?���??�� JDK ?��?��로드 ?��?���??�� �?�? ?��?��처럼 JCE �? ?��?��받는?��.
	  ?��?��?�� JRE 버전?�� 맞는 ?��?�� ?��?��?�� ?��?��로드 받아?�� ?��축을 ?�� ?�� local_policy.jar ?��?���? US_export_policy.jar ?��?��?��
	 JDK?��치경�?\jre\lib\security ??  JRE?��치경�?\lib\security ?�� 
	 local_policy.jar ?��?���? US_export_policy.jar ?���? ?��?��?�� 모두 붙여?��기�?? ?��?�� ?��?��?��?��.
	 (Linux 계열?��?�� JDK?��치경로에�? ?��?��주면 ?��결됨)
	 (JDK ?���? 경로�? 모르�? ?��컴퓨?�� ?��?���? > ?��?�� > 고급 ?��?��?�� ?��?�� > ?��경�??�� > JAVA_HOME?�� 찾는?��)
	  그런 ?��?��?�� WAS(?���?)�? ?��구동?��?��. 
	  
	 https://mvnrepository.com/artifact/commons-codec/commons-codec ?�� �??��
	 ?��?���?�? 버전?�� ?��?��?�� 1.11 ?�� ?��?���??�� jar(327 KB)�? ?���??��?�� ?��?��?�� 받는?��. 
	 ?��?��받�? ?��?��명�? commons-codec-1.11.jar ?��?�� ?�� ?��?��?�� C:\Program Files\Java\jdk1.8.0_112\jre\lib\ext 경로?�� 붙여?��?��.
	 ?��?�� ?��로젝?��?�� Build Path ?�� �??�� Libraies ?��?��?�� Add External JARs.. �? ?���??��?�� commons-codec-1.11.jar ?��?��?�� 직접 ?��?��?��?��. 
	   
	
	 >>> JDK 버전�? Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files ?��?��로드 경로 <<<
	 jdk8 (Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files 8)
	 https://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html
	
	 jdk7 (Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files 7)
	 http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html
	 
	
	 jdk6 (Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files 6)
	 http://www.oracle.com/technetwork/java/javase/downloads/jce-6-download-429243.html  
*/

package jdbc.util;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * ?��방향 ?��?��?�� ?��고리즘인 AES256 ?��?��?���? �??��?��?�� ?��?��?��
 */
public class AES256 {
    private String iv;
    private Key keySpec;

    /** 
     * ?��?��?��
     * 16?��리의 ?��값을 ?��?��?��?�� 객체�? ?��?��?��?��.
     * @param key ?��?��?��/복호?���? ?��?�� ?���?
     * @throws UnsupportedEncodingException ?��값의 길이�? 16?��?��?�� 경우 발생
     */
    public AES256(String key) throws UnsupportedEncodingException {
        this.iv = key.substring(0, 16);
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        int len = b.length;
        if(len > keyBytes.length){
            len = keyBytes.length;
        }
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

        this.keySpec = keySpec;
    }

    /**
     * AES256 ?���? ?��?��?�� ?��?��.
     * @param str ?��?��?��?�� 문자?��
     * @return String
     * @throws NoSuchAlgorithmException
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public String encrypt(String str) throws NoSuchAlgorithmException, GeneralSecurityException, UnsupportedEncodingException{
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
        byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
        String enStr = new String(Base64.encodeBase64(encrypted));
        return enStr;
    }

    /**
     * AES256?���? ?��?��?��?�� txt �? 복호?��?��?��.
     * @param str 복호?��?�� 문자?��
     * @return
     * @throws NoSuchAlgorithmException
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public String decrypt(String str) throws NoSuchAlgorithmException, GeneralSecurityException, UnsupportedEncodingException {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
        byte[] byteStr = Base64.decodeBase64(str.getBytes());
        return new String(c.doFinal(byteStr), "UTF-8");
    }

}// end of class AES256///////////////////////////////////////
