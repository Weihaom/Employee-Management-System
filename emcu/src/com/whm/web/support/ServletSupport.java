/**
 * 核心控制器:
 *    负责拦截所有的以.html结尾,用户请求,并且将请求分配到对应的  业务控制器  上
 */
package com.whm.web.support;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.whm.web.support.BaseController;


@WebServlet("*.html")
public final class ServletSupport extends HttpServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String toPath=null;
		try
		{
			//1.获取servlet访问名,并完成初步替换(/--->""   .html--->Servlet)
			String servletPath=request.getServletPath().replace("/", "").replace(".html", "Servlet");
			//2.拼接执行类名称
			String servletName="com.whm.web.impl."+servletPath.substring(0, 1).toUpperCase()+servletPath.substring(1);
			//3.加载业务控制器--基于反射完成对象创建
			BaseController controller=(BaseController)Class.forName(servletName).newInstance();
			
			//向业务控制器织入DTO切片
			controller.setDto(this.createDto(request));    //控制器已经包含了页面数据
			
			//初始化Controller
			controller.initController();
			controller.initRole();
			
			//创建Services
			controller.createServices();   //告诉控制器,实例化Services
			
			//初始化Services
			controller.initServices();  //告诉业务控制器,将代表页面数据的dto,再传给services
			
			//调用流程控制方法
			toPath=controller.execute();
			
			//获取属性
			Map<String,Object> attributes=controller.getAttributes();
			//织入属性处理切片
			this.saveAttributes(request, attributes);
			
		}
		catch(Exception ex)
		{
			request.setAttribute("msg", "网络故障!");
			ex.printStackTrace();
		}
		
		request.getRequestDispatcher("/"+toPath+".jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doGet(request, response);
	}
	
	/**
	 * 属性处理切片
	 * @param request
	 * @param atts
	 */
	private void saveAttributes(HttpServletRequest request,Map<String,Object> atts)
	{
		//1.获取atts中所有的键值对形成集合
		Set<Entry<String,Object>> entrySet=atts.entrySet();
		//2.循环entrySet
		for(Entry<String,Object> entry:entrySet)
		{
			//将Map中每个映射,存储到request中,形成属性
			request.setAttribute(entry.getKey(),entry.getValue());
		}
		//清空属性的临时存储容器
		atts.clear();
	}
	
	/**
	 * DTO切片
	 * @param request
	 * @return
	 */
	private final Map<String,Object>  createDto(HttpServletRequest request)
	{
	    //获取页面数据,形成Map
		Map<String,String[]> tem=request.getParameterMap();
		//计算tem中,键值对的个数
		int count=tem.size();
		//计算安全的初始容量
		int initSize=((int)(count/0.75))+1;
		
		//导出所有键值对,形成集合
		Set<Entry<String,String[]>> entrySet=tem.entrySet();
		//创建DTO对象
		Map<String,Object> dto=new HashMap<>(initSize);
		//定义变量,表示tem的value部分
		String[] val=null;
		//循环集合,获取每个Entry对象
		for(Entry<String,String[]> entry:entrySet)
		{
			//获取entry对象的value部分,形成数组
			val=entry.getValue();
			//依据数组长度判断页面控件类型
			if(val.length==1)  //非checkbox
			{
				//还原数据为页面录入状态
				if(val[0]!=null && !val[0].equals(""))
				{
					dto.put(entry.getKey(), val[0]);	
				}
			}
			else     //checkbox
			{
				//以数组方式,装载页面的checkbox的数据
				dto.put(entry.getKey(), val);
			}	
		}
		return dto;
	}

}
