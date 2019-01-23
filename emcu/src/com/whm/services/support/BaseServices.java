/**
 * 后继所有Servics的共同接口
 *   后继所有services中,用到方法,仅限于该接口中方法的定义
 */
package com.whm.services.support;

import java.util.*;

public interface BaseServices 
{
	
	/**
	 * 为子类Services ,传递DTO
	 * @param dto
	 */
    void initDto(Map<String,Object> dto);
	
	/**
	 * 单一实例查询
	 * @return
	 * @throws Exception
	 */
    default Map<String,String> findById()throws Exception
    {
    	return null;
    }
    
    /**
     * 数据分页查询
     * @return
     * @throws Exception
     */
    default List<Map<String,String>> queryForPage()throws Exception
    {
    	return null;
    }
    
    default Object queryForObject()throws Exception
    {
    	return null;
    }
    
    
    /**
     * Services中所有更新方法的总调度方法
     * @param updateType
     * @return
     * @throws Exception
     */
    default  boolean update(String updateType)throws Exception
    {
    	return false;
    }
    
}
