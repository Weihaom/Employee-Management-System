package com.whm.web.impl;

import com.whm.web.implsupport.S1020Support;

public class S1024Servlet extends S1020Support
{
	@Override
	public String execute() throws Exception 
	{
		this.update("modify", "�޸�");
		this.addAttribute("msg", "�޸ĳɹ�");
		return "S1021";
	}
	
}
