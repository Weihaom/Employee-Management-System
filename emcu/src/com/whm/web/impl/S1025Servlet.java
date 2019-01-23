package com.whm.web.impl;

import com.whm.web.implsupport.S1020Support;

//ÅúÁ¿É¾³ý
public class S1025Servlet extends S1020Support {

	@Override
	public String execute() throws Exception 
	{
		this.update("del", "É¾³ý");
		this.updateForQuery();
		return "S1020";
	}

}
