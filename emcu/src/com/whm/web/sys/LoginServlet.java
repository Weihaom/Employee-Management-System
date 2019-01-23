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
			//1.校验用户名和密码的正确性
			String loginName=request.getParameter("loginName");
			String pwd=request.getParameter("pwd");
			
			//实例化Services
			LoginServicesImpl services=new LoginServicesImpl();
			//查询用户信息
			Map<String,String> userInfo=services.checkUser(loginName, pwd);
			if(userInfo!=null)
			{
				toPath="/main.jsp";
				//将用户信息存储到session中
				request.getSession().setAttribute("userInfo", userInfo);
				
				//处理用户菜单
				this.saveUserMenuList(request, userInfo);
			}
			else
			{
				request.setAttribute("msg", "用户名或密码错误!");
			}	
			
		}
		catch(Exception ex)
		{
			request.setAttribute("msg","网络故障!");
			ex.printStackTrace();
		}
		request.getRequestDispatcher(toPath).forward(request, response);
	}

	/**
	 * 保存用户菜单
	 * @param request
	 * @param userInfo
	 */
	private void saveUserMenuList(HttpServletRequest request,Map<String,String> userInfo)
	{
		//获取用户的角色列表
		String roleList[]=userInfo.get("roleList").split("\\,");
		//获取application中保存的菜单信息
		Map<String,String> sysMenuList=(Map)this.getServletContext().getAttribute("sysMenuList");
		
		//用户菜单集合
		Set<String> userMenuSet=new TreeSet<>();
		
		//循环角色数组
		for(String role:roleList)
		{
			//依据角色获取该角色可以访问的菜单串
			String menu=sysMenuList.get(role);
			//将菜单还原成数组
			if(menu!=null && !menu.equals(""))
			{
				String menuArray[]=menu.split("\\#");	
				//将菜单添加到用户菜单集合中
				for(String element:menuArray)
				{
					userMenuSet.add(element);   //不重复的菜单字符串
				}
			}
		}
		
		//解析菜单
		String userMenu=this.parseMenu(userMenuSet, request.getContextPath());
		System.out.println(userMenu);
		//将用户字符串存储到session中
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
