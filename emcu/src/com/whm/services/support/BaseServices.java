/**
 * �������Servics�Ĺ�ͬ�ӿ�
 *   �������services��,�õ�����,�����ڸýӿ��з����Ķ���
 */
package com.whm.services.support;

import java.util.*;

public interface BaseServices 
{
	
	/**
	 * Ϊ����Services ,����DTO
	 * @param dto
	 */
    void initDto(Map<String,Object> dto);
	
	/**
	 * ��һʵ����ѯ
	 * @return
	 * @throws Exception
	 */
    default Map<String,String> findById()throws Exception
    {
    	return null;
    }
    
    /**
     * ���ݷ�ҳ��ѯ
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
     * Services�����и��·������ܵ��ȷ���
     * @param updateType
     * @return
     * @throws Exception
     */
    default  boolean update(String updateType)throws Exception
    {
    	return false;
    }
    
}
