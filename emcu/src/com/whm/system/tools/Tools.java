package com.whm.system.tools;

import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mywq.util.LabelValueBean;

import com.whm.system.db.DBUtils;

public class Tools
{
	private Tools() {}
	
	public static void main(String[] args) 
	{
	    try
	    {
	    	System.out.println(Tools.getMd5Code("0000"));
	    }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    }
	}
	
	/**
	 * ��ȡ����ֵ
	 * 
	 */
	public static int getSequence(final String sname)throws Exception
	{
		PreparedStatement pstm1 = null;//��ȡ��ǰ���е�ֵ
		PreparedStatement pstm2 = null;//���µ�ǰ���е�ֵ
		ResultSet rs = null;
		try
		{
			StringBuilder sql1 = new StringBuilder()
					.append("select svalue")
					.append("  from sequence")
					.append(" where sname=?")
					;
			pstm1 = DBUtils.prepareStatement(sql1.toString());
			pstm1.setObject(1, sname);
			rs = pstm1.executeQuery();
			
			//�������ڸ��µ�sql2
			StringBuilder sql2 = new StringBuilder();
			
			int curval = 0;
			//�ж��Ƿ����ֵ
			if(rs.next()) //��ֵ������ȡ�͸�������ֵ
			{
				//��ȡ����
				curval = rs.getInt(1);
				//��������
				sql2.append("update sequence")
					.append("   set svalue=?")
					.append(" where sname=?")
					;
			}
			else //���Ҳ���sname���У���ʱ����
			{
				sql2.append("insert into sequence(svalue,sname)")
					.append("       values(?,?)")
					;
			}
			pstm2 = DBUtils.prepareStatement(sql2.toString());
			pstm2.setObject(1, ++curval);
			pstm2.setObject(2, sname);
			pstm2.executeUpdate();
			
			return curval;
		}
		finally
		{
			DBUtils.close(pstm1);
			DBUtils.close(pstm2);
		}
	}
	
	/***************************************************************************
	 *                    MD5Begin
	 ***************************************************************************/
	   /**
	    * �û���ʼ����
	    */
	   public static final String INIT_PWD="51ee7c97dda8bd6e0ed31ea01f50e7b1";
	   /**
	    * ��ȡMD5����
	    * @param pwd
	    * @return
	    * @throws Exception
	    */
	   public static String getMd5Code(Object pwd)throws Exception
	   {
		    //1.����MD5����
	    	String md5pwd1=Tools.MD5Encode(pwd);
	    	//2.����һ�μ��ܵ�����,���ɶ��λ�������
	    	String pwd2=md5pwd1+"�Ԥ��ͤ��o�ާڨƨڨԨɨŧ񨢨��������,�ԟo�����ѥ������з�,�[���æب��������Ө�����ڤ���⥧���ʟo��"+md5pwd1;
	    	//3.�Ի������Ľ��м���
	    	String md5pwd2=Tools.MD5Encode(pwd2);
	    	return md5pwd2;
	   }
	   
	
	    private final static String[] hexDigits = {
		     "0", "1", "2", "3", "4", "5", "6", "7",
		     "8", "9", "a", "b", "c", "d", "e", "f"};

		  /**
		   * ת���ֽ�����Ϊ16�����ִ�
		   * @param b �ֽ�����
		   * @return 16�����ִ�
		   */
		  private static String byteArrayToHexString(byte[] b)
		  {
		      StringBuffer resultSb = new StringBuffer();
		      for (int i = 0; i < b.length; i++)
		      {
		         resultSb.append(byteToHexString(b[i]));
		      }
		      return resultSb.toString();
		  }
		  /**
		   * ת���ֽ�Ϊ16�����ַ���
		   * @param b byte
		   * @return String
		   */
		  private static String byteToHexString(byte b)
		  {
		      int n = b;
		      if (n < 0)
		         n = 256 + n;
		      int d1 = n / 16;
		      int d2 = n % 16;
		      return hexDigits[d1] + hexDigits[d2];
		  }
		  /**
		   * �õ�MD5����������
		   * @param origin String
		   * @throws Exception
		   * @return String
		   */
		  private static String MD5Encode(Object origin) throws Exception
		  {
		       String resultString = null;
		       try
		       {
		           resultString=new String(origin.toString());
		           MessageDigest md = MessageDigest.getInstance("MD5");
		           resultString=byteArrayToHexString(md.digest(resultString.getBytes()));
		           return resultString;
		       }
		       catch (Exception ex)
		       {
		          throw ex;
		       }
		  }	
	/***************************************************************************
	 *                    MD5End
	 ***************************************************************************/
		  
		  
	/***********************�����û���¼��****************************/
	private static final String baseCode = "0000";
	public static String getLoginName(String first)throws Exception
	{
		//��ȡβ��
		int lastNumber = Tools.getLatNumber("SSA103");
		//��ʽ��β��
		String formatNumber = Tools.formatLastNumber(lastNumber);
		return first+Tools.getCurrentYear()+formatNumber;
	}
	
	//��ʽ��β��
	private static String formatLastNumber(int lastNumber)
	{
		//����β�볤��
		int len = String.valueOf(lastNumber).length();
		return baseCode.substring(len)+lastNumber;
	}
	
	//��ȡβ��
	private static int getLatNumber(final String sname)throws Exception
	{
		//1.����JDBC�ӿڱ���
		PreparedStatement pstm1 = null; //��ѯ���еĵ�ǰֵ
		PreparedStatement pstm2 = null; //�������еĵ�ǰֵ
		ResultSet rs = null;
		try
		{
			//2.����SQL
			StringBuilder sql1 = new StringBuilder()
					.append("select x.svalue") 
					.append("  from sequence x") 
					.append(" where x.sname=?") 
					.append("  and date_format(x.syear,'%Y')=date_format(current_date,'%Y')")	
					;
			//3.����sql
			pstm1 = DBUtils.prepareStatement(sql1.toString());
			//4.������ֵ
			pstm1.setObject(1, sname);
			//5.ִ�в�ѯ
			rs = pstm1.executeQuery();
			
			int currval = 0;
			//�������ڽ��ж����ж��δ����sql2
			StringBuilder sql2 = new StringBuilder();
			
			//�ж��Ƿ���ڲ�ѯ���
			if(rs.next())
			{
				//��ȡ��ǰ���е�ֵ
				currval = rs.getInt(1);
				//sql2:�������е�ֵ
				sql2.append("update sequence x")
					.append("   set x.svalue=?")
					.append(" where x.sname=?") 
					.append("   and date_format(x.syear,'%Y')=date_format(current_date,'%Y')")		 
					;
			}
			else // �޲�ѯ��������ǵ�һ������û�����ʱΪ�����������1
			{
				//sql1��¼�����еĵ�һ��ֵ
				sql2.append("insert into sequence(svalue,sname,syear)")
					.append("     values(?,?,current_date)")
					;
				
			}
			pstm2 = DBUtils.prepareStatement(sql2.toString());
			pstm2.setObject(1, ++currval);
			pstm2.setObject(2, sname);
			pstm2.executeUpdate();
			
			return currval;
		}
		finally
		{
			DBUtils.close(rs);
			DBUtils.close(pstm1);
			DBUtils.close(pstm2);
		}
		
	}
	
	
	private static String getCurrentYear()
	{
		//ʵ����������
		Date d = new Date();
		//���ڸ�ʽ��
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String sdate = sdf.format(d);
		return sdate;
	}
	
	/******************************************************************/
	
	
	/**
	 * ��ȡ�û���ɫ�б�
	 */
	public static List<LabelValueBean> getRoles()throws Exception
	{
		//1.����SQL���
		StringBuilder sql=new StringBuilder()
				.append("select ssa202,ssa201 ")
				.append("  from sa02 ")
				.append(" where ssa203=1")
				.append(" order by ssa202")		
				;
		return Tools.queryForBean(sql.toString(), new Object[] {});
	}
	
	
	/**
	 * ��ȡ��̬�����б�
	 * @param val
	 * @return
	 */
	public static List<LabelValueBean> getOptions(String fname)throws Exception
	{
		//1.����SQL���
		StringBuilder sql=new StringBuilder()
				.append("select fvalue,fcode ")
				.append("  from syscode ")
				.append(" where fname=?")
				.append(" order by fcode")		
				;
		//2.ִ�в�ѯ
		return Tools.queryForBean(sql.toString(), fname);
	}
	
	public static List<LabelValueBean> queryForBean(final String sql,final Object...args)throws Exception
	{
		//1.����JDBC�ӿڱ���
		PreparedStatement pstm=null;
		ResultSet rs=null;
		try
		{
			
			//3.����SQL���
			pstm=DBUtils.prepareStatement(sql);
			//4.������ֵ
			int index = 1;
			for(Object param:args)
			{
				pstm.setObject(index++, param);
			}
			//5.ִ�в�ѯ
			rs=pstm.executeQuery();
			
			//6.������������
			List<LabelValueBean> opts=new ArrayList<>();   //��������
			LabelValueBean bean=null;                      //��ǰ������
			
			//7.ѭ������rs
			while(rs.next())
			{
				//8.ʵ����bean
				bean=new LabelValueBean(rs.getString(1), rs.getString(2));
				//9.����ǰ�����ݷ���list
				opts.add(bean);
			}
			return opts;
		}
		finally
		{
			DBUtils.close(rs);
			DBUtils.close(pstm);
		}
	}
	
	public static String joinArray(Object val)
	{
		//����NPE
		if(val==null)
		{
			return "";
		}
		
		if(val instanceof java.lang.String[])
		{
			//��Object��ԭ������
			String array[]=(String[])val;
			//��������ĳ���
			int len=array.length;
			//�����ַ�������
			StringBuilder text=new StringBuilder(array[0]);
			for(int i=1;i<len;i++)
			{
				text.append(",").append(array[i]);
			}
			return text.toString();
		}
		else
		{
			return val.toString();
		}	
	}

}
