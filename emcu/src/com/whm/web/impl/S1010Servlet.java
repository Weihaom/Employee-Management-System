package com.whm.web.impl;

import com.whm.web.implsupport.S1010Support;

/**
 * ��ɫ���
 */
public final class S1010Servlet extends S1010Support 
{
	@Override
	public String execute() throws Exception 
	{
		this.update("add", "���");
		return "S1011";
	}

}
