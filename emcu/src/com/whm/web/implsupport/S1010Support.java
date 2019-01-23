package com.whm.web.implsupport;

import com.whm.services.impl.S1010ServicesImpl;
import com.whm.web.support.BaseAbstractController;

public abstract class S1010Support extends BaseAbstractController {

	@Override
	public void createServices() 
	{
		this.setServices(new S1010ServicesImpl());
	}

}
