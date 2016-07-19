package com.ava.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class MyHandlerExceptionResolver implements HandlerExceptionResolver {
	@ExceptionHandler(Exception.class)
    public String handleException(Exception ex,HttpServletRequest request) {     
        if(ex instanceof org.springframework.web.multipart.MaxUploadSizeExceededException){  
            request.setAttribute("error", "文件超过长度");  
        }
        return "";  
    }

	@Override
	public ModelAndView resolveException(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3) {
		// TODO Auto-generated method stub
		return null;
	}
}
