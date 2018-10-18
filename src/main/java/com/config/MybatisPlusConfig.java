/*package com.config;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.log4j.Logger;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class MybatisPlusConfig {
  
  private static Logger log = Logger.getLogger(MybatisPlusConfig.class);
  
  @Autowired(required=false)
  public DataSource dataSource;

  @Autowired
  public MybatisProperties properties;

  @Autowired
  private DefaultResourceLoader resourceLoader = new DefaultResourceLoader();

  @Autowired(required=false)
  public Interceptor[] interceptors;
	  
  public DataSource getDataSource() {
	return dataSource;
  }
  public void setDataSource(DataSource dataSource) {
	this.dataSource = dataSource;
  }
  public void setProperties(MybatisProperties properties) {
	this.properties = properties;
  }
  public void setResourceLoader(DefaultResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }
  public void setInterceptors(Interceptor[] interceptors) {
	this.interceptors = interceptors;
  }
  
  @Bean
  public PaginationInterceptor paginationInterceptor() {
    PaginationInterceptor page = new PaginationInterceptor();
    page.setDialectType("mysql");
    return page;
  }

  @Bean
  public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean() {
    String str = "mybatis-plus配置,sqlsession  dataSource=" + this.dataSource; 
    log.info(str);
    MybatisSqlSessionFactoryBean mybatisPlus = new MybatisSqlSessionFactoryBean();
    mybatisPlus.setDataSource(this.dataSource);
    mybatisPlus.setVfs(SpringBootVFS.class);
    if (StringUtils.hasText(this.properties.getConfigLocation())) {
      mybatisPlus.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));
    }
    mybatisPlus.setConfiguration(this.properties.getConfiguration());
    mybatisPlus.setPlugins(this.interceptors);

    GlobalConfiguration globalConfig = new GlobalConfiguration();
    globalConfig.setDbType(DBType.MYSQL.name());
    globalConfig.setIdType(0);
    globalConfig.setDbColumnUnderline(true);

    MybatisConfiguration mc = new MybatisConfiguration();
    mc.setMapUnderscoreToCamelCase(true);
    mc.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);

    mybatisPlus.setGlobalConfig(globalConfig);
    mybatisPlus.setConfiguration((org.apache.ibatis.session.Configuration)mc);
    mybatisPlus.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
    
    if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
      mybatisPlus.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
    }
    if (!ObjectUtils.isEmpty((Object[])this.properties.resolveMapperLocations())) {
      mybatisPlus.setMapperLocations(this.properties.resolveMapperLocations());
    }
    return mybatisPlus;
  }
}
*/