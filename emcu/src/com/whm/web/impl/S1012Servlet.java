package com.whm.web.impl;

import com.whm.web.implsupport.S1010Support;

public final class S1012Servlet extends S1010Support
{
	@Override
	public String execute() throws Exception 
	{
		String next = this.getVal("next");
		this.update("modifySsa203", next.equals("u")?"É¾³ý":next);
		this.updateForQuery();
		return "S1010";
	}
}
