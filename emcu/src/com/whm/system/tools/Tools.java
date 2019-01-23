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
	 * 获取序列值
	 * 
	 */
	public static int getSequence(final String sname)throws Exception
	{
		PreparedStatement pstm1 = null;//获取当前序列的值
		PreparedStatement pstm2 = null;//更新当前序列的值
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
			
			//定义用于更新的sql2
			StringBuilder sql2 = new StringBuilder();
			
			int curval = 0;
			//判断是否存在值
			if(rs.next()) //有值，即获取和更新序列值
			{
				//获取序列
				curval = rs.getInt(1);
				//更新序列
				sql2.append("update sequence")
					.append("   set svalue=?")
					.append(" where sname=?")
					;
			}
			else //查找不到sname序列，此时创建
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
	    * 用户初始密码
	    */
	   public static final String INIT_PWD="51ee7c97dda8bd6e0ed31ea01f50e7b1";
	   /**
	    * 获取MD5密文
	    * @param pwd
	    * @return
	    * @throws Exception
	    */
	   public static String getMd5Code(Object pwd)throws Exception
	   {
		    //1.生成MD5密文
	    	String md5pwd1=Tools.MD5Encode(pwd);
	    	//2.基于一次加密的密文,生成二次混淆明文
	    	String pwd2=md5pwd1+"以ちねぉ無限иㄆㄚㄔㄉㄅяáǐр為有限,以無法為паセヵヱ有法,隱āγωáǎà技ㄓㄤㄖ巧于ぁゐモェィナ無形"+md5pwd1;
	    	//3.对混淆明文进行加密
	    	String md5pwd2=Tools.MD5Encode(pwd2);
	    	return md5pwd2;
	   }
	   
	
	    private final static String[] hexDigits = {
		     "0", "1", "2", "3", "4", "5", "6", "7",
		     "8", "9", "a", "b", "c", "d", "e", "f"};

		  /**
		   * 转换字节数组为16进制字串
		   * @param b 字节数组
		   * @return 16进制字串
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
		   * 转换字节为16进制字符串
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
		   * 得到MD5的秘文密码
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
		  
		  
	/***********************生成用户登录名****************************/
	private static final String baseCode = "0000";
	public static String getLoginName(String first)throws Exception
	{
		//获取尾码
		int lastNumber = Tools.getLatNumber("SSA103");
		//格式化尾码
		String formatNumber = Tools.formatLastNumber(lastNumber);
		return first+Tools.getCurrentYear()+formatNumber;
	}
	
	//格式化尾码
	private static String formatLastNumber(int lastNumber)
	{
		//计算尾码长度
		int len = String.valueOf(lastNumber).length();
		return baseCode.substring(len)+lastNumber;
	}
	
	//获取尾码
	private static int getLatNumber(final String sname)throws Exception
	{
		//1.定义JDBC接口变量
		PreparedStatement pstm1 = null; //查询序列的当前值
		PreparedStatement pstm2 = null; //更新序列的当前值
		ResultSet rs = null;
		try
		{
			//2.定义SQL
			StringBuilder sql1 = new StringBuilder()
					.append("select x.svalue") 
					.append("  from sequence x") 
					.append(" where x.sname=?") 
					.append("  and date_format(x.syear,'%Y')=date_format(current_date,'%Y')")	
					;
			//3.编译sql
			pstm1 = DBUtils.prepareStatement(sql1.toString());
			//4.参数赋值
			pstm1.setObject(1, sname);
			//5.执行查询
			rs = pstm1.executeQuery();
			
			int currval = 0;
			//定义用于进行对序列二次处理的sql2
			StringBuilder sql2 = new StringBuilder();
			
			//判断是否存在查询结果
			if(rs.next())
			{
				//获取当前序列的值
				currval = rs.getInt(1);
				//sql2:更新序列的值
				sql2.append("update sequence x")
					.append("   set x.svalue=?")
					.append(" where x.sname=?") 
					.append("   and date_format(x.syear,'%Y')=date_format(current_date,'%Y')")		 
					;
			}
			else // 无查询结果，即是第一次添加用户。此时为序列添加数据1
			{
				//sql1：录入序列的第一个值
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
		//实例化日期类
		Date d = new Date();
		//日期格式类
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String sdate = sdf.format(d);
		return sdate;
	}
	
	/******************************************************************/
	
	
	/**
	 * 获取用户角色列表
	 */
	public static List<LabelValueBean> getRoles()throws Exception
	{
		//1.定义SQL语句
		StringBuilder sql=new StringBuilder()
				.append("select ssa202,ssa201 ")
				.append("  from sa02 ")
				.append(" where ssa203=1")
				.append(" order by ssa202")		
				;
		return Tools.queryForBean(sql.toString(), new Object[] {});
	}
	
	
	/**
	 * 获取动态下拉列表
	 * @param val
	 * @return
	 */
	public static List<LabelValueBean> getOptions(String fname)throws Exception
	{
		//1.定义SQL语句
		StringBuilder sql=new StringBuilder()
				.append("select fvalue,fcode ")
				.append("  from syscode ")
				.append(" where fname=?")
				.append(" order by fcode")		
				;
		//2.执行查询
		return Tools.queryForBean(sql.toString(), fname);
	}
	
	public static List<LabelValueBean> queryForBean(final String sql,final Object...args)throws Exception
	{
		//1.定义JDBC接口变量
		PreparedStatement pstm=null;
		ResultSet rs=null;
		try
		{
			
			//3.编译SQL语句
			pstm=DBUtils.prepareStatement(sql);
			//4.参数赋值
			int index = 1;
			for(Object param:args)
			{
				pstm.setObject(index++, param);
			}
			//5.执行查询
			rs=pstm.executeQuery();
			
			//6.定义数据容器
			List<LabelValueBean> opts=new ArrayList<>();   //所有数据
			LabelValueBean bean=null;                      //当前行数据
			
			//7.循环解析rs
			while(rs.next())
			{
				//8.实例化bean
				bean=new LabelValueBean(rs.getString(1), rs.getString(2));
				//9.将当前行数据放入list
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
		//拦截NPE
		if(val==null)
		{
			return "";
		}
		
		if(val instanceof java.lang.String[])
		{
			//将Object还原成数组
			String array[]=(String[])val;
			//计算数组的长度
			int len=array.length;
			//构建字符串载体
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
