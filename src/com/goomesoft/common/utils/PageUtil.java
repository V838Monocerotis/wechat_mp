package com.goomesoft.common.utils;

import java.util.List;

/**
 * 分页工具类
 * @author eternal
 *
 */
public class PageUtil<T> {
	
	/**当前页*/
	private int currPage;
	/**上一页*/
	private int prevPage;
	/**下一页*/
	private int nextPage;
	/**总页数*/
	private int totalPages;
	/**当前页开始记录*/
	private long startRecord;
	/**总记录数*/
	private long totalRecords;
	/**每页记录数*/
	private int pageSize = 5;
	/**结果集*/
	private List<T> list;
	
	public PageUtil(int currPage, long totalRecords) {
		this.currPage = currPage;
		this.totalRecords = totalRecords;
		init();
	}
	
	public PageUtil(int pageSize, int currPage, long totalRecords) {
		this.pageSize = pageSize;
		this.currPage = currPage;
		this.totalRecords = totalRecords;
		init();
	}
	
	private void init() {
		//计算总页数
		int tempPage = (int) (totalRecords / pageSize);
		int temp = (int) (totalRecords % pageSize);
		totalPages = (temp > 0) ? tempPage + 1 : tempPage;
		
		//计算当前页开始记录
		startRecord = (currPage - 1) * pageSize;
		
		//上一页
		setPrevPage();
		//下一页
		setNextPage();
	}
	
	public int getCurrPage() {
		return currPage;
	}
	
	public int getPrevPage() {
		return prevPage;
	}
	
	public int getNextPage() {
		return nextPage;
	}
	
	public int getTotalPages() {
		return totalPages;
	}
	
	public long getStartRecord() {
		return startRecord;
	}
	
	public long getTotalRecords() {
		return totalRecords;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public List<T> getList() {
		return list;
	}
	
	public void setList(List<T> list) {
		this.list = list;
	}

	public void setPrevPage() {
		if(currPage <= 1) {
			prevPage = 1;
		} else {
			prevPage = currPage - 1;
		}
	}

	public void setNextPage() {
		if(currPage >= totalPages) {
			nextPage = totalPages;
		} else {
			nextPage = currPage + 1;
		}
	}

}
