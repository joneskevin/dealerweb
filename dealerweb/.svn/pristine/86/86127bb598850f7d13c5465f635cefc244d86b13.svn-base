package com.ava.exception;

import javax.servlet.http.HttpServletRequest;   
import javax.servlet.http.HttpServletResponse;   
  
import org.springframework.web.servlet.HandlerExceptionResolver;   
import org.springframework.web.servlet.ModelAndView;   
  
public class FileUploadExceptionHandler implements HandlerExceptionResolver {
    @Override  
    public ModelAndView resolveException(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex) {   
       if(ex instanceof org.springframework.web.multipart.MaxUploadSizeExceededException){
    	   	return new ModelAndView("upLoad");//这里就是跳转的视图
       }
       if(ex instanceof java.io.FileNotFoundException){
    	   return new ModelAndView("exception");  
    	}
       return null;
    }
}
