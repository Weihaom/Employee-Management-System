package com.whm.web.implsupport;

import com.whm.services.impl.S1040ServicesImpl;
import com.whm.system.tools.Tools;
import com.whm.web.support.BaseAbstractController;

public abstract class S1040Support extends BaseAbstractController
{
	@Override
	public void createServices() 
	{
		this.setServices(new S1040ServicesImpl());
	}
	
	//��ʼ�����������б�
	@Override
	public void initController() throws Exception 
	{
		this.addAttribute("ocssa201", Tools.getRoles());
	}
}
