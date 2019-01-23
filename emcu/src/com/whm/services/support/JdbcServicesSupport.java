package com.whm.services.support;

import java.util.*;
import java.sql.*;
import com.whm.system.db.DBUtils;


public abstract class JdbcServicesSupport  implements BaseServices
{
    private Map<String,Object> dto=null;
	
    @Override
    public final void initDto(Map<String, Object> dto) 
    {
       this.dto=dto;	
    }
    

	/*************************************************************************
	 *                 以下为DTO辅助方法
	 *************************************************************************/
	/**
	 * 子类获取DTO单一数据
	 * @param key
	 * @return
	 */
	 protected final Object getVal(final String key)
	 {
		 return this.dto.get(key);
	 }
	 
	 protected final void addEntry(final String key,final Object value)
	 {
		 this.dto.put(key, value);
	 }
	 
	 protected final String[] getArray(String key)
	 {
		//从DTO获取页面chckbox的提交值
		Object tem=this.dto.get(key);
		//判断tem的具体数据类型
		if(tem instanceof java.lang.String[])
		{
			return (String[])tem;
		}
		else
		{
			//创建匿名数组
			return new String[]{tem.toString()};
		}	
	}
	
	/*************************************************************************
	 *                 辅助方法
	 *************************************************************************/
	 
	 /**
	  * 非空校验
	  * @param value
	  * @return
	  */
	  protected final boolean isNotNull(Object value)
	  {
		 return value!=null && !value.equals("");
	  }

	  
	
	/*************************************************************************
	 *                 数据查询操作
	 *************************************************************************/
	
	
	/**
	 * 数据批量查询
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 */
    protected final List<Map<String,String>> queryForList(final String  sql,final Object...args)throws Exception
    {
    	//1.定义JDBC接口变量
    	PreparedStatement pstm=null;
    	ResultSet rs=null;
    	try
    	{
    		//2.编译SQL语句
    		pstm=DBUtils.prepareStatement(sql);
    		//3.参数赋值
    		int index=1;
    		for(Object param:args)
    		{
    			pstm.setObject(index++, param);
    		}
    		//4.执行SQL
    		rs=pstm.executeQuery();
    		//5.获取结果集描述对象
    		ResultSetMetaData rsmd=rs.getMetaData();
    		//6.计算列数
    		int count=rsmd.getColumnCount();
    		//7.计算安全的初始容量
    		int initSize=((int)(count/0.75))+1;
    		
    		//8.定义容器
    		List<Map<String,String>> rows=new ArrayList<>();   //装载所有数据
    		Map<String,String> ins=null;                       //装载当前行数据
    		
    		//9.循环解析rs
    		while(rs.next())
    		{
    			//10.实例化HashMap
    			ins=new HashMap<>(initSize);
    			//11.循环当前行所有列
    			for(int i=1;i<=count;i++)
    			{
    				//12.完成列级映射
    				ins.put(rsmd.getColumnLabel(i).toLowerCase(), rs.getString(i));
    			}
    			//13.将当前行数据放入List容器
    			rows.add(ins);
    		}
            //14.返回查询结果
    		return rows;
    	}
    	finally
    	{
    		DBUtils.close(rs);
    		DBUtils.close(pstm);
    	}
    }

	
	/**
	 * 单一实例查询处理
	 * <
	 *    查询一条数据
	 * >
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 */
    protected final Map<String,String> queryForMap(final String sql,final Object...args)throws Exception
	{
		//1.定义JDBC接口变量
		PreparedStatement pstm=null;
		ResultSet rs=null;
		try
		{
			//2.编译SQL语句
			pstm=DBUtils.prepareStatement(sql);
			//3.参数赋值
			int index=1;
			for(Object param:args)
			{
				pstm.setObject(index++, param);
			}
			//4.执行查询
			rs=pstm.executeQuery();
			
			//5.定义变量,装载当前行数据
			Map<String,String> ins=null;
			//6.判断是否存在查询结果
			if(rs.next())
			{
				//6.获取结果集描述对象
				ResultSetMetaData rsmd=rs.getMetaData();
				//7.计算列数
				int count=rsmd.getColumnCount();
				//8.计算安全的初始容量
				int initSize=((int)(count/0.75))+1;
				//9.实例化HashMap
				ins=new HashMap<>(initSize);
				//10.循环当前行的所有列
				for(int i=1;i<=count;i++)
				{
					//11.完成列级映射
					ins.put(rsmd.getColumnLabel(i).toLowerCase(), rs.getString(i));
				}
			}
			return ins;
		}
		finally
		{
			DBUtils.close(rs);
			DBUtils.close(pstm);
		}
	}
    
    
    /**
     * 返回一行一列数据
     * 
     *   select max(sal) from emp where deptno=13
     * @return
     * @throws Exception
     */
    protected final Object queryForData(final String sql,final Object...args)throws Exception
    {
    	 //1.定义JDBC接口变量
    	 PreparedStatement pstm=null;
    	 ResultSet rs=null;
    	 try
    	 {
    		 pstm=DBUtils.prepareStatement(sql);
    		 int index=1;
    		 for(Object param:args)
    		 {
    			 pstm.setObject(index++, param);
    		 }
    		 rs=pstm.executeQuery();
    		 
    		 Object val=null;
    		 if(rs.next())
    		 {
    			 val=rs.getObject(1);
    		 }
    		 return val;
    	 }	 
    	 finally
    	 {
    		 DBUtils.close(rs);
    		 DBUtils.close(pstm);
    	 }
    }
	
	/************************************************************************
	 *                     以下为级联更新
	 ************************************************************************/
    //当前类内部多表关联更新方法中，所有语句对象的列表
    //简单说。。。将所有sql-->pstm-->pstmMetaData(pmd) 用一个容器存储起来
    private List<PstmMetaData> pstmList = new ArrayList<>();
    
    //事务、非批处理
    //事务、批处理
    
    //事务，非批处理类型
    protected void appendSql(final String sql,final Object...args)throws Exception
    {
    	PreparedStatement pstm = DBUtils.prepareStatement(sql);
    	//参数赋值
    	int index = 1;
    	for(Object param:args)
    	{
    		pstm.setObject(index++, param);
    	}
    	//生成语句描述对象，将其放入容器
    	PstmMetaData pmd = new PstmMetaData(pstm);
    	this.pstmList.add(pmd);
    }
    
    /**
     * 	以下为事务、批处理类型
     */
    //update set c1=?,c2=?,c3=? where id1=? and id2=? 类型
    protected void appendBatchSql(final String sql,final Object[] stateList,final Object...idlist)throws Exception
    {
    	PreparedStatement pstm = DBUtils.prepareStatement(sql);
    	//固定参数赋值
    	int index = 1;
    	for(Object state:stateList)
    	{
    		pstm.setObject(index++, state);
    	}
    	//主键赋值，并添加到缓冲区
    	for(Object id:idlist)
    	{
    		pstm.setObject(index, id);
    		pstm.addBatch();
    	}
    	
    	//生成语句描述对象，放入容器
    	PstmMetaData pmd = new PstmMetaData(pstm,true);
    	this.pstmList.add(pmd);
    }
    
    //insert into table(c1,c2,c3) values(?,?,?) 类型
    //delete from table where id=? 类型
    protected void appendBatchSql(final String sql,final Object...idlist)throws Exception
    {
    	this.appendBatchSql(sql,new Object[] {}, idlist);
    }
    
    //update table set c=? where id=? 类型
    protected void appendBatchSql(final String sql,final Object newState,Object...idlist)throws Exception
    {
    	this.appendBatchSql(sql, new Object[] {newState}, idlist);
    }
    
    /**
     * 	执行批处理
     */
    protected boolean executeTransaction()throws Exception
    {
    	boolean tag = false;
    	DBUtils.beginTransaction();
    	try
    	{
    		//循环语句描述对象列表
    		for(PstmMetaData pmd:this.pstmList)
    		{
    			pmd.executePstm();
    		}
    		DBUtils.commit();
    		tag = true;
    	}
    	catch(Exception e)
    	{
    		DBUtils.rollback();
    		e.printStackTrace();
    	}
    	finally
    	{
    		DBUtils.endTransaction();
    		this.close();
    	}
    	return tag;
    }
    
    
    /**
     * 	处理掉事务列表
     */
    private void close()
    {
    	for(PstmMetaData pmd:this.pstmList)
    	{
    		pmd.close(); //关闭语句对象
    	}
    	this.pstmList.clear(); //清空语句列表
    }
    
    
	/*************************************************************************
	 *                 以下为单一表更新处理
	 *************************************************************************/
    
    
    /**
     *  单一表批处理事务更新
     *  <
     *    单一表,单一状态批处理修改
     *    适合的SQL如下
     *    update table 
     *       set col=?
     *     where id=?  
     *     
     *     按照主键批处理,只更新一列
     *  >
     * @param sql         ---  sql语句
     * @param newState    ---  目标状态
     * @param args        ---  主键数组 
     * @return
     * @throws Exception
     */
    protected final boolean batchTransaction(final String sql,final Object newState,final Object...args)throws Exception
    {
    	return this.batchTransaction(sql, new Object[]{newState}, args);
    }
    
	/**
	 * 单一表批处理事务更新
	 * <
	 *   单一表批处理删除
	 *   适合的SQL语句
	 *   delete from table where id=?
	 * >
	 * @param sql   --- 需要执行的SQL语句
	 * @param args  --- 页面checkbox数组(主键数组)
	 * @return
	 * @throws Exception
	 */
    protected final boolean batchTransaction(final String sql,final Object...args)throws Exception
    {
    	return this.batchTransaction(sql, new Object[]{}, args);
    }

    /**
     * 单一表批处理事务更新
     * <
     *   单一表,多状态更新
     *   适合SQL语句如下:
     *   update table set col1=?,col2=?,col3=?......coln=? where id=?
     *   
     *   按照主键批处理,多列同态更新
     * >
     * @param sql            ---- SQL语句
     * @param newStateList   ---- 状态数组
     * @param args           ---- 主键数组 
     * @return
     * @throws Exception
     */
    protected final boolean batchTransaction(final String sql,final Object[] newStateList,final Object...args)throws Exception
    {
    	//1.定义JDBC接口变量
    	PreparedStatement pstm=null;
    	try
    	{
    		//2.编译SQL语句
    		pstm=DBUtils.prepareStatement(sql);
    		//3.状态列表赋值
    		int index=1;
    		for(Object newState:newStateList)
    		{
    			pstm.setObject(index++, newState);
    		}
    		//4.循环主键数组
    		for(Object id:args)
    		{
    			//5.主键赋值
    			pstm.setObject(index, id);
    			//6.向缓冲区写入准备好的SQL语句
    			pstm.addBatch();
    		}
    		return this.executeBatchTransaction(pstm);
    	}
    	finally
    	{
    		DBUtils.close(pstm);
    	}
    }

    /**
     * 单一表批处理事务执行 
     * @param pstm
     * @return
     * @throws Exception
     */
    private boolean executeBatchTransaction(PreparedStatement pstm)throws Exception
    {
    	//6.定义事务返回值
		boolean tag=false;
		//7.开启事务
		DBUtils.beginTransaction();
		try
		{
			//8.在事务范围内,执行批处理SQL更新
			pstm.executeBatch();
			//9.提交事务
			DBUtils.commit();
			//10.修改事务返回值
			tag=true;
		}
		catch(Exception ex)
		{
			//11.事务回滚
			DBUtils.rollback();
			ex.printStackTrace();
		}
		finally
		{
			//12.结束事务
			DBUtils.endTransaction();
		}
		return tag;
    }

	
	/**
	 * 非事务方式,单一表更新
	 * @param sql   --- 方法需要执行的更新语句
	 * @param args  --- sql中的参数列表
	 * @return
	 * @throws Exception
	 */
    protected final int executeUpdate(final String sql,final Object...args)throws Exception
	{
		//1.定义JDBC接口变量
		PreparedStatement pstm=null;
		try
		{
			//2.编译SQL语句
			pstm=DBUtils.prepareStatement(sql);
			//3.参数赋值
			int index=1;
			for(Object param:args)
			{
				pstm.setObject(index++, param);
			}
			//4.执行更新操作
			return pstm.executeUpdate();
		}
		finally
		{
			DBUtils.close(pstm);
		}
	}
}
