package com.whm.web.sys;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.whm.services.impl.InitServicesImpl;


@WebServlet(value="/init",loadOnStartup=0)
public class InitServlet extends HttpServlet
{
   @Override
   public void init() throws ServletException 
   {
	   try 
	   {
		   InitServicesImpl services=new InitServicesImpl();
		   //获取系统中每个角色可以访问的菜单详情
		   Map<String,String> menuList=services.queryMenuListForRole();
		   //将角色菜单保存到Application对象中
		   this.getServletContext().setAttribute("sysMenuList", menuList);
		   System.out.println("--------------------------------------------------------------------------------------");
		   System.out.println(menuList);
		   System.out.println("--------------------------------------------------------------------------------------");
	   }
	   catch (Exception e) 
	   {
		  throw new ServletException("-----------InitServlet  菜单读取错误----------------------------");
	   }
   }
}
