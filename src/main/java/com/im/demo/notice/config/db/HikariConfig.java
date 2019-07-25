package com.im.demo.notice.config.db;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(value = "com.im.demo.notice")
@EnableTransactionManagement
public class HikariConfig implements EnvironmentAware {

    private Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.MASTER, masterHikariDataSource());
        targetDataSources.put(DataSourceType.SLAVE, slaveHikariDataSource());

        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(masterHikariDataSource());

        return routingDataSource;
    }

    @Bean(destroyMethod = "close")
    @ConfigurationProperties("spring.datasource.master.hikari")
    public HikariDataSource masterHikariDataSource() {
        //HikariDataSource hikariDataSource = new HikariDataSource();
        HikariDataSource hikariDataSource = DataSourceBuilder.create().type(HikariDataSource.class).build();
        return hikariDataSource;
    }

    @Bean(destroyMethod = "close")
    @ConfigurationProperties("spring.datasource.slave.hikari")
    public HikariDataSource slaveHikariDataSource() {
        //HikariDataSource hikariDataSource = new HikariDataSource();
        HikariDataSource hikariDataSource = DataSourceBuilder.create().type(HikariDataSource.class).build();
        return hikariDataSource;
    }

//    @Bean(destroyMethod = "close")
//    public DataSource primaryDataSource() {
//
//        HikariConfig config = new HikariConfig();
//        config.setDataSourceClassName(env.getProperty("spring.datasource.dataSourceClassName"));
//        config.addDataSourceProperty("url", env.getProperty("spring.datasource.url"));
//        config.addDataSourceProperty("user", env.getProperty("spring.datasource.username"));
//        config.addDataSourceProperty("password", env.getProperty("spring.datasource.password"));
//
//        // MySQL 최적화
//        // https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration
//        if ("com.mysql.jdbc.jdbc2.optional.MysqlDataSource".equals(
//                env.getProperty("spring.datasource.dataSourceClassName"))) {
//            config.addDataSourceProperty("cachePrepStmts",
//                    env.getProperty("spring.datasource.cachePrepStmts", "true"));
//            config.addDataSourceProperty("prepStmtCacheSize",
//                    env.getProperty("spring.datasource.prepStmtCacheSize", "250"));
//            config.addDataSourceProperty("prepStmtCacheSqlLimit",
//                    env.getProperty("spring.datasource.prepStmtCacheSqlLimit", "2048"));
//        }
//
//        return new HikariDataSource(config);
//    }

}
