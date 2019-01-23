package com.whm.web.impl;

import com.whm.web.implsupport.S1020Support;

public class S1023Servlet extends S1020Support {

	@Override
	public String execute() throws Exception 
	{
		this.saveInstances();
		return "S1021";
	}

}
