package com.whm.web.impl;

import com.whm.web.implsupport.S1020Support;

public class S1021Servlet extends S1020Support {

	@Override
	public String execute() throws Exception 
	{
		//1.������ӷ���
		this.update("add", "���");
		
		//2.��ȡ�����û��ĵ�¼����
		Object loginName = this.getVal("loginName");
		//��ҳ�淴����¼��
		String msg = "��ӳɹ�����ǰ�û��ĵ�¼���ǣ�"+loginName;
		this.addAttribute("msg", msg);
		
		return "S1021";
	}

}
