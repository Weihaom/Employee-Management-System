package com.whm.web.support;

import java.util.Map;

public interface BaseController
{
	/**
	 * ����Services
	 * ҵ�����������,ͨ���÷���,ʵ������Ҫ��Services����
	 */
	void createServices();
	
	/**
	 * ��ʼ��Services
	 * ҵ�����������,Ϊservices����DTO
	 */
	void initServices();
	
	
	/**
	 * ֯��DTO   ---  ���ݵ�����
	 * @param dto
	 */
	void  setDto(Map<String,Object> dto);
	
	/**
	 * ִ�����̿���  --- ����Services�ɻ�
	 * @return
	 * @throws Exception
	 */
	String execute()throws Exception;
	
	/**
	 * ��ȡ����  --- �������
	 * @return
	 */
	Map<String,Object> getAttributes();
	
	/**
	 * ��ʼ��������
	 */
	default void initController()throws Exception
	{
		
	}
	default void initRole()throws Exception
	{
		
	}
}
