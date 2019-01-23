package com.whm.web.impl;

import com.whm.web.implsupport.S1010Support;
/**
 * ½ÇÉ«²éÑ¯
 */
public class S1011Servlet extends S1010Support
{
	@Override
	public String execute() throws Exception 
	{
		this.savePageData();
		return "S1010";
	}
}
