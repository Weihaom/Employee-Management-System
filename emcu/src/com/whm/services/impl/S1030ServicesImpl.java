package com.whm.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.whm.services.support.JdbcServicesSupport;

public class S1030ServicesImpl extends JdbcServicesSupport 
{
	@Override
	public boolean update(String updateType) throws Exception 
	{
		if(updateType.equalsIgnoreCase("add"))
		{
			return this.addMenu();
		}
		else if(updateType.equalsIgnoreCase("modify"))
		{
			return this.modifyMenu();
		}
		else if(updateType.equalsIgnoreCase("deleteById"))
		{
			return this.deleteById();
		}
		else if(updateType.equalsIgnoreCase("batchDelete"))
		{
			return this.batchDelete();
		}
		else
		{
			throw new Exception("����["+this.getClass().getName()+"]��û������Ϊ["+updateType+"]�ĸ������");
		}
	}
	
	private boolean addMenu()throws Exception
	{
		//1.����SQL���
		String sql = "insert into sa04(ssa402,ssa403,ssa404,ssa405) values(?,?,?,?)";
		//2.��д��������
		Object args[] ={
				this.getVal("ssa402"),
				this.getVal("ssa403"),
				this.getVal("ssa404"),
				"1"
		};
		return this.executeUpdate(sql, args)>0;
	}
	
	private boolean batchDelete()throws Exception
	{
		//1.��ȡҳ��checkbox����
		String idlist[]=this.getArray("idlist");
		
		//2.ɾ���˵���Ȩ
		String sql1="delete from sa05 where ssa401=?";
		this.appendBatchSql(sql1, idlist);
		
		//3.ɾ���˵�
		String sql2="delete from sa04 where ssa401=?";
		this.appendBatchSql(sql2, idlist);
		
        return this.executeTransaction();				
	}
	
	private boolean deleteById()throws Exception
	{
		Object ssa401=this.getVal("ssa401");
		
		//1.ɾ���˵���Ȩ
		String sql1="delete from sa05 where ssa401=?";
		this.appendSql(sql1, ssa401);
		
		//2.ɾ���˵�
		String sql2="delete from sa04 where ssa401=?";
		this.appendSql(sql2, ssa401);
		
		return this.executeTransaction();
	}
	
	private boolean modifyMenu()throws Exception
	{
		String sql = "update sa04 set ssa402=?,ssa403=?,ssa404=? where ssa401=?";
		Object args[]={
				this.getVal("ssa402"),
				this.getVal("ssa403"),
				this.getVal("ssa404"),
				this.getVal("ssa401")
			};
			return this.executeUpdate(sql, args)>0;
	}
	
	
	/***********************************************
	 *               ����Ϊ��ѯ
	 **********************************************/
	
	@Override
	public List<Map<String, String>> queryForPage() throws Exception 
	{
		//1.��ԭҳ���ѯ����
		Object ssa402 = this.getVal("qssa402");
		Object ssa403 = this.getVal("qssa403");
		Object ssa405 = this.getVal("qssa405");
		
		//��дsql
		StringBuilder sql=new StringBuilder()
				.append("select x.ssa401,x.ssa402,x.ssa403,x.ssa404,a.fvalue cnssa405")
				.append("  from syscode a,sa04 x")
				.append(" where a.fcode=x.ssa405 and a.fname='SSA405'")
		;
		//3.����̬SQL
		List<Object> paramList=new ArrayList<>();
		if(this.isNotNull(ssa405))
		{
			sql.append(" and x.ssa405=?");
			paramList.add(ssa405);
		}
		if(this.isNotNull(ssa403))
		{
			sql.append(" and x.ssa403 like ?");
			paramList.add("%"+ssa403+"%");
		}
		if(this.isNotNull(ssa402))
		{
			sql.append(" and x.ssa402 like ?");
			paramList.add("%"+ssa402+"%");
		}
		sql.append(" order by x.ssa402");
		return this.queryForList(sql.toString(), paramList.toArray());
	}
	
	@Override
	public Map<String, String> findById() throws Exception 
	{
		StringBuilder sql=new StringBuilder()
				.append("select x.ssa402,x.ssa403,x.ssa404")
				.append("  from sa04 x")
				.append(" where x.ssa401=?")
		;
		return this.queryForMap(sql.toString(), this.getVal("ssa401"));
	}
}






















