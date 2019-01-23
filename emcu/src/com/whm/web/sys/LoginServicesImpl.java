package com.whm.web.sys;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.whm.system.db.DBUtils;
import com.whm.system.tools.Tools;

public class LoginServicesImpl 
{
	
	public final Map<String,String> checkUser(String userName,String pwd)throws Exception
	{
		PreparedStatement pstm=null;
		ResultSet rs=null;
		try
		{
			StringBuilder sql=new StringBuilder()
					.append("select c.ssa101,c.ssa102,c.ssa103,group_concat(a.ssa201 SEPARATOR ',') rolelist")
					.append("  from sa03 a,sa02 b,sa01 c")
					.append(" where a.ssa101=c.ssa101")
					.append("   and a.ssa201=b.ssa201")
					.append("   and a.ssa302=? and b.ssa203=? and c.ssa105=?")
					.append("   and c.ssa104=? and c.ssa103=?") 
					.append(" group by c.ssa101")		
			; 
			pstm=DBUtils.prepareStatement(sql.toString());
			pstm.setObject(1,"1" );
			pstm.setObject(2,"1");
			pstm.setObject(3,"01" );
			pstm.setObject(4, Tools.getMd5Code(pwd));
			pstm.setObject(5, userName);
			
			rs=pstm.executeQuery();
			Map<String,String> ins=null;
			if(rs.next())
			{
				ins=new HashMap<>();
				ins.put("ssa101", rs.getString(1));
				ins.put("ssa102", rs.getString(2));
				ins.put("ssa103", rs.getString(3));
				ins.put("roleList", rs.getString(4));
			}
			
			return ins;
		}
		finally
		{
			DBUtils.close(rs);
			DBUtils.close(pstm);
		}
	}

}
