package jdbc.util;

import java.sql.*;

/**
 *  ����Ŭ �����ͺ��̽��� ����Ǿ��� Connection ��ü�� �����ؼ�
 *  ���� �����ִ� Ŭ����
 */

public class DBConnection {

	// static �������(Ŭ���� �������)�� �����Ͽ� DBConnection Ŭ������ ����ϰڴٰ� �ϴ¼���
	// DBConnection Ŭ������ ��ü�� �������� �ʾƵ� static �������(Ŭ���� �������)�� �����Ǿ�����.
	
	// >>>> 1. ����Ŭ ����̹�  <<<<
		private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
		
	// >>>> 2. ����Ŭ ������ ������ �ּ�  <<<<
		private static final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
	
	// >>>> 3. ����Ŭ ������ ������ ������  <<<<
		private static final String USER = "MYORAUSER";
	
	// >>>> 4. ����Ŭ ������ ������ ������ ��ȣ  <<<<
		private static final String PASSWORD = "1234";

	// >>>> 5. ����Ŭ ������ �������ִ� Connection ��ü �޾ƿ���
	
	// **** singleton ���Ͽ��� �߿��� ���� ���� �� �����̴�.**** //
	// ==>ù��°, ��������� ���������ڸ� private ���� �Ͽ� �ܺ� Ŭ���������� ���������� ������ �Ұ��ϰ� �Ѵ�.
	//          ���� static ��������� �����Ͽ� Connection Ŭ������ ����Ҷ� ��ü������ �� 1���� �����ȴ�.	
		private static Connection conn = null;
		
	///// ***** static �ʱ�ȭ �� ***** /////
		/*  DBConnection Ŭ������ ����ϰڴٰ� �ϴ¼��� 
		    DBConnection Ŭ������ ��ü�� �������� �ʾƵ� static �ʱ�ȭ ���� ��1���� ����Ǿ����� �κ��̴�.
		 */
		static{ 
			System.out.println("==> Ȯ�ο� DBConnection ����ƽ �ʱ�ȭ �� ������!! ");
		// <1>. ����Ŭ ����̹� �ε�
			try {
				Class.forName(DRIVER);
			
			
		// <2>. ����Ŭ DB ������ �������ִ� ��ü �����ϱ�. ��, Connection ��ü �����ϱ�
			// conn �� ���� null �̰ų� close �� �����̶�� ���Ӱ� conn �� ���� ������ش�.
				if(conn==null || conn.isClosed() ) {
					// conn.isClosed() ��  conn �� ���������� true, conn �� ���������� false �� �������ش�.
					 
				   conn = DriverManager.getConnection(URL, USER, PASSWORD);
				}
				
			} catch (ClassNotFoundException e) {
				System.out.println("����Ŭ ����̹� �ε� ����!! ojdbc6.jar ������ �����ϴ�.");
				System.out.println(e.getMessage());
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		} // static �ʱ�ȭ �� �� -----------
			
	
	
	// ==>�ι�°, �����ڿ� ���������ڷ� private �� �����Ͽ�, �ܺο��� ����� �ν��Ͻ��� �������� ���ϰ� �Ѵ�.
		private DBConnection() { }	
		
		
	// ==> ����°, static �� getConn() �޼ҵ带 ���ؼ��� �ܺο��� �ش� Ŭ������ ��ü�� ������ �� �ֵ��� �Ѵ�.
	    public static Connection getConn() {
	        System.out.println(">>> Ȯ�ο� DBConnection ����ƽ �޼ҵ� getConn() ȣ���� !!");
	    	return conn;
	    }	
		
		
	// <3>. DB�� �����Ϸ��� ����Ͽ��� ��ü conn �ݾ��ֱ�
		public static void close() {
			if(conn==null)
				return;
			
			try {
				if(!conn.isClosed()) // conn �� ����������
					conn.close();    // conn �� �ݾ��ش�.
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			
			conn = null; // conn �� ���� �ʱ�ȭ ��Ű��
			
		}// end of close() --------------------------------
		
}// end of class DBConnection ////////////////
