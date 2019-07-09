package com.saas.mybatis;

/**
 * 分页参数类
 * 
 */
public class PageParameter {

	public static final int DEFAULT_PAGE_SIZE = 10;

    private int pageSize;
    private int currentPage;
    private int prePage;
    private int nextPage;
    private int totalPage;
    private int totalCount;
    private int offset;
    //
    private String sortField;
    private String order;

    public PageParameter() {
        this.currentPage = 1;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    /**
     * 
     * @param currentPage
     * @param pageSize
     */
    public PageParameter(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

	public int getOffset() {
		return (this.currentPage - 1) * this.pageSize;
	}

	public void setOffset(int offset) {
		this.offset =offset ;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	public static PageParameter getPageParameter(String iDisplayStart,String iDisplayLength) {
		int page = 0;
		int size = 10;

		if (iDisplayStart == null) {
			iDisplayStart = "0";
		}
		if (iDisplayLength == null) {
			iDisplayLength = "10";
		}
		page = Integer.parseInt(iDisplayStart);
		size = Integer.parseInt(iDisplayLength);
		page = page / size;
		
		
		PageParameter pageParameter = new PageParameter(page+1, size);
		return pageParameter;
	}
}
