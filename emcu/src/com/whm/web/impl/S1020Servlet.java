package com.whm.web.impl;

import com.whm.web.implsupport.S1020Support;

public class S1020Servlet extends S1020Support {

	@Override
	public String execute() throws Exception 
	{
		String path=this.getVal("path").toString();
		String toPath = (path!=null && !path.equals(""))?path:"S1020";
		return toPath;
	}

}
