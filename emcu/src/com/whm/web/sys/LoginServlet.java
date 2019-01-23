package com.whm.web.sys;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/loginServlet.xhtml")
public class LoginServlet extends HttpServlet 
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String toPath="/login.jsp";
		try
		{
			//1.У���û������������ȷ��
			String loginName=request.getParameter("loginName");
			String pwd=request.getParameter("pwd");
			
			//ʵ����Services
			LoginServicesImpl services=new LoginServicesImpl();
			//��ѯ�û���Ϣ
			Map<String,String> userInfo=services.checkUser(loginName, pwd);
			if(userInfo!=null)
			{
				toPath="/main.jsp";
				//���û���Ϣ�洢��session��
				request.getSession().setAttribute("userInfo", userInfo);
				
				//�����û��˵�
				this.saveUserMenuList(request, userInfo);
			}
			else
			{
				request.setAttribute("msg", "�û������������!");
			}	
			
		}
		catch(Exception ex)
		{
			request.setAttribute("msg","�������!");
			ex.printStackTrace();
		}
		request.getRequestDispatcher(toPath).forward(request, response);
	}

	/**
	 * �����û��˵�
	 * @param request
	 * @param userInfo
	 */
	private void saveUserMenuList(HttpServletRequest request,Map<String,String> userInfo)
	{
		//��ȡ�û��Ľ�ɫ�б�
		String roleList[]=userInfo.get("roleList").split("\\,");
		//��ȡapplication�б���Ĳ˵���Ϣ
		Map<String,String> sysMenuList=(Map)this.getServletContext().getAttribute("sysMenuList");
		
		//�û��˵�����
		Set<String> userMenuSet=new TreeSet<>();
		
		//ѭ����ɫ����
		for(String role:roleList)
		{
			//���ݽ�ɫ��ȡ�ý�ɫ���Է��ʵĲ˵���
			String menu=sysMenuList.get(role);
			//���˵���ԭ������
			if(menu!=null && !menu.equals(""))
			{
				String menuArray[]=menu.split("\\#");	
				//���˵���ӵ��û��˵�������
				for(String element:menuArray)
				{
					userMenuSet.add(element);   //���ظ��Ĳ˵��ַ���
				}
			}
		}
		
		//�����˵�
		String userMenu=this.parseMenu(userMenuSet, request.getContextPath());
		System.out.println(userMenu);
		//���û��ַ����洢��session��
		request.getSession().setAttribute("userMenu", userMenu);
	}
	
	private String parseMenu(Set<String> userMenuSet,String  contextPath)
	{
		StringBuilder userMenu=new StringBuilder();
		
		for(String menu:userMenuSet)
		{
			String menuInfo[]=menu.split("\\-");
			userMenu.append("<a href=\""+contextPath+"/"+menuInfo[2]+"\" target=\"VIEW\">")
			        .append(menuInfo[1])
			        .append("</a><br>")
			;
		}
		return userMenu.toString();
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doGet(request, response);
	}

}
