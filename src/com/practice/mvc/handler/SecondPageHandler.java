package com.practice.mvc.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecondPageHandler extends BaseEventHandler{

	@Override
	public String process(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		String nextUrl = null;
		nextUrl = "nextpage.jsp";
		
		return nextUrl;
	}
	
}
