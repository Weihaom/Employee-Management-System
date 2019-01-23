package com.whm.web.impl;

import com.whm.web.implsupport.S1040Support;

public class S1042Servlet extends S1040Support 
{
	@Override
	public String execute() throws Exception 
	{
		this.update("grant", "ÊÚÈ¨");
		
		this.updateForQuery();
		
		return "S1040";
	}

}
