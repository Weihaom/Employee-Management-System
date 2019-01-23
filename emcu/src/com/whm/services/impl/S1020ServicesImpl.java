package com.whm.services.impl;

import static com.whm.system.tools.Tools.INIT_PWD;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.whm.services.support.JdbcServicesSupport;
import com.whm.system.db.DBUtils;
import com.whm.system.tools.Tools;

public class S1020ServicesImpl extends JdbcServicesSupport 
{
	@Override
	public boolean update(String updateType) throws Exception 
	{
		if(updateType.equalsIgnoreCase("add"))
		{
			return this.addUser2();
		}
		else if(updateType.equalsIgnoreCase("modify"))
		{
			return this.modify();
		}
		else if(updateType.equalsIgnoreCase("del"))
		{
			return this.batchDelete();
		}
		else
		{
			throw new Exception("在类"+this.getClass().getName()+" 中没有名称为"+"["+updateType+"]的更新类别！");
		}
	}
	
	/**
	 * 批量删除
	 * 由于用户已经和角色绑定，删除时需执行以下步骤
	 * 1.删除sa03中用户和橘色的绑定
	 * 2.删除sa01中的用户
	 */
	private boolean batchDelete()throws Exception
	{
		
		//获取checkbox数组
		String idlist[] = this.getArray("idlist");

		String sql1 = "delete from sa03 where ssa101=?";
		this.appendBatchSql(sql1, idlist);

		String sql2 = "delete from sa01 where ssa101=?";
		this.appendBatchSql(sql2, idlist);
		
		return this.executeTransaction();
			
	}
	
	/**
	 *修改用户信息
	 *在该修改界面仅能修改姓名和其角色，对于角色的修改应分为以下步骤
	 *1.修改用户姓名
	 *2.删除用户原角色
	 *3.绑定用户现角色
	 **/
	private boolean modify()throws Exception
	{
		//获取用户主键
		Object ssa101 = this.getVal("ssa101");

		//修改用户姓名
		StringBuilder sql1 = new StringBuilder()
				.append("update sa01 a")
				.append("   set a.ssa102=?")
				.append(" where a.ssa101=?")
				;
		Object args1[] = {this.getVal("ssa102"),ssa101};	
		this.appendSql(sql1.toString(), args1);	

		//删除用户原角色
		String sql2 = "delete from sa03 where ssa101=?";
		this.appendSql(sql2, ssa101);


		//绑定用户现角色
		StringBuilder sql3 = new StringBuilder()
				.append("insert into sa03(ssa101,ssa302,ssa201)")
				.append("       values(?,?,?)")
				;
		Object stateList[] = {ssa101,"1"};
		this.appendBatchSql(sql3.toString(), stateList, this.getArray("roleList"));

		//执行事务
		return this.executeTransaction();
	}
	
	
	
	
	/**
	 * 从S1020点击，进入用户修改界面，通过此方法还原用户数据
	 * 
	 */
	@Override
	public Map<String, String> findById() throws Exception 
	{
		StringBuilder sql = new StringBuilder()
				.append("select z.ssa102,group_concat(x.ssa201 separator ',') rolelist")
				.append("  from sa03 x,sa01 z")
				.append(" where x.ssa101=z.ssa101")
				.append("   and z.ssa101=?")
				.append(" group by z.ssa102")
				;
		
		return this.queryForMap(sql.toString(), this.getVal("ssa101"));
	}
	
	/**
	 * 依据页面条件查询用户信息
	 * 显示信息：序号、姓名ssa102、登录名ssa103、用户状态ssa105、 用户角色ssa201(sa02) 
	 * 页面的查询条件：姓名ssa102、登录名ssa103、角色ssa201、状态ssa105
	 */
	@Override
	public java.util.List<Map<String, String>> queryForPage() throws Exception 
	{
		//获取页面数据
		Object ssa102 = this.getVal("qssa102");
		Object ssa103 = this.getVal("qssa103");
		Object ssa105 = this.getVal("qssa105");
		Object ssa201 = this.getVal("qssa201");
		//定义容器用于参数赋值
		List<Object> paramList = new ArrayList<>(); 


		StringBuilder sql = new StringBuilder();
		sql
		.append("select z.ssa101,z.ssa102,z.ssa103,a.fvalue cnssa105,GROUP_CONCAT(y.ssa202 separator ',') rolelist")
		.append("  from syscode a,sa03 x,sa02 y,sa01 z")
		.append(" where x.ssa101=z.ssa101")
		.append("   and x.ssa201=y.ssa201")
		.append("	and a.fcode=z.ssa105")
		.append("	and a.fname='ssa105'");

		//拼接从页面获得的查询条件
		if(this.isNotNull(ssa105)) //状态
		{
			sql.append("   and z.ssa105=?");
			paramList.add(ssa105);
		}
		if(this.isNotNull(ssa201)) //角色
		{
			sql.append("   and y.ssa201=?");
			paramList.add(ssa201);
		}

		if(this.isNotNull(ssa102)) //姓名
		{
			sql.append("   and z.ssa102 like ?");
			paramList.add("%"+ssa102+"%");
		}
		if(this.isNotNull(ssa103)) //登录名
		{
			sql.append("   and z.ssa103=?");
			paramList.add(ssa103);
		}

		sql
		.append(" group by z.ssa101,z.ssa102,z.ssa103,z.ssa105")
		.append(" order by z.ssa103")
		;

		return this.queryForList(sql.toString(), paramList.toArray());
	}
	
	/**
	 * 添加用户完整方法
	 *1.将用户名、登录名、密码、用户状态放入sa01
	 *2.加载sa02中的用户角色
	 *3.将用户和其相应角色放入sa03 
	 *
	 *用户名：手动输入
	 *登录名：自动生成 
	 *密码：   自动 
	 *用户状态：自动 
	 *
	 *用户角色：手动生成 
	 **/
	private boolean addUser2()throws Exception
	{
		//获取主键序列值 
		int ssa101 = Tools.getSequence("SSA101");
		//获取登录名，并将登录名写入dto
		String loginName = Tools.getLoginName("E");
		this.addEntry("loginName", loginName);

		//定义sql1语句
		StringBuilder sql1 = new StringBuilder()
				.append("insert into sa01(ssa101,ssa102,ssa103,ssa104,ssa105)")
				.append("       values(?,?,?,?,?)")
				;

		Object[] args1 = {ssa101,this.getVal("ssa102"),loginName,Tools.INIT_PWD,"01"};

		this.appendSql(sql1.toString(), args1);
		//完成用户授权
		String sql2="insert into sa03(ssa101,ssa302,ssa201) values(?,?,?)";
		Object stateList[]={ssa101,"1"};
		this.appendBatchSql(sql2, stateList, this.getArray("roleList"));
		return this.executeTransaction();
	}
	
	
	//添加用户
	private boolean addUser()throws Exception
	{
		//1.定义JDBC接口变量
		PreparedStatement pstm1 = null;
		try
		{
			//2.定义sql
			StringBuilder sql1=new StringBuilder()
					.append("insert into sa01(ssa102,ssa103,ssa104,ssa105)")
					.append("   values(?,?,?,?)")
					;
			//3.编译sql
			pstm1 = DBUtils.prepareStatement(sql1.toString());
			
			//获取登录名
			String loginName = Tools.getLoginName("E");
			//将登录名写入dto
			this.addEntry("loginName", loginName);
			
			
			//4.参数赋值
			pstm1.setObject(1, this.getVal("ssa102"));
			pstm1.setObject(2, loginName);
			pstm1.setObject(3, INIT_PWD);
			pstm1.setObject(4, "01");
			
			return pstm1.executeUpdate()>0;
		}
		finally
		{
			DBUtils.close(pstm1);
		}
	}
}
