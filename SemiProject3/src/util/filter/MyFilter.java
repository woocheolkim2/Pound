package util.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
/*
���Ͷ� Servlet 2.3 ������ �߰��� ������,
Ŭ���̾�Ʈ�� ��û�� ������ �ޱ� ���� ����ä�� ���Ϳ� �ۼ��� ������ �����ϴ� ���� ���Ѵ�. 
���� ���͸� ����ϸ� Ŭ���̾�Ʈ�� ��û�� ����ä�� ���� ������Ʈ�� �߰����� �ٸ� ����� �����ų �� �ִ�.

<< ���� ���� ���� >>
1. Filter �������̽��� �����ϴ� �ڹ� Ŭ������ ����.
2. /WEB-INF/web.xml �� filter ������Ʈ�� ����Ͽ� ���� Ŭ������ ����ϴµ�
     ������ web.xml �� ������� �ʰ� @WebFilter ������̼��� ���� ����Ѵ�.
*/
public class MyFilter implements Filter {

	@Override
	public void destroy() {
		// ���� �ν��Ͻ��� �����Ű�� ���� ȣ���ϴ� �޼ҵ�
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// ������ ������ �ۼ��ϴ� �޼ҵ�
		// ==> doPost()���� �ѱ��� �� �������� 
		//     request.getParameter("name"); �� �ϱ�����
		//     request.setCharacterEncoding("UTF-8"); �� 
		//     ���� ���־�� �Ѵ�.
		request.setCharacterEncoding("UTF-8"); 
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// ���� �����̳ʰ� ���� �ν��Ͻ��� �ʱ�ȭ�ϱ� ���ؼ� ȣ���ϴ� �޼ҵ�
		// ����� ����� �ʿ䰡 ����.
	}

}
