package com.whm.web.impl;

import com.whm.web.implsupport.S1040Support;

public class S1041Servlet extends S1040Support 
{
	@Override
	public String execute() throws Exception 
	{
		this.savePageData();
		this.saveData("menuList");
		
		return "S1040";
	}

}
