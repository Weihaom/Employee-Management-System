package com.whm.web.impl;

import com.whm.web.implsupport.S1030Support;

public class S1031Servlet extends S1030Support {

	@Override
	public String execute() throws Exception 
	{
		this.savePageData();
		return "S1030";
	}

}
