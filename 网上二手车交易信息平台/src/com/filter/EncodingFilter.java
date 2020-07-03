package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


/*
 * 
 * �� filter ���½�һ�� EncodingFilter ������������ַ������룬
 * ����Ҫʵ�� Filter �ӿڣ�����д doFilter ����
 * 
 * */


public class EncodingFilter implements Filter {

	public EncodingFilter() {
		System.out.println("����������");
	}
	public void destroy() {
		System.out.println("����������");
	}
	
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		//Filter.super.init(filterConfig);
		System.out.println("��������ʼ��");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");
		System.out.println("����filter��תΪutf-8");
		chain.doFilter(request, response);

	}

}

