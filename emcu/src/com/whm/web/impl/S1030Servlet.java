package com.whm.web.impl;

import com.whm.web.implsupport.S1030Support;

public class S1030Servlet extends S1030Support
{
	@Override
	public String execute() throws Exception 
	{
		this.update("add", "���");
		return "S1031";
	}

}
