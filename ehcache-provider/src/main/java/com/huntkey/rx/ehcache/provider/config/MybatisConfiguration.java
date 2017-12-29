package com.huntkey.rx.ehcache.provider.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration(value = "MybatisConfiguration")
@MapperScan("com.huntkey.rx.ehcache.provider.dao")
public class MybatisConfiguration {

	@Value("${druid.jdbc.driveClassName}")
	private String driveClassName;

	@Value("${druid.jdbc.jdbcUrl}")
	private String jdbcUrl;

	@Value("${druid.jdbc.jdbcUsername}")
	private String jdbcUsername;

	@Value("${druid.jdbc.jdbcPassword}")
	private String jdbcPassword;

	@Value("${druid.jdbc.filters}")
	private String filters;

	@Value("${druid.jdbc.maxActive}")
	private int maxActive;

	@Value("${druid.jdbc.initialSize}")
	private int initialSize;

	@Value("${druid.jdbc.maxWait}")
	private int maxWait;

	@Value("${druid.jdbc.minIdle}")
	private int minIdle;

	@Value("${druid.jdbc.timeBetweenEvictionRunsMillis}")
	private long timeBetweenEvictionRunsMillis;

	@Value("${druid.jdbc.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;

	@Value("${druid.jdbc.validationQuery}")
	private String validationQuery;

	@Value("${druid.jdbc.testWhileIdle}")
	private boolean testWhileIdle;

	@Value("${druid.jdbc.testOnBrowwon}")
	private boolean testOnBrowwon;

	@Value("${druid.jdbc.testOnReturn}")
	private boolean testOnReturn;

	@Bean
	public DataSource dataSource() {
		
		System.out.println(this.driveClassName);
		
		DruidDataSource datasource = new DruidDataSource();
		datasource.setDriverClassName(this.driveClassName);
		datasource.setUrl(this.jdbcUrl);
		datasource.setUsername(this.jdbcUsername);
		datasource.setPassword(this.jdbcPassword);
		datasource.setMaxActive(this.maxActive);
		datasource.setInitialSize(this.initialSize);
		datasource.setMaxWait(this.maxWait);
		datasource.setMinIdle(this.minIdle);
		datasource.setTimeBetweenEvictionRunsMillis(this.timeBetweenEvictionRunsMillis);
		datasource.setMinEvictableIdleTimeMillis(this.minEvictableIdleTimeMillis);
		datasource.setValidationQuery(this.validationQuery);
		datasource.setTestWhileIdle(this.testWhileIdle);
		datasource.setTestOnBorrow(this.testOnBrowwon);
		datasource.setTestOnReturn(this.testOnReturn);

		try {
			datasource.setFilters("stat,wall");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return datasource;
	}
}
