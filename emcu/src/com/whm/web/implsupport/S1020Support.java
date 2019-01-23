package com.whm.web.implsupport;

import com.whm.services.impl.S1020ServicesImpl;
import com.whm.system.tools.Tools;
import com.whm.web.support.BaseAbstractController;

public abstract class S1020Support extends BaseAbstractController
{
	@Override
	public void createServices() 
	{
		this.setServices(new S1020ServicesImpl());
	}
	
	/**
	 * 为页面创建加载下拉列表
	 */
	@Override
	public void initController() throws Exception 
	{
		this.addAttribute("ocssa105", Tools.getOptions("SSA105"));
		this.addAttribute("ocssa201", Tools.getRoles());
	}
}
