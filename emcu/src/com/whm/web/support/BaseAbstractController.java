package com.whm.web.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whm.services.support.BaseServices;

public abstract class BaseAbstractController implements BaseController 
{
	/**************************************************************
	 *             处理Services
	 **************************************************************/
    private BaseServices services=null;
    
    /**
     * 子类调用该方法,完成Services实现类的挂接
     * @param services
     */
	protected final void setServices(BaseServices services)
	{
		this.services=services;
	}
//	/**
//	 * 子类获取Services
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
 	 *             业务流程调度方法
 	 **************************************************************/
    /**
     * 数据分页查询
     * @throws Exception
     */
     protected final void savePageData()throws Exception
     {
    	 List<Map<String,String>> rows=this.services.queryForPage();
    	 if(rows.size()>0)
    	 {
    		 //向页面反馈数据
    		 this.addAttribute("rows", rows);
    	 }
    	 else
    	 {
    		 this.addAttribute("msg", "没有符合条件的数据!");
    	 } 	 
     }

     /**
      * 删除后检索数据
      * @throws Exception
      */
     protected final void updateForQuery()throws Exception
     {
    	 List<Map<String,String>> rows=this.services.queryForPage();
    	 if(rows.size()>0)
    	 {
    		 //向页面反馈数据
    		 this.addAttribute("rows", rows);
    	 }
     }
     
     /**
            * 单一实例查询方法
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
 	 		this.addAttribute("msg", "该数据已删除或禁止访问!");
 		 }	
     }
     
     /**
      * 单一行单一列数据存储
      * @param dataName
      * @throws Exception
      */
     protected final void saveData(final String dataName)throws Exception
     {
    	 this.addAttribute(dataName, this.services.queryForObject());
     }
     
     
     /**
      * 通用更新方法
      * @param updateType   --- 更新类别
      * @param msgFirst     --- 消息前缀
      * @throws Exception
      */
     protected final void update(String updateType,String msgFirst)throws Exception
     {
    	 String msg=this.services.update(updateType)?"成功!":"失败!";
    	 this.addAttribute("msg", msgFirst+msg);
     }
     
     
     
	/**************************************************************
	 *             数据输入处理
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
	 *                        数据输出处理
	 **************************************************************/

	//属性的存储容器
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
