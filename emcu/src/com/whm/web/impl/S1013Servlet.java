package com.whm.web.impl;

import com.whm.web.implsupport.S1010Support;

public class S1013Servlet extends S1010Support {

	@Override
	public String execute() throws Exception 
	{
		this.saveInstances();
		return "S1011";
	}

}
