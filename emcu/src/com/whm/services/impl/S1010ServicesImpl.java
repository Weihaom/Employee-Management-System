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
			throw new Exception("����"+this.getClass().getName()+"�в���������Ϊ["+ updateType +"]�ĸ��²���!");
		}
	}
	
	private boolean modify()throws Exception
	{
		//1.����sql���
		StringBuilder sql=new StringBuilder()
				.append("update sa02 x")
				.append("   set x.ssa202=?,x.ssa204=?")
				.append(" where x.ssa201=?")
				;
		//2.��д��������
		Object args[] = {
				this.getVal("ssa202"),
				this.getVal("ssa204"),
				this.getVal("ssa201")
		};
		return this.executeUpdate(sql.toString(), args)>0;
	}
	
	private boolean modifySsa203()throws Exception
	{
		//1.����sql���
		String sql="update sa02 set ssa203=? where ssa201=?";
		//2.��ȡ��������
		String idlist[] = this.getArray("idlist");
		//3.ִ��
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
	
	
	
	/***************************��ѯ���*******************************/
	@Override
	public Map<String, String> findById() throws Exception 
	{
		//1.����sql���
		String sql="select x.ssa202,x.ssa204 from  sa02 x where ssa201=?";
		return this.queryForMap(sql, this.getVal("ssa201"));
	}
	
	@Override
	public List<Map<String, String>> queryForPage() throws Exception 
	{
		//1.��ԭҳ���ѯ����
		Object ssa202 = this.getVal("qssa202"); //��ɫ����
		Object ssa203 = this.getVal("qssa203"); //��ɫ״̬
		
		//2.����sql
		StringBuilder sql = new StringBuilder()
				.append("select x.ssa201,x.ssa202,a.fvalue cnssa203,x.ssa204") 
				.append("  from syscode a, sa02 x")
				.append(" where a.fcode=x.ssa203 and a.fname='SSA203'")
				.append("   and x.ssa203!=?")
				;
		
		//3.�����������
		List<Object> paramList = new ArrayList<>();
		paramList.add("3"); //3----����
		
		//4.ƴ������
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
				//5.ִ�в�ѯ
				return this.queryForList(sql.toString(), paramList.toArray());
	}

}
