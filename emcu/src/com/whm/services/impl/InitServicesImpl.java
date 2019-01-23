package com.whm.services.impl;

import java.util.*;
import java.sql.*;
import com.whm.system.db.DBUtils;

public final class InitServicesImpl 
{
    public Map<String,String> queryMenuListForRole()throws Exception
    {
    	PreparedStatement pstm=null;
    	ResultSet rs=null;
    	try
    	{
    		StringBuilder sql=new StringBuilder()
    				.append("select c.ssa201,group_concat(concat(b.ssa402,'-',b.ssa403,'-',b.ssa404) SEPARATOR '#')")
    				.append("  from sa05 a,sa04 b,sa02 c")
    				.append(" where a.ssa401=b.ssa401")
    				.append("   and a.ssa201=c.ssa201")
    				.append("   and a.ssa502=? and b.ssa405=?")
    				.append("   group by c.ssa201")
    				.append("   order by c.ssa201,b.ssa402")		
    		;
    		pstm=DBUtils.prepareStatement(sql.toString());
    		pstm.setObject(1, "1");
    		pstm.setObject(2, "1");
    		
    		rs=pstm.executeQuery();
    		Map<String,String> menu=new HashMap<>();
    		while(rs.next())
    		{
    			menu.put(rs.getString(1),rs.getString(2));
    		}
    		return menu;
    	}
    	finally
    	{
    		DBUtils.close(rs);
    		DBUtils.close(pstm);
    	}
    }
}
