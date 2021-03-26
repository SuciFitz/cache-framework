package com.sucifitz.eshop.eshopcache;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 缓存服务入口
 *
 * @author S zh
 */
@SpringBootApplication
@MapperScan("com.sucifitz.eshop.cache.mapper")
public class EshopCacheApplication {

    /**
     * 构建数据源
     *
     * @return DataSource
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return new DataSource();
    }

    /**
     * 构建mybatis入口类：sqlSessionFactory
     *
     * @return SqlSessionFactory
     * @throws Exception getResource getObject
     */
    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 构建事务管理器
     *
     * @return DataSourceTransactionManager
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    // /**
    //  * 注册监听器
    //  * @return ServletListenerRegistrationBean
    //  */
    // @Bean
    // public ServletListenerRegistrationBean<InitListener> servletListenerRegistrationBean() {
    //     ServletListenerRegistrationBean<InitListener> bean =
    //             new ServletListenerRegistrationBean<>();
    //     bean.setListener(new InitListener());
    //     return bean;
    // }

    public static void main(String[] args) {
        SpringApplication.run(EshopCacheApplication.class, args);
    }

}
