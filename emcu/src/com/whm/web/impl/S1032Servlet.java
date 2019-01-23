package com.whm.web.impl;

import com.whm.web.implsupport.S1030Support;

public class S1032Servlet extends S1030Support 
{
	@Override
	public String execute() throws Exception 
	{
		this.saveInstances();
		return "S1031";
	}
}
