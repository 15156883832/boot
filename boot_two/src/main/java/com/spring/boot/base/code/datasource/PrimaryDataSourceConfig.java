package com.spring.boot.base.code.datasource;

import com.spring.boot.base.BaseController;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@EnableTransactionManagement  //boot事务支持
@Configuration
//mybatis 接口类存放的位置
@MapperScan(basePackages = "com.spring.boot.base.code.first.dao", sqlSessionFactoryRef = "primarySqlSessionFactory")
public class PrimaryDataSourceConfig extends BaseController {

    /*
     *创建数据源
     *
     */
    @Bean(name = "primaryDataSource")  //返回值作为Bean注入到spring ico容器中
    @Primary  //表示这个数据源时默认数据源，一定要加
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource getPrimaryDataSource() {
        return DataSourceBuilder.create().build();//创建数据源，根据jdbc url自动检测驱动程序
    }

    /*
     *配置事务管理
     *
     */
    @Bean(name = "primaryTransactionManager")
    public DataSourceTransactionManager primaryTransactionManager() {
        return new DataSourceTransactionManager(getPrimaryDataSource());
    }

    /*
     *创建 primary session 工厂
     *
     */
    @Bean(name = "primarySqlSessionFactory")
    @Primary// 表示这个数据源是默认数据源
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDataSource") DataSource datasource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        // 设置mybatis的xml所在位置
        Resource[] resource = new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/first/*.xml");
        logger.info("获取到的primary resource[]结果为："+resource);
        bean.setMapperLocations(resource);
        return bean.getObject();
    }

   /*
    * 创建template,管理sqlSession和事务
    * 管理session生命周期，替换defaultSqlSession,是线程安全的
    */
    @Bean("primarySqlSessionTemplate")
    @Primary// 表示这个数据源是默认数据源
    public SqlSessionTemplate primarySqlSessionTemplate(@Qualifier("primarySqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }

}
