package com.ava.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ava.gateway.facade.IProtocolParseBusinessFacade;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Rerun {
	private final static Logger log = LoggerFactory.getLogger(Rerun.class);
	
	
	private  static IProtocolParseBusinessFacade protocolParseBusinessFacade;

	/**
	 * 1.读取log或者txt文本信息 读取log或者txt信息
	 * 
	 * @param filePath
	 * @return
	 */
	public static List<String> readLog(String filePath) {
		List<String> list = new ArrayList<String>();

		try {
			FileInputStream is = new FileInputStream(filePath);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
			try {
				while ((line = br.readLine()) != null) {
					if (line.equals("")) {
						continue;
					} else {
						list.add(line);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("读取一行数据时出错");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("文件读取路径错误FileNotFoundException");
		}
		return list;
	}
	
//	/**
//	 * 处理上送报文
//	 *
//	 * @author wangchao
//	 * @version 
//	 * @param jsonStr
//	 */
//	public static void handleJson(String jsonStr){
//		protocolParseBusinessFacade = SpringUtil.getBean("testDriver.web2gatewayFacade",
//				IProtocolParseBusinessFacade.class);
//		protocolParseBusinessFacade.handleMessage(jsonStr);
//	}

//	/**
//	 * 处理main方法
//	 *
//	 * @author wangchao
//	 * @version 
//	 * @param args
//	 * @throws ParseException
//	 */
//	public static void main(String[] args) throws ParseException {
//		log.info("read config file start...");
//		ApplicationContext context = new ClassPathXmlApplicationContext(
//				"applicationContext.xml");
//		SpringUtil.setApplicationContext(context);
//		String logFileURI = CommonConfig.getString("log_fil_path");
//		log.info("read config file success...");
//		
//		List<String> jsonList = readLog(logFileURI);
//		
//		if( null != jsonList && jsonList.size()>0){
//			for(String jsonStr : jsonList){
//				log.info(" handle json [" + jsonStr + "] start");
//				handleJson(jsonStr);
//				log.info(" handle json [" + jsonStr + "] end");
//			}
//		
//		}
//	}

}
