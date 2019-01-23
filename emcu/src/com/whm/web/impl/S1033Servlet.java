package com.whm.web.impl;

import com.whm.web.implsupport.S1030Support;

public class S1033Servlet extends S1030Support
{
	@Override
	public String execute() throws Exception 
	{
		this.update("modify", "ÐÞ¸Ä");
		return "S1031";
	}
}
