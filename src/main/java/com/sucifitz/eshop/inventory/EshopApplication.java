package com.sucifitz.eshop.inventory;

import com.sucifitz.eshop.inventory.listener.InitListener;
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
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * 库存服务入口
 *
 * @author S zh
 */
@SpringBootApplication
@MapperScan("com.sucifitz.eshop.inventory.mapper")
public class EshopApplication {

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

    /**
     * 构建redis集群数据源
     *
     * @return JedisCluster
     */
    @Bean
    public JedisCluster jedisClusterFactory() {
        Set<HostAndPort> jedisClusterNodes = new HashSet<>();
        jedisClusterNodes.add(new HostAndPort("sucifitz.top", 8010));
        jedisClusterNodes.add(new HostAndPort("sucifitz.top", 8011));
        jedisClusterNodes.add(new HostAndPort("sucifitz.top", 8012));
        return new JedisCluster(jedisClusterNodes);
    }

    /**
     * 注册监听器
     * @return ServletListenerRegistrationBean
     */
    @Bean
    public ServletListenerRegistrationBean<InitListener> servletListenerRegistrationBean() {
        ServletListenerRegistrationBean<InitListener> bean =
                new ServletListenerRegistrationBean<>();
        bean.setListener(new InitListener());
        return bean;
    }

    public static void main(String[] args) {
        SpringApplication.run(EshopApplication.class, args);
    }

}
