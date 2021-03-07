package com.sucifitz.eshop.inventory;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 库存服务入口
 *
 * @author S zh
 */
@SpringBootApplication
// @MapperScan("com.sucifitz.eshop.inventory.mapper")
public class EshopApplication {

    /**
     * 构建数据源
     *
     * @return DataSource
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.source")
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
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mybatis/*.xml"));
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

    public static void main(String[] args) {
        SpringApplication.run(EshopApplication.class, args);
    }

}
