package com.whm.services.support;

import java.io.Serializable;
import java.sql.PreparedStatement;

import com.whm.system.db.DBUtils;

public class PstmMetaData implements Serializable 
{
	private PreparedStatement  pstm = null; //语句对象
	private boolean isBatch = false;        //用以决定语句对象的执行方式
	
	public PstmMetaData(PreparedStatement pstm,boolean isBatch)
	{
		this.pstm = pstm;
		this.isBatch = isBatch;
	}
	
	//默认构造方法，非批处理
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
