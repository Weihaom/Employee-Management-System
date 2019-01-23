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
			throw new Exception("����"+this.getClass().getName()+" ��û������Ϊ"+"["+updateType+"]�ĸ������");
		}
	}
	
	/**
	 * ����ɾ��
	 * �����û��Ѿ��ͽ�ɫ�󶨣�ɾ��ʱ��ִ�����²���
	 * 1.ɾ��sa03���û�����ɫ�İ�
	 * 2.ɾ��sa01�е��û�
	 */
	private boolean batchDelete()throws Exception
	{
		
		//��ȡcheckbox����
		String idlist[] = this.getArray("idlist");

		String sql1 = "delete from sa03 where ssa101=?";
		this.appendBatchSql(sql1, idlist);

		String sql2 = "delete from sa01 where ssa101=?";
		this.appendBatchSql(sql2, idlist);
		
		return this.executeTransaction();
			
	}
	
	/**
	 *�޸��û���Ϣ
	 *�ڸ��޸Ľ�������޸����������ɫ�����ڽ�ɫ���޸�Ӧ��Ϊ���²���
	 *1.�޸��û�����
	 *2.ɾ���û�ԭ��ɫ
	 *3.���û��ֽ�ɫ
	 **/
	private boolean modify()throws Exception
	{
		//��ȡ�û�����
		Object ssa101 = this.getVal("ssa101");

		//�޸��û�����
		StringBuilder sql1 = new StringBuilder()
				.append("update sa01 a")
				.append("   set a.ssa102=?")
				.append(" where a.ssa101=?")
				;
		Object args1[] = {this.getVal("ssa102"),ssa101};	
		this.appendSql(sql1.toString(), args1);	

		//ɾ���û�ԭ��ɫ
		String sql2 = "delete from sa03 where ssa101=?";
		this.appendSql(sql2, ssa101);


		//���û��ֽ�ɫ
		StringBuilder sql3 = new StringBuilder()
				.append("insert into sa03(ssa101,ssa302,ssa201)")
				.append("       values(?,?,?)")
				;
		Object stateList[] = {ssa101,"1"};
		this.appendBatchSql(sql3.toString(), stateList, this.getArray("roleList"));

		//ִ������
		return this.executeTransaction();
	}
	
	
	
	
	/**
	 * ��S1020����������û��޸Ľ��棬ͨ���˷�����ԭ�û�����
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
	 * ����ҳ��������ѯ�û���Ϣ
	 * ��ʾ��Ϣ����š�����ssa102����¼��ssa103���û�״̬ssa105�� �û���ɫssa201(sa02) 
	 * ҳ��Ĳ�ѯ����������ssa102����¼��ssa103����ɫssa201��״̬ssa105
	 */
	@Override
	public java.util.List<Map<String, String>> queryForPage() throws Exception 
	{
		//��ȡҳ������
		Object ssa102 = this.getVal("qssa102");
		Object ssa103 = this.getVal("qssa103");
		Object ssa105 = this.getVal("qssa105");
		Object ssa201 = this.getVal("qssa201");
		//�����������ڲ�����ֵ
		List<Object> paramList = new ArrayList<>(); 


		StringBuilder sql = new StringBuilder();
		sql
		.append("select z.ssa101,z.ssa102,z.ssa103,a.fvalue cnssa105,GROUP_CONCAT(y.ssa202 separator ',') rolelist")
		.append("  from syscode a,sa03 x,sa02 y,sa01 z")
		.append(" where x.ssa101=z.ssa101")
		.append("   and x.ssa201=y.ssa201")
		.append("	and a.fcode=z.ssa105")
		.append("	and a.fname='ssa105'");

		//ƴ�Ӵ�ҳ���õĲ�ѯ����
		if(this.isNotNull(ssa105)) //״̬
		{
			sql.append("   and z.ssa105=?");
			paramList.add(ssa105);
		}
		if(this.isNotNull(ssa201)) //��ɫ
		{
			sql.append("   and y.ssa201=?");
			paramList.add(ssa201);
		}

		if(this.isNotNull(ssa102)) //����
		{
			sql.append("   and z.ssa102 like ?");
			paramList.add("%"+ssa102+"%");
		}
		if(this.isNotNull(ssa103)) //��¼��
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
	 * ����û���������
	 *1.���û�������¼�������롢�û�״̬����sa01
	 *2.����sa02�е��û���ɫ
	 *3.���û�������Ӧ��ɫ����sa03 
	 *
	 *�û������ֶ�����
	 *��¼�����Զ����� 
	 *���룺   �Զ� 
	 *�û�״̬���Զ� 
	 *
	 *�û���ɫ���ֶ����� 
	 **/
	private boolean addUser2()throws Exception
	{
		//��ȡ��������ֵ 
		int ssa101 = Tools.getSequence("SSA101");
		//��ȡ��¼����������¼��д��dto
		String loginName = Tools.getLoginName("E");
		this.addEntry("loginName", loginName);

		//����sql1���
		StringBuilder sql1 = new StringBuilder()
				.append("insert into sa01(ssa101,ssa102,ssa103,ssa104,ssa105)")
				.append("       values(?,?,?,?,?)")
				;

		Object[] args1 = {ssa101,this.getVal("ssa102"),loginName,Tools.INIT_PWD,"01"};

		this.appendSql(sql1.toString(), args1);
		//����û���Ȩ
		String sql2="insert into sa03(ssa101,ssa302,ssa201) values(?,?,?)";
		Object stateList[]={ssa101,"1"};
		this.appendBatchSql(sql2, stateList, this.getArray("roleList"));
		return this.executeTransaction();
	}
	
	
	//����û�
	private boolean addUser()throws Exception
	{
		//1.����JDBC�ӿڱ���
		PreparedStatement pstm1 = null;
		try
		{
			//2.����sql
			StringBuilder sql1=new StringBuilder()
					.append("insert into sa01(ssa102,ssa103,ssa104,ssa105)")
					.append("   values(?,?,?,?)")
					;
			//3.����sql
			pstm1 = DBUtils.prepareStatement(sql1.toString());
			
			//��ȡ��¼��
			String loginName = Tools.getLoginName("E");
			//����¼��д��dto
			this.addEntry("loginName", loginName);
			
			
			//4.������ֵ
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
