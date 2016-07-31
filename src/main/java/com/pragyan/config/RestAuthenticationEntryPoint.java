package com.pragyan.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static final int UNAUTHORIZED = HttpStatus.UNAUTHORIZED.value();
	
	
	@Override
	public void commence(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, AuthenticationException authEx)
			throws IOException, ServletException {


	    String message = "No authentication exception provided. This is an unexpected error.";
	    if (authEx != null) {
	      message = authEx.getMessage();
	    }

	   

	    response.setContentType("application/json;charset=UTF-8");
	    response.setStatus(UNAUTHORIZED);
	    response.setContentLength(message.length());
	    response.getWriter().print(message);
	    response.getWriter().flush();
		
	}

}
