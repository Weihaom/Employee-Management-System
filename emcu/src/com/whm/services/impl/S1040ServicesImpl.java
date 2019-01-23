package com.whm.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.whm.services.support.JdbcServicesSupport;

public class S1040ServicesImpl extends JdbcServicesSupport
{
	@Override
	public boolean update(String updateType) throws Exception 
	{
		return this.grant();
	}
	
	
	private boolean  grant()throws Exception
	{
		//1.获取页面数据
		Object ssa201=this.getVal("qssa201");
		String idlist[]=this.getArray("idlist");
		
		//2.删除该角色的授权
		String sql1="delete from sa05 where ssa201=?";
		this.appendSql(sql1, ssa201);
		
		//2.添加新授权
		String  sql2="insert into sa05(ssa201,ssa502,ssa401) values(?,?,?)";
		Object stateList[]={ssa201,"1"};
		this.appendBatchSql(sql2, stateList, idlist);
		
		return this.executeTransaction();
		
	}
	
	
	@Override
	public List<Map<String, String>> queryForPage() throws Exception 
	{
		//1.还原查询条件
		Object ssa402=this.getVal("qssa402");   //菜单编号
		Object ssa403=this.getVal("qssa403");   //菜单名称
		
		//2.拼写SQL主体
		StringBuilder sql=new StringBuilder()
				.append("select x.ssa401,x.ssa402,x.ssa403,x.ssa404,a.fvalue cnssa405")
				.append("  from syscode a,sa04 x")
				.append(" where a.fcode=x.ssa405 and a.fname='SSA405'")
		;
		//3.处理动态SQL
		List<Object> paramList=new ArrayList<>();
		
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
	public String[] queryForObject() throws Exception 
	{
		StringBuilder sql=new StringBuilder()
				.append("select group_concat(x.ssa401 SEPARATOR ',') menuList") 
				.append("  from sa05 x")
				.append(" where x.ssa201=?")		
		;
		Object val=this.queryForData(sql.toString(), this.getVal("qssa201"));
		
		String arr[]=(val!=null)?val.toString().split("\\,"):new String[]{};		

		return arr;
	}
}
