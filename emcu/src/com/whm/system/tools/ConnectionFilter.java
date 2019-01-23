package com.whm.system.tools;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;

import com.whm.system.db.DBUtils;

/**
 * Servlet Filter implementation class ConnectionFilter
 */
@WebFilter("/*")
public class ConnectionFilter extends HttpServlet implements Filter 
{
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		try
		{
			//������Ŀ���ַ����
			chain.doFilter(request, response);	
		}
		finally
		{
			//�������������,Ӧ��JDBC�����ʱ���
			DBUtils.close();
		}
	}

	public void init(FilterConfig fConfig) throws ServletException 
	{
	}

}
