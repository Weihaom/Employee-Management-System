package com.whm.web.impl;

import com.whm.web.implsupport.S1030Support;

public class S1035Servlet extends S1030Support
{
	@Override
	public String execute() throws Exception 
	{
		this.update("batchDelete", "É¾³ý");
		this.updateForQuery();
		return "S1030";
	}
}
