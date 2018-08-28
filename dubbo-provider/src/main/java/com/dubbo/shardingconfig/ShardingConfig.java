package com.dubbo.shardingconfig;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.StringUtils;
import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.jdbc.core.datasource.ShardingDataSource;
import com.dubbo.algorithm.ModuleDatabaseShardingAlgorithm;
import com.dubbo.algorithm.ModuleTableShardingAlgorithm;
import com.dubbo.connectinfo.ConnectInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

@Configuration
public class ShardingConfig {


    @Bean(name = "dataSource")
    public DataSource shardingDataSource() throws SQLException {
        //分库映射
        Map<String, DataSource> dataSourceMap = new LinkedHashMap<>(2);

        dataSourceMap.put("dataSource0", druidDataSource("test_0"));
        dataSourceMap.put("dataSource1", druidDataSource("test_1"));

        //分库映射规则
        DataSourceRule dataSourceRule = new DataSourceRule(dataSourceMap, "dataSource0");

        //分表映射
        TableRule tableRule = TableRule.builder("user") //逻辑表
                .actualTables(Arrays.asList("user_gz", "user_sz"))  //真实表
                .dataSourceRule(dataSourceRule) //分库映射规则
                .build();

        //分库分表规则
        ShardingRule shardingRule = ShardingRule.builder()
                .tableRules(Collections.singleton(tableRule))
                .dataSourceRule(dataSourceRule)
                .databaseShardingStrategy(new DatabaseShardingStrategy("id", new ModuleDatabaseShardingAlgorithm()))
                .tableShardingStrategy(new TableShardingStrategy("address", new ModuleTableShardingAlgorithm()))
                .build();
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(shardingRule);
        return dataSource;
    }

    private DataSource druidDataSource(String dataSourceName) throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(ConnectInfo.DRIVER);
        druidDataSource.setUrl(String.format(ConnectInfo.URL, dataSourceName));
        druidDataSource.setUsername(ConnectInfo.USERNAME);
        druidDataSource.setPassword(ConnectInfo.PASSWORD);

        /*配置初始化大小、最小、最大*/
        druidDataSource.setInitialSize(1);
        druidDataSource.setMinIdle(1);
        druidDataSource.setMaxActive(20);

        /*配置获取连接等待超时的时间*/
        druidDataSource.setMaxWait(60000);

        /*配置间隔对酒进行一次检测，检测需要关闭的空闲连接*/
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);

        //最小生存时间
        druidDataSource.setMinEvictableIdleTimeMillis(300000);
        druidDataSource.setValidationQuery("SELECT 'x'");
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);

        druidDataSource.setPoolPreparedStatements(false);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(20);

        /* 配置监控统计拦截的filters */
        druidDataSource.setFilters("stat,wall,log4j");
        return druidDataSource;
    }

}
