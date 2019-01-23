/**
 * ���Ŀ�����:
 *    �����������е���.html��β,�û�����,���ҽ�������䵽��Ӧ��  ҵ�������  ��
 */
package com.whm.web.support;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.whm.web.support.BaseController;


@WebServlet("*.html")
public final class ServletSupport extends HttpServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String toPath=null;
		try
		{
			//1.��ȡservlet������,����ɳ����滻(/--->""   .html--->Servlet)
			String servletPath=request.getServletPath().replace("/", "").replace(".html", "Servlet");
			//2.ƴ��ִ��������
			String servletName="com.whm.web.impl."+servletPath.substring(0, 1).toUpperCase()+servletPath.substring(1);
			//3.����ҵ�������--���ڷ�����ɶ��󴴽�
			BaseController controller=(BaseController)Class.forName(servletName).newInstance();
			
			//��ҵ�������֯��DTO��Ƭ
			controller.setDto(this.createDto(request));    //�������Ѿ�������ҳ������
			
			//��ʼ��Controller
			controller.initController();
			controller.initRole();
			
			//����Services
			controller.createServices();   //���߿�����,ʵ����Services
			
			//��ʼ��Services
			controller.initServices();  //����ҵ�������,������ҳ�����ݵ�dto,�ٴ���services
			
			//�������̿��Ʒ���
			toPath=controller.execute();
			
			//��ȡ����
			Map<String,Object> attributes=controller.getAttributes();
			//֯�����Դ�����Ƭ
			this.saveAttributes(request, attributes);
			
		}
		catch(Exception ex)
		{
			request.setAttribute("msg", "�������!");
			ex.printStackTrace();
		}
		
		request.getRequestDispatcher("/"+toPath+".jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doGet(request, response);
	}
	
	/**
	 * ���Դ�����Ƭ
	 * @param request
	 * @param atts
	 */
	private void saveAttributes(HttpServletRequest request,Map<String,Object> atts)
	{
		//1.��ȡatts�����еļ�ֵ���γɼ���
		Set<Entry<String,Object>> entrySet=atts.entrySet();
		//2.ѭ��entrySet
		for(Entry<String,Object> entry:entrySet)
		{
			//��Map��ÿ��ӳ��,�洢��request��,�γ�����
			request.setAttribute(entry.getKey(),entry.getValue());
		}
		//������Ե���ʱ�洢����
		atts.clear();
	}
	
	/**
	 * DTO��Ƭ
	 * @param request
	 * @return
	 */
	private final Map<String,Object>  createDto(HttpServletRequest request)
	{
	    //��ȡҳ������,�γ�Map
		Map<String,String[]> tem=request.getParameterMap();
		//����tem��,��ֵ�Եĸ���
		int count=tem.size();
		//���㰲ȫ�ĳ�ʼ����
		int initSize=((int)(count/0.75))+1;
		
		//�������м�ֵ��,�γɼ���
		Set<Entry<String,String[]>> entrySet=tem.entrySet();
		//����DTO����
		Map<String,Object> dto=new HashMap<>(initSize);
		//�������,��ʾtem��value����
		String[] val=null;
		//ѭ������,��ȡÿ��Entry����
		for(Entry<String,String[]> entry:entrySet)
		{
			//��ȡentry�����value����,�γ�����
			val=entry.getValue();
			//�������鳤���ж�ҳ��ؼ�����
			if(val.length==1)  //��checkbox
			{
				//��ԭ����Ϊҳ��¼��״̬
				if(val[0]!=null && !val[0].equals(""))
				{
					dto.put(entry.getKey(), val[0]);	
				}
			}
			else     //checkbox
			{
				//�����鷽ʽ,װ��ҳ���checkbox������
				dto.put(entry.getKey(), val);
			}	
		}
		return dto;
	}

}
