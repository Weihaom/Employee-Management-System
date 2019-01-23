package com.whm.web.impl;

import com.whm.web.implsupport.S1020Support;

public class S1021Servlet extends S1020Support {

	@Override
	public String execute() throws Exception 
	{
		//1.调用添加方法
		this.update("add", "添加");
		
		//2.获取新增用户的登录名称
		Object loginName = this.getVal("loginName");
		//向页面反馈登录名
		String msg = "添加成功，当前用户的登录名是："+loginName;
		this.addAttribute("msg", msg);
		
		return "S1021";
	}

}
