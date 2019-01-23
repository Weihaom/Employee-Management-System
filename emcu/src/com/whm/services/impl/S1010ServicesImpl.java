package com.whm.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.whm.services.support.JdbcServicesSupport;

public class S1010ServicesImpl extends JdbcServicesSupport
{
	@Override
	public boolean update(String updateType) throws Exception 
	{
		if(updateType.equalsIgnoreCase("add"))
		{
			return this.addRole();
		}
		else if(updateType.equalsIgnoreCase("modifySsa203"))
		{
			return this.modifySsa203();
		}
		else if(updateType.equalsIgnoreCase("modify"))
		{
			return this.modify();
		}
		else
		{
			throw new Exception("在类"+this.getClass().getName()+"中不存在名称为["+ updateType +"]的更新操作!");
		}
	}
	
	private boolean modify()throws Exception
	{
		//1.定义sql语句
		StringBuilder sql=new StringBuilder()
				.append("update sa02 x")
				.append("   set x.ssa202=?,x.ssa204=?")
				.append(" where x.ssa201=?")
				;
		//2.编写参数数组
		Object args[] = {
				this.getVal("ssa202"),
				this.getVal("ssa204"),
				this.getVal("ssa201")
		};
		return this.executeUpdate(sql.toString(), args)>0;
	}
	
	private boolean modifySsa203()throws Exception
	{
		//1.定义sql语句
		String sql="update sa02 set ssa203=? where ssa201=?";
		//2.获取主键数组
		String idlist[] = this.getArray("idlist");
		//3.执行
		return this.batchTransaction(sql, this.getVal("ssa203"), idlist);
	}
	
	private boolean addRole()throws Exception
	{
		StringBuilder sql=new StringBuilder()
				.append("insert into sa02(ssa202,ssa203,ssa204)")
				.append("          values(?,?,?)")
				;
		Object args[] = {
				this.getVal("ssa202"),
				"1",
				this.getVal("ssa204")
		};
		
		return this.executeUpdate(sql.toString(), args)>0;
	}
	
	
	
	/***************************查询相关*******************************/
	@Override
	public Map<String, String> findById() throws Exception 
	{
		//1.定义sql语句
		String sql="select x.ssa202,x.ssa204 from  sa02 x where ssa201=?";
		return this.queryForMap(sql, this.getVal("ssa201"));
	}
	
	@Override
	public List<Map<String, String>> queryForPage() throws Exception 
	{
		//1.还原页面查询条件
		Object ssa202 = this.getVal("qssa202"); //角色名称
		Object ssa203 = this.getVal("qssa203"); //角色状态
		
		//2.定义sql
		StringBuilder sql = new StringBuilder()
				.append("select x.ssa201,x.ssa202,a.fvalue cnssa203,x.ssa204") 
				.append("  from syscode a, sa02 x")
				.append(" where a.fcode=x.ssa203 and a.fname='SSA203'")
				.append("   and x.ssa203!=?")
				;
		
		//3.定义参数数组
		List<Object> paramList = new ArrayList<>();
		paramList.add("3"); //3----作废
		
		//4.拼接条件
				if(this.isNotNull(ssa203))
				{
					sql.append(" and x.ssa203 = ?");
					paramList.add(ssa203);
				}
				if(this.isNotNull(ssa202))
				{
					sql.append(" and x.ssa202 like ?");
					paramList.add("%"+ssa202+"%");
				}
				//5.执行查询
				return this.queryForList(sql.toString(), paramList.toArray());
	}

}
