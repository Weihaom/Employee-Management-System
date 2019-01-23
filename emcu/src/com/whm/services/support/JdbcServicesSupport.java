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
	 *                 ����ΪDTO��������
	 *************************************************************************/
	/**
	 * �����ȡDTO��һ����
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
		//��DTO��ȡҳ��chckbox���ύֵ
		Object tem=this.dto.get(key);
		//�ж�tem�ľ�����������
		if(tem instanceof java.lang.String[])
		{
			return (String[])tem;
		}
		else
		{
			//������������
			return new String[]{tem.toString()};
		}	
	}
	
	/*************************************************************************
	 *                 ��������
	 *************************************************************************/
	 
	 /**
	  * �ǿ�У��
	  * @param value
	  * @return
	  */
	  protected final boolean isNotNull(Object value)
	  {
		 return value!=null && !value.equals("");
	  }

	  
	
	/*************************************************************************
	 *                 ���ݲ�ѯ����
	 *************************************************************************/
	
	
	/**
	 * ����������ѯ
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 */
    protected final List<Map<String,String>> queryForList(final String  sql,final Object...args)throws Exception
    {
    	//1.����JDBC�ӿڱ���
    	PreparedStatement pstm=null;
    	ResultSet rs=null;
    	try
    	{
    		//2.����SQL���
    		pstm=DBUtils.prepareStatement(sql);
    		//3.������ֵ
    		int index=1;
    		for(Object param:args)
    		{
    			pstm.setObject(index++, param);
    		}
    		//4.ִ��SQL
    		rs=pstm.executeQuery();
    		//5.��ȡ�������������
    		ResultSetMetaData rsmd=rs.getMetaData();
    		//6.��������
    		int count=rsmd.getColumnCount();
    		//7.���㰲ȫ�ĳ�ʼ����
    		int initSize=((int)(count/0.75))+1;
    		
    		//8.��������
    		List<Map<String,String>> rows=new ArrayList<>();   //װ����������
    		Map<String,String> ins=null;                       //װ�ص�ǰ������
    		
    		//9.ѭ������rs
    		while(rs.next())
    		{
    			//10.ʵ����HashMap
    			ins=new HashMap<>(initSize);
    			//11.ѭ����ǰ��������
    			for(int i=1;i<=count;i++)
    			{
    				//12.����м�ӳ��
    				ins.put(rsmd.getColumnLabel(i).toLowerCase(), rs.getString(i));
    			}
    			//13.����ǰ�����ݷ���List����
    			rows.add(ins);
    		}
            //14.���ز�ѯ���
    		return rows;
    	}
    	finally
    	{
    		DBUtils.close(rs);
    		DBUtils.close(pstm);
    	}
    }

	
	/**
	 * ��һʵ����ѯ����
	 * <
	 *    ��ѯһ������
	 * >
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 */
    protected final Map<String,String> queryForMap(final String sql,final Object...args)throws Exception
	{
		//1.����JDBC�ӿڱ���
		PreparedStatement pstm=null;
		ResultSet rs=null;
		try
		{
			//2.����SQL���
			pstm=DBUtils.prepareStatement(sql);
			//3.������ֵ
			int index=1;
			for(Object param:args)
			{
				pstm.setObject(index++, param);
			}
			//4.ִ�в�ѯ
			rs=pstm.executeQuery();
			
			//5.�������,װ�ص�ǰ������
			Map<String,String> ins=null;
			//6.�ж��Ƿ���ڲ�ѯ���
			if(rs.next())
			{
				//6.��ȡ�������������
				ResultSetMetaData rsmd=rs.getMetaData();
				//7.��������
				int count=rsmd.getColumnCount();
				//8.���㰲ȫ�ĳ�ʼ����
				int initSize=((int)(count/0.75))+1;
				//9.ʵ����HashMap
				ins=new HashMap<>(initSize);
				//10.ѭ����ǰ�е�������
				for(int i=1;i<=count;i++)
				{
					//11.����м�ӳ��
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
     * ����һ��һ������
     * 
     *   select max(sal) from emp where deptno=13
     * @return
     * @throws Exception
     */
    protected final Object queryForData(final String sql,final Object...args)throws Exception
    {
    	 //1.����JDBC�ӿڱ���
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
	 *                     ����Ϊ��������
	 ************************************************************************/
    //��ǰ���ڲ����������·����У�������������б�
    //��˵������������sql-->pstm-->pstmMetaData(pmd) ��һ�������洢����
    private List<PstmMetaData> pstmList = new ArrayList<>();
    
    //���񡢷�������
    //����������
    
    //���񣬷�����������
    protected void appendSql(final String sql,final Object...args)throws Exception
    {
    	PreparedStatement pstm = DBUtils.prepareStatement(sql);
    	//������ֵ
    	int index = 1;
    	for(Object param:args)
    	{
    		pstm.setObject(index++, param);
    	}
    	//��������������󣬽����������
    	PstmMetaData pmd = new PstmMetaData(pstm);
    	this.pstmList.add(pmd);
    }
    
    /**
     * 	����Ϊ��������������
     */
    //update set c1=?,c2=?,c3=? where id1=? and id2=? ����
    protected void appendBatchSql(final String sql,final Object[] stateList,final Object...idlist)throws Exception
    {
    	PreparedStatement pstm = DBUtils.prepareStatement(sql);
    	//�̶�������ֵ
    	int index = 1;
    	for(Object state:stateList)
    	{
    		pstm.setObject(index++, state);
    	}
    	//������ֵ������ӵ�������
    	for(Object id:idlist)
    	{
    		pstm.setObject(index, id);
    		pstm.addBatch();
    	}
    	
    	//��������������󣬷�������
    	PstmMetaData pmd = new PstmMetaData(pstm,true);
    	this.pstmList.add(pmd);
    }
    
    //insert into table(c1,c2,c3) values(?,?,?) ����
    //delete from table where id=? ����
    protected void appendBatchSql(final String sql,final Object...idlist)throws Exception
    {
    	this.appendBatchSql(sql,new Object[] {}, idlist);
    }
    
    //update table set c=? where id=? ����
    protected void appendBatchSql(final String sql,final Object newState,Object...idlist)throws Exception
    {
    	this.appendBatchSql(sql, new Object[] {newState}, idlist);
    }
    
    /**
     * 	ִ��������
     */
    protected boolean executeTransaction()throws Exception
    {
    	boolean tag = false;
    	DBUtils.beginTransaction();
    	try
    	{
    		//ѭ��������������б�
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
     * 	����������б�
     */
    private void close()
    {
    	for(PstmMetaData pmd:this.pstmList)
    	{
    		pmd.close(); //�ر�������
    	}
    	this.pstmList.clear(); //�������б�
    }
    
    
	/*************************************************************************
	 *                 ����Ϊ��һ����´���
	 *************************************************************************/
    
    
    /**
     *  ��һ���������������
     *  <
     *    ��һ��,��һ״̬�������޸�
     *    �ʺϵ�SQL����
     *    update table 
     *       set col=?
     *     where id=?  
     *     
     *     ��������������,ֻ����һ��
     *  >
     * @param sql         ---  sql���
     * @param newState    ---  Ŀ��״̬
     * @param args        ---  �������� 
     * @return
     * @throws Exception
     */
    protected final boolean batchTransaction(final String sql,final Object newState,final Object...args)throws Exception
    {
    	return this.batchTransaction(sql, new Object[]{newState}, args);
    }
    
	/**
	 * ��һ���������������
	 * <
	 *   ��һ��������ɾ��
	 *   �ʺϵ�SQL���
	 *   delete from table where id=?
	 * >
	 * @param sql   --- ��Ҫִ�е�SQL���
	 * @param args  --- ҳ��checkbox����(��������)
	 * @return
	 * @throws Exception
	 */
    protected final boolean batchTransaction(final String sql,final Object...args)throws Exception
    {
    	return this.batchTransaction(sql, new Object[]{}, args);
    }

    /**
     * ��һ���������������
     * <
     *   ��һ��,��״̬����
     *   �ʺ�SQL�������:
     *   update table set col1=?,col2=?,col3=?......coln=? where id=?
     *   
     *   ��������������,����̬ͬ����
     * >
     * @param sql            ---- SQL���
     * @param newStateList   ---- ״̬����
     * @param args           ---- �������� 
     * @return
     * @throws Exception
     */
    protected final boolean batchTransaction(final String sql,final Object[] newStateList,final Object...args)throws Exception
    {
    	//1.����JDBC�ӿڱ���
    	PreparedStatement pstm=null;
    	try
    	{
    		//2.����SQL���
    		pstm=DBUtils.prepareStatement(sql);
    		//3.״̬�б�ֵ
    		int index=1;
    		for(Object newState:newStateList)
    		{
    			pstm.setObject(index++, newState);
    		}
    		//4.ѭ����������
    		for(Object id:args)
    		{
    			//5.������ֵ
    			pstm.setObject(index, id);
    			//6.�򻺳���д��׼���õ�SQL���
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
     * ��һ������������ִ�� 
     * @param pstm
     * @return
     * @throws Exception
     */
    private boolean executeBatchTransaction(PreparedStatement pstm)throws Exception
    {
    	//6.�������񷵻�ֵ
		boolean tag=false;
		//7.��������
		DBUtils.beginTransaction();
		try
		{
			//8.������Χ��,ִ��������SQL����
			pstm.executeBatch();
			//9.�ύ����
			DBUtils.commit();
			//10.�޸����񷵻�ֵ
			tag=true;
		}
		catch(Exception ex)
		{
			//11.����ع�
			DBUtils.rollback();
			ex.printStackTrace();
		}
		finally
		{
			//12.��������
			DBUtils.endTransaction();
		}
		return tag;
    }

	
	/**
	 * ������ʽ,��һ�����
	 * @param sql   --- ������Ҫִ�еĸ������
	 * @param args  --- sql�еĲ����б�
	 * @return
	 * @throws Exception
	 */
    protected final int executeUpdate(final String sql,final Object...args)throws Exception
	{
		//1.����JDBC�ӿڱ���
		PreparedStatement pstm=null;
		try
		{
			//2.����SQL���
			pstm=DBUtils.prepareStatement(sql);
			//3.������ֵ
			int index=1;
			for(Object param:args)
			{
				pstm.setObject(index++, param);
			}
			//4.ִ�и��²���
			return pstm.executeUpdate();
		}
		finally
		{
			DBUtils.close(pstm);
		}
	}
}
