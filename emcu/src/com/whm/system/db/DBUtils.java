/**
 * ���ݿ����ӹ�����
 */
package com.whm.system.db;

//���Ӷ���
import java.sql.Connection;
//Ԥ����������
import java.sql.PreparedStatement;
//���������
import java.sql.ResultSet;
import java.sql.SQLException;
//����������
import java.sql.DriverManager;
//��Դ�ļ�������
import java.util.ResourceBundle;
//�ֲ߳̾�����---�̸߳���
import java.lang.ThreadLocal;




public class DBUtils 
{
	private DBUtils() {}

	//������mysql����jar����,���������ĸ�,������·����ʲô����----������
	private static String driver=null;
	//�������ݿ����ڵ�λ��,���ݿ������--- ���Ӵ�
	private static String url=null;
	//�û���
	private static String userName=null;
	//����
	private static String password=null;
	
	//����ThreadLocal����,����ǰ�߳������Ӷ���󶨵�һ��
	private static final ThreadLocal<Connection> threadLocal=new ThreadLocal<>();
	

    /**************************************************************	
     *     ����Ϊ��̬��ʼ��ģ��
     *************************************************************/

    /**
     * ��̬��:
     *  ��̬���еĴ���,���౻��һ�μ������ڴ�ʱ��,ִ��һ��,�Ժ�ִ��
     */
	static
	{
		try 
		{
			//��ȡ��Դ�ļ�����������
			ResourceBundle bundle=ResourceBundle.getBundle("DBOptions");
			//ͨ������������,����Դ�ļ�,��ȡ�����Ϣ
			driver=bundle.getString("DRIVER");
			url=bundle.getString("URL");
			userName=bundle.getString("USERNAME");
			password=bundle.getString("PASSWORD");
			
			//��������    --- new com.mysql.jdbc.Driver()
			Class.forName(driver);
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	
    /**************************************************************	
     *                  ����Ϊ��������
     *************************************************************/
	/**
	 * ��������
	 * @throws Exception
	 */
     public static void beginTransaction()throws Exception
     {
    	 DBUtils.getConnection().setAutoCommit(false);
     }
     /**
      * �����ύ
      * @throws Exception
      */
     public static void commit()throws Exception
     {
    	 DBUtils.getConnection().commit();
     }
     /**
      * ����ع�
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
      * ��������
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
     *      ����ΪSQL���뷽��
     *************************************************************/
	/**
	 * ����SQL���
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static PreparedStatement prepareStatement(String sql)throws Exception
	{
		return DBUtils.getConnection().prepareStatement(sql);
	}
	
    /**************************************************************	
     *              ����Ϊ���Ӵ������� 
     *************************************************************/
	/**
	 * ��������
	 * <
	 *   ���½������뵱ǰ�̰߳�
	 * >
	 * @return
	 * @throws Exception
	 */
	private static Connection getConnection()throws Exception
	{
		//��threadLocal�л�ȡ��ǰ�̰߳����Ӷ���
		Connection conn=threadLocal.get();
		//�жϸ������Ƿ����
		if(conn==null || conn.isClosed())
		{
			//��������---ͨ������������,������Ŀ�����ݿ�����Ӷ���
			conn=DriverManager.getConnection(url, userName, password);
			//Ϊ��ǰ�̰߳����Ӷ���
			threadLocal.set(conn);
		}
		//��������
		return conn;
	} 

	
    /**************************************************************	
     *         ����Ϊ��Դ���ٷ���
     *************************************************************/
	
	public static void close(ResultSet rs)
	{
		try
		{
			if(rs!=null)   //���NPE
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
			if(pstm!=null)   //���NPE
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
			//��ȡ��ǰ�̰߳󶨵����Ӷ���
			Connection conn=threadLocal.get();
			//�ж������Ƿ���Ҫ�ر�
			if(conn!=null && !conn.isClosed())
			{
				//�����ǰ�߳������ӵİ󶨹�ϵ
				threadLocal.remove();
				//�ر�����
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









