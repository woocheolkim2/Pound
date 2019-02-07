package common.controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

/**
 * Servlet implementation class FrontController
 */
@WebServlet(
		urlPatterns = { "*.do" }, 
		initParams = { 
				@WebInitParam(name = "propertyConfig", value = "C:/myProject/SemiProject3/WebContent/WEB-INF/Command.properties")
		})
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	HashMap<String,Object> cmdMap = new HashMap<String,Object>();
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		String props = config.getInitParameter("propertyConfig");
		Properties pr = new Properties(); 
		
		FileInputStream fis =null;
		try {
			fis = new FileInputStream(props);
			pr.load(fis); 
			Enumeration<Object> en = pr.keys(); 
			while(en.hasMoreElements()) {
				String key_urlcmd = (String)en.nextElement();
				String className = pr.getProperty(key_urlcmd);
				if(className != null) {
					className = className.trim();
					Class<?> cls = Class.forName(className);
					Object obj = cls.newInstance();
					cmdMap.put(key_urlcmd, obj);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("C:/myProject/SemiProject3/WebContent/WEB-INF/Command.properties�� �����ϴ�.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		requestProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		requestProcess(request, response);
	}
	private void requestProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI(); 
		String ctxPath = request.getContextPath(); 
		String mapKey = uri.substring(ctxPath.length());
		AbstractController action = (AbstractController) cmdMap.get(mapKey);
		if(action==null) {
			System.out.println(mapKey+" url 매핑객체가 없습니다.");
			return;
		}
		else {
			try {
				action.execute(request, response);
				String viewPage = action.getViewPage();
				boolean bool = action.isRedirect();
				if(bool) {
					response.sendRedirect(viewPage);
				}
				else {
					RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
					dispatcher.forward(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}




