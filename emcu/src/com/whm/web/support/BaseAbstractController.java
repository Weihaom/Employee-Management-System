package com.whm.web.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whm.services.support.BaseServices;

public abstract class BaseAbstractController implements BaseController 
{
	/**************************************************************
	 *             ����Services
	 **************************************************************/
    private BaseServices services=null;
    
    /**
     * ������ø÷���,���Servicesʵ����Ĺҽ�
     * @param services
     */
	protected final void setServices(BaseServices services)
	{
		this.services=services;
	}
//	/**
//	 * �����ȡServices
//	 * @return
//	 */
//	protected final BaseServices getServices()
//	{
//		return this.services;
//	}
    
     @Override
    public final void initServices() 
    {
    	 this.services.initDto(this.dto);
    }

 	/**************************************************************
 	 *             ҵ�����̵��ȷ���
 	 **************************************************************/
    /**
     * ���ݷ�ҳ��ѯ
     * @throws Exception
     */
     protected final void savePageData()throws Exception
     {
    	 List<Map<String,String>> rows=this.services.queryForPage();
    	 if(rows.size()>0)
    	 {
    		 //��ҳ�淴������
    		 this.addAttribute("rows", rows);
    	 }
    	 else
    	 {
    		 this.addAttribute("msg", "û�з�������������!");
    	 } 	 
     }

     /**
      * ɾ�����������
      * @throws Exception
      */
     protected final void updateForQuery()throws Exception
     {
    	 List<Map<String,String>> rows=this.services.queryForPage();
    	 if(rows.size()>0)
    	 {
    		 //��ҳ�淴������
    		 this.addAttribute("rows", rows);
    	 }
     }
     
     /**
            * ��һʵ����ѯ����
      * @throws Exception
      */
     protected final void saveInstances()throws Exception
     {
    	 Map<String,String> ins=this.services.findById();
 		 if(ins!=null)
 		 {
 	 		this.addAttribute("ins", ins);
 		 }
 		 else
 		 {
 	 		this.addAttribute("msg", "��������ɾ�����ֹ����!");
 		 }	
     }
     
     /**
      * ��һ�е�һ�����ݴ洢
      * @param dataName
      * @throws Exception
      */
     protected final void saveData(final String dataName)throws Exception
     {
    	 this.addAttribute(dataName, this.services.queryForObject());
     }
     
     
     /**
      * ͨ�ø��·���
      * @param updateType   --- �������
      * @param msgFirst     --- ��Ϣǰ׺
      * @throws Exception
      */
     protected final void update(String updateType,String msgFirst)throws Exception
     {
    	 String msg=this.services.update(updateType)?"�ɹ�!":"ʧ��!";
    	 this.addAttribute("msg", msgFirst+msg);
     }
     
     
     
	/**************************************************************
	 *             �������봦��
	 **************************************************************/
	
    private Map<String,Object> dto=null;
	@Override
	public final void setDto(Map<String, Object> dto) 
	{
	    this.dto=dto;
	}
	
	protected final Map<String,Object> getDto()
	{
		return this.dto;
	}

	protected final String getVal(String key)
	{
		if(this.dto.containsKey(key))
		{
			return this.dto.get(key).toString();	
		}
		else
		{
			return "";
		}	
		
	}
	
	protected final Object getVal(Object key)
	{
		return this.dto.get(key);
	}
	
	protected final void showDto()
	{
		System.out.println(this.dto);
	}
	
	
	/**************************************************************
	 *                        �����������
	 **************************************************************/

	//���ԵĴ洢����
	private final Map<String,Object> attributes=new HashMap<>();
	
	@Override
	public final Map<String, Object> getAttributes() 
	{
	    return this.attributes;
	}
	
	protected final void addAttribute(String key,Object value)
	{
		this.attributes.put(key, value);
	}
	

}
