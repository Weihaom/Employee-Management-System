package com.whm.web.impl;

import com.whm.web.implsupport.S1010Support;

/**
 * ½ÇÉ«Ìí¼Ó
 */
public final class S1010Servlet extends S1010Support 
{
	@Override
	public String execute() throws Exception 
	{
		this.update("add", "Ìí¼Ó");
		return "S1011";
	}

}
