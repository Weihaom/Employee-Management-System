/**
 * 数据库连接管理类
 */
package com.whm.system.db;

//连接对象
import java.sql.Connection;
//预编译语句对象
import java.sql.PreparedStatement;
//结果集对象
import java.sql.ResultSet;
import java.sql.SQLException;
//驱动管理器
import java.sql.DriverManager;
//资源文件解析器
import java.util.ResourceBundle;
//线程局部变量---线程副本
import java.lang.ThreadLocal;




public class DBUtils 
{
	private DBUtils() {}

	//描述在mysql驱动jar包中,核心类是哪个,其完整路径是什么样的----驱动串
	private static String driver=null;
	//描述数据库所在的位置,数据库的名称--- 连接串
	private static String url=null;
	//用户名
	private static String userName=null;
	//密码
	private static String password=null;
	
	//定义ThreadLocal变量,将当前线程与连接对象绑定到一起
	private static final ThreadLocal<Connection> threadLocal=new ThreadLocal<>();
	

    /**************************************************************	
     *     以下为静态初始化模块
     *************************************************************/

    /**
     * 静态块:
     *  静态块中的代码,在类被第一次加载如内存时候,执行一次,以后不执行
     */
	static
	{
		try 
		{
			//获取资源文件解析器对象
			ResourceBundle bundle=ResourceBundle.getBundle("DBOptions");
			//通过解析器对象,从资源文件,获取相关信息
			driver=bundle.getString("DRIVER");
			url=bundle.getString("URL");
			userName=bundle.getString("USERNAME");
			password=bundle.getString("PASSWORD");
			
			//加载驱动    --- new com.mysql.jdbc.Driver()
			Class.forName(driver);
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	
    /**************************************************************	
     *                  以下为事务处理方法
     *************************************************************/
	/**
	 * 开启事务
	 * @throws Exception
	 */
     public static void beginTransaction()throws Exception
     {
    	 DBUtils.getConnection().setAutoCommit(false);
     }
     /**
      * 事务提交
      * @throws Exception
      */
     public static void commit()throws Exception
     {
    	 DBUtils.getConnection().commit();
     }
     /**
      * 事务回滚
      */
     public static void rollback()
     {
    	 try 
    	 {
			DBUtils.getConnection().rollback();
		 }
    	 catch (Exception e) 
    	 {
			e.printStackTrace();
		}
     }
     /**
      * 结束事务
      */
     public static void endTransaction()
     {
    	 try 
    	 {
			DBUtils.getConnection().setAutoCommit(true);
		 }
    	 catch (Exception e) 
    	 {
			e.printStackTrace();
		 }
     }
	
	
    /**************************************************************	
     *      以下为SQL编译方法
     *************************************************************/
	/**
	 * 编译SQL语句
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static PreparedStatement prepareStatement(String sql)throws Exception
	{
		return DBUtils.getConnection().prepareStatement(sql);
	}
	
    /**************************************************************	
     *              以下为连接创建方法 
     *************************************************************/
	/**
	 * 创建连接
	 * <
	 *   将新建连接与当前线程绑定
	 * >
	 * @return
	 * @throws Exception
	 */
	private static Connection getConnection()throws Exception
	{
		//到threadLocal中获取当前线程绑定连接对象
		Connection conn=threadLocal.get();
		//判断该连接是否可用
		if(conn==null || conn.isClosed())
		{
			//创建连接---通过驱动管理器,创建到目标数据库的连接对象
			conn=DriverManager.getConnection(url, userName, password);
			//为当前线程绑定连接对象
			threadLocal.set(conn);
		}
		//返回连接
		return conn;
	} 

	
    /**************************************************************	
     *         以下为资源销毁方法
     *************************************************************/
	
	public static void close(ResultSet rs)
	{
		try
		{
			if(rs!=null)   //规避NPE
			{
				rs.close();	
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static void close(PreparedStatement pstm)
	{
		try
		{
			if(pstm!=null)   //规避NPE
			{
				pstm.close();	
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public static void close()
	{
		try
		{
			//获取当前线程绑定的连接对象
			Connection conn=threadLocal.get();
			//判断连接是否需要关闭
			if(conn!=null && !conn.isClosed())
			{
				//解除当前线程与连接的绑定关系
				threadLocal.remove();
				//关闭连接
				conn.close();	
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) 
	{
		try 
		{
			for(int i=0;i<1;i++)
			{
				System.out.println(DBUtils.getConnection());	
			}
			
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}









