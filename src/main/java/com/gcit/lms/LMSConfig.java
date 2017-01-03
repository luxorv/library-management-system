package com.gcit.lms;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoanDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.service.AuthorService;
import com.gcit.lms.service.BookCopiesService;
import com.gcit.lms.service.BookLoanService;
import com.gcit.lms.service.BookService;
import com.gcit.lms.service.BorrowerService;
import com.gcit.lms.service.BranchService;
import com.gcit.lms.service.GenreService;
import com.gcit.lms.service.PublisherService;

@EnableTransactionManagement
@Configuration
public class LMSConfig {
	
	private String driverName = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost/library?useSSL=false";
	private String uname = "root";
	private String pwd = "test";
	
	@Bean
	public BasicDataSource dataSource(){
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driverName);
		ds.setUrl(url);
		ds.setUsername(uname);
		ds.setPassword(pwd);
		
		return ds;
	}
	
	@Bean
	public JdbcTemplate template(){
		JdbcTemplate template = new JdbcTemplate();
		template.setDataSource(dataSource());
		
		return template;
	}
	
	@Bean
	public PlatformTransactionManager txManager(){
		DataSourceTransactionManager txManager = new DataSourceTransactionManager();
		txManager.setDataSource(dataSource());
		
		return txManager;
	}
	
	@Bean
	public AuthorDAO authorDao(){
		return new AuthorDAO();
	}
	
	@Bean
	public BookDAO bookDao(){
		return new BookDAO();
	}
	
	@Bean
	public BookCopiesDAO bookCopiesDao(){
		return new BookCopiesDAO();
	}
	
	@Bean
	public BookLoanDAO bookLoanDao(){
		return new BookLoanDAO();
	}
	
	@Bean
	public BorrowerDAO borrowerDao(){
		return new BorrowerDAO();
	}
	
	@Bean
	public GenreDAO genreDao(){
		return new GenreDAO();
	}
	
	@Bean
	public BranchDAO branchDao(){
		return new BranchDAO();
	}
	
	@Bean
	public PublisherDAO publisherDao(){
		return new PublisherDAO();
	}

	@Bean
	public BookService bookService() {
		return new BookService();
	}
	
	@Bean
	public AuthorService authorService() {
		return new AuthorService();
	}
	
	@Bean
	public GenreService genreService() {
		return new GenreService();
	}
	
	@Bean
	public BookCopiesService copiesService() {
		return new BookCopiesService();
	}
	
	@Bean
	public BookLoanService loanService() {
		return new BookLoanService();
	}
	
	@Bean
	public BorrowerService borrowerService() {
		return new BorrowerService();
	}
	
	@Bean
	public BranchService branchService() {
		return new BranchService();
	}
	
	@Bean
	public PublisherService publisherService() {
		return new PublisherService();
	}
}
