package com.whm.web.impl;

import com.whm.web.implsupport.S1030Support;

public class S1030Servlet extends S1030Support
{
	@Override
	public String execute() throws Exception 
	{
		this.update("add", "Ìí¼Ó");
		return "S1031";
	}

}
