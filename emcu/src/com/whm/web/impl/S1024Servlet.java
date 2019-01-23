package com.whm.web.impl;

import com.whm.web.implsupport.S1020Support;

public class S1024Servlet extends S1020Support
{
	@Override
	public String execute() throws Exception 
	{
		this.update("modify", "修改");
		this.addAttribute("msg", "修改成功");
		return "S1021";
	}
	
}
