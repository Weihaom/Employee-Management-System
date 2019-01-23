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
		   //��ȡϵͳ��ÿ����ɫ���Է��ʵĲ˵�����
		   Map<String,String> menuList=services.queryMenuListForRole();
		   //����ɫ�˵����浽Application������
		   this.getServletContext().setAttribute("sysMenuList", menuList);
		   System.out.println("--------------------------------------------------------------------------------------");
		   System.out.println(menuList);
		   System.out.println("--------------------------------------------------------------------------------------");
	   }
	   catch (Exception e) 
	   {
		  throw new ServletException("-----------InitServlet  �˵���ȡ����----------------------------");
	   }
   }
}
