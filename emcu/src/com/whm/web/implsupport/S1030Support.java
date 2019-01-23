package com.whm.web.implsupport;

import com.whm.services.impl.S1030ServicesImpl;
import com.whm.web.support.BaseAbstractController;

public abstract class S1030Support extends BaseAbstractController {

	@Override
	public void createServices() 
	{
		this.setServices(new S1030ServicesImpl());
	}

}
