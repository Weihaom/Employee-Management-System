package com.whm.web.impl;

import com.whm.web.implsupport.S1020Support;

//����ɾ��
public class S1025Servlet extends S1020Support {

	@Override
	public String execute() throws Exception 
	{
		this.update("del", "ɾ��");
		this.updateForQuery();
		return "S1020";
	}

}
