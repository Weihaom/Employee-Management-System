package com.whm.web.support;

import java.util.Map;

public interface BaseController
{
	/**
	 * 创建Services
	 * 业务控制器子类,通过该方法,实例化需要的Services对象
	 */
	void createServices();
	
	/**
	 * 初始化Services
	 * 业务控制器子类,为services传递DTO
	 */
	void initServices();
	
	
	/**
	 * 织入DTO   ---  数据的输入
	 * @param dto
	 */
	void  setDto(Map<String,Object> dto);
	
	/**
	 * 执行流程控制  --- 调用Services干活
	 * @return
	 * @throws Exception
	 */
	String execute()throws Exception;
	
	/**
	 * 获取属性  --- 数据输出
	 * @return
	 */
	Map<String,Object> getAttributes();
	
	/**
	 * 初始化控制器
	 */
	default void initController()throws Exception
	{
		
	}
	default void initRole()throws Exception
	{
		
	}
}
