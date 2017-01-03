package com.gcit.lms.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class BaseDAO {
	
	@Autowired
	JdbcTemplate template;
	
	private Integer pageNo;
	private Integer pageSize = 10;

	public Integer getFetchSize() {
		return fetchSize;
	}

	public void setFetchSize(Integer fetchSize) {
		this.fetchSize = fetchSize;
	}

	private Integer fetchSize = 0;

	/**
	 * @return the pageNo
	 */
	public Integer getPageNo() {
		return pageNo;
	}

	/**
	 * @param pageNo the pageNo to set
	 */
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	protected Integer getQueryCount(String sql, String query) {

		String newSql = "SELECT count(*) " + sql.split("SELECT * ")[1].substring(1);

		if(query != null && !query.isEmpty()) {
			return template.queryForObject(
					newSql,
					new Object[]{query},
					Integer.class
			);
		} else {
			return template.queryForObject(
					newSql,
					Integer.class
			);
		}
	}

	protected String getPagedSql(String sql) {
		if(getPageNo()!=null && getPageNo() > 0){
			Integer limit = (getPageNo()-1) * getPageSize();
			sql += " LIMIT " + limit + " ," + getPageSize();
		}

		return sql;
	}
}
