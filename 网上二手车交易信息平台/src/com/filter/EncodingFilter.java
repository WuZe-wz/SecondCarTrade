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
 * 在 filter 下新建一个 EncodingFilter 用来解决中文字符集乱码，
 * 它需要实现 Filter 接口，并重写 doFilter 函数
 * 
 * */


public class EncodingFilter implements Filter {

	public EncodingFilter() {
		System.out.println("过滤器构造");
	}
	public void destroy() {
		System.out.println("过滤器销毁");
	}
	
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		//Filter.super.init(filterConfig);
		System.out.println("过滤器初始化");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");
		System.out.println("经过filter，转为utf-8");
		chain.doFilter(request, response);

	}

}

