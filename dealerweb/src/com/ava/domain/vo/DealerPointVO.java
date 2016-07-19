package com.ava.domain.vo;

import java.util.List;

public class DealerPointVO  {
	
	private Integer status;
	
	private String message;
	
	private List<DealerPointResultVO> results;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<DealerPointResultVO> getResults() {
		return results;
	}

	public void setResults(List<DealerPointResultVO> results) {
		this.results = results;
	}


	
	
}
