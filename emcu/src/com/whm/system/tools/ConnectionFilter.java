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
			//请求向目标地址传递
			chain.doFilter(request, response);	
		}
		finally
		{
			//整个请求过程中,应用JDBC的最后时间点
			DBUtils.close();
		}
	}

	public void init(FilterConfig fConfig) throws ServletException 
	{
	}

}
