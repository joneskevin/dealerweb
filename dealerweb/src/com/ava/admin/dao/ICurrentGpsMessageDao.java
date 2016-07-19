package com.ava.admin.dao;

import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.ava.admin.domain.vo.CurrentGpsMessageVo;
import com.ava.gateway.domain.entity.CurrentGpsMessage;

public interface ICurrentGpsMessageDao {
	
      public List<CurrentGpsMessageVo> findGpsMessage(Map<String, Object> argMap);

  	  public Queue<String> findMessage(Map<String,Object> argMap,String taskId);
  	  
  	  /**
  	   * 
  	   * 查询GPS报文
  	   * @author tangqingsong
  	   * @version 
  	   * @param argMap
  	   * @return
  	   */
  	  public List<CurrentGpsMessage> findCurrentGpsMessage(Map<String, Object> argMap);
 
  	  public void saveCurrentGpsMessage(CurrentGpsMessage gpsMessage);
}
