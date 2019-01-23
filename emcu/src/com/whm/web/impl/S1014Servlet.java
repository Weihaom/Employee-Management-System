package com.whm.web.impl;

import com.whm.web.implsupport.S1010Support;

public class S1014Servlet extends S1010Support {

	@Override
	public String execute() throws Exception 
	{
		this.update("modify", "ÐÞ¸Ä");
		return "S1011";
	}

}
