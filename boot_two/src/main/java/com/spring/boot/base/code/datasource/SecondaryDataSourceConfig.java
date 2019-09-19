package com.spring.boot.base.code.datasource;

import com.spring.boot.base.BaseController;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@EnableTransactionManagement  //boot事务支持
@Configuration
//mybatis 接口类存放的位置
@MapperScan(basePackages = "com.spring.boot.base.code.two.dao", sqlSessionFactoryRef = "secondarySqlSessionFactory")
public class SecondaryDataSourceConfig extends BaseController {
    /*
     *创建数据源
     *
     */
    @Bean(name = "secondaryDataSource")  //返回值作为Bean注入到spring ico容器中
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource getSecondaryDataSource() {
        return DataSourceBuilder.create().build();//创建数据源，根据jdbc url自动检测驱动程序
    }

    /*
     *配置事务管理
     *
     */
    @Bean(name = "secondaryTransactionManager")
    public DataSourceTransactionManager secondaryTransactionManager() {
        return new DataSourceTransactionManager(getSecondaryDataSource());
    }

    /*
     *创建 secondary session 工厂
     *
     */
    @Bean(name = "secondarySqlSessionFactory")
    public SqlSessionFactory secondarySqlSessionFactory(@Qualifier("secondaryDataSource")DataSource datasource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        // 设置mybatis的xml所在位置
        Resource[] resource = new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/two/*.xml");
        logger.info("获取到的secondary resource[]结果为："+resource);
        bean.setMapperLocations(resource);
        return bean.getObject();
    }

    /*
     * 创建template,管理sqlSession和事务
     * 管理session生命周期，替换defaultSqlSession,是线程安全的
     */
    @Bean("secondarySqlSessionTemplate")
    public SqlSessionTemplate secondarySqlSessionTemplate(@Qualifier("secondarySqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }
}
