package com.whm.web.impl;

import com.whm.web.implsupport.S1020Support;

//����������ѯ�û�
public class S1022Servlet extends S1020Support
{
	@Override
	public String execute() throws Exception 
	{
		this.savePageData();
		return "S1020";
	}
}
