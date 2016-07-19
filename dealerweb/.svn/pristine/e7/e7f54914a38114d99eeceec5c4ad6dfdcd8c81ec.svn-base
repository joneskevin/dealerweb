package com.ava.util;
import java.util.List;

public class PageModel {
//	private int page = 1; // 当前页
	public int page = 1; // 当前页
	public int totalPages = 0; // 总页数
	private int pageRecorders;// 每页数据数
	private int totalRows = 0; // 总数据数
	private int pageStartRow = 0;// 每页的起始数
	private int pageEndRow = 0; // 每页显示数据的终止数
	private boolean hasNextPage = false; // 是否有下一页
	private boolean hasPreviousPage = false; // 是否有前一页
	public List<?> list;

	// private Iterator it;

	public PageModel() {
	}

	public PageModel(List<?> list, int pageRecorders) {
		init(list, pageRecorders);// 通过对象集，记录总数划分
	}

	/**
	 * 初始化list，并告之该list每页的记录数
	 * 
	 * @param list
	 * @param pageRecorders
	 */
	public void init(List<?> list, int pageRecorders) {
		this.pageRecorders = pageRecorders;
		this.list = list;
		totalRows = list.size();
		hasPreviousPage = false;
		if ((totalRows % pageRecorders) == 0) {
			totalPages = totalRows / pageRecorders;
		} else {
			totalPages = totalRows / pageRecorders + 1;
		}
		if (page >= totalPages) {
			hasNextPage = false;
		} else {
			hasNextPage = true;
		}

		if (totalRows < pageRecorders) {
			this.pageStartRow = 0;
			this.pageEndRow = totalRows;
		} else {
			this.pageStartRow = 0;
			this.pageEndRow = pageRecorders;
		}
	}

	/**
	 * 判断要不要分页
	 * 
	 * @return
	 */
	public boolean isNext() {
		return list.size() > this.pageRecorders;
	}

	public void description() {
		@SuppressWarnings("unused")
		String description = "共有数据数:" + this.getTotalRows() + "共有页数: "
				+ this.getTotalPages() + "当前页数为:" + this.getPage()
				+ " 是否有前一页: " + this.hasPreviousPage + " 是否有下一页:"
				+ this.hasNextPage + " 开始行数:" + this.getPageStartRow()
				+ " 终止行数:" + this.getPageEndRow();
	}

	/**
	 * 处理分页
	 */
	private void disposePage() {

		if (page == 0) {
			page = 1;
		}

		if ((page - 1) > 0) {
			hasPreviousPage = true;
		} else {
			hasPreviousPage = false;
		}

		if (page >= totalPages) {
			hasNextPage = false;
		} else {
			hasNextPage = true;
		}
	}

	/**
	 * 获得上一页内容
	 * 
	 * @return
	 */
	public List<?> getPreviousPage() {

		if (hasPreviousPage) {
			page = page - 1;
			this.disposePage();
			this.description();
		}
		return getObjects(page);
	}

	/**
	 * 获得下一页内容
	 * 
	 * @return
	 */
	public List<?> getNextPage() {
		if (hasNextPage) {
			page = page + 1;
			this.disposePage();
			this.description();
		}
		return getObjects(page);
	}

	/**
	 * 获取第几页的内容
	 * 
	 * @param page
	 * @return
	 */
	public List<?> getObjects(int page) {

		if (page == 0) {
			this.page = 1;
		}
		else if (page > this.totalPages) {
			this.page = totalPages;
		}
		else
		{
			this.page=page;
		}
		if ((this.page - 1) > 0) {
			hasPreviousPage = true;
		} else {
			hasPreviousPage = false;
		}

		if (this.page >= totalPages) {
			hasNextPage = false;
		} else {
			hasNextPage = true;
		}

		if (this.page * pageRecorders < totalRows) {// 判断是否为最后一页
			pageEndRow = this.page * pageRecorders;
			pageStartRow = pageEndRow - pageRecorders;
		} else {
			pageEndRow = totalRows;
			pageStartRow = pageRecorders * (totalPages - 1);
		}
		List<?> objects = null;
		if (!list.isEmpty()) {
			objects = list.subList(pageStartRow, pageEndRow);
		}
		// this.description();
		return objects;
	}

	/**
	 * 获得首页内容
	 * 
	 * @return
	 */
	public List<?> getFistPage() {
		if (this.isNext()) {
			page = 1;
			this.disposePage();
			return list.subList(0, pageRecorders);
		} else {
			return list;
		}
	}

	/**
	 * 获得尾页内容
	 * 
	 * @return
	 */
	public List<?> getLastPage() {
		if (this.isNext()) {
			this.disposePage();
			page = this.getTotalPages();
			return this.getObjects(this.getTotalPages());
		} else {
			return list;
		}
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageEndRow() {
		return pageEndRow;
	}

	public void setPageEndRow(int pageEndRow) {
		this.pageEndRow = pageEndRow;
	}

	public int getPageRecorders() {
		return pageRecorders;
	}

	public void setPageRecorders(int pageRecorders) {
		this.pageRecorders = pageRecorders;
	}

	public int getPageStartRow() {
		return pageStartRow;
	}

	public void setPageStartRow(int pageStartRow) {
		this.pageStartRow = pageStartRow;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}
}
 