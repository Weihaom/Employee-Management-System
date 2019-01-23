package com.whm.services.support;

import java.io.Serializable;
import java.sql.PreparedStatement;

import com.whm.system.db.DBUtils;

public class PstmMetaData implements Serializable 
{
	private PreparedStatement  pstm = null; //������
	private boolean isBatch = false;        //���Ծ����������ִ�з�ʽ
	
	public PstmMetaData(PreparedStatement pstm,boolean isBatch)
	{
		this.pstm = pstm;
		this.isBatch = isBatch;
	}
	
	//Ĭ�Ϲ��췽������������
	public PstmMetaData(PreparedStatement pstm)
	{
		this(pstm, false);
	}
	
	public void executePstm()throws Exception
	{
		if(this.isBatch)
		{
			this.pstm.executeBatch();
		}
		else
		{
			this.pstm.executeUpdate();
		}
	}
	
	public void close()
	{
		DBUtils.close(this.pstm);
	}
	
}
