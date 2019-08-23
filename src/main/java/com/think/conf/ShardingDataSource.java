package com.think.conf;

import com.alibaba.druid.pool.DruidDataSource;
import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.InlineShardingStrategyConfiguration;
import io.shardingjdbc.core.api.config.strategy.StandardShardingStrategyConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/***
 * sharding-jdbc 配置数据源和分库分表规则
 *
 * @author donghuating
 *
 */
@Component
public class ShardingDataSource {

    @Autowired
    private DruidDataSource primaryDataSource;


    @Autowired
    @Qualifier("secondaryDataSource")
    private DruidDataSource secondaryDataSource;

    private DataSource shardingDataSource;

    @PostConstruct
    public void init() {
        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();
        DruidDataSource dataSource0 = primaryDataSource;
        dataSourceMap.put("ds0", dataSource0);
        DruidDataSource dataSource1 = secondaryDataSource;
        dataSourceMap.put("ds1", dataSource1);
        TableRuleConfiguration tableRuleConfig = userRuleConfig();
        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.setDefaultDataSourceName("ds0");
        shardingRuleConfig.setBindingTableGroups(Arrays.asList("han_user"));
        shardingRuleConfig.getTableRuleConfigs().add(tableRuleConfig);
        Properties p = new Properties();
        p.setProperty("sql.show", Boolean.TRUE.toString());
        // 获取数据源对象
        try {
            shardingDataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new ConcurrentHashMap(), p);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * user表分片策略
     *
     * @return
     */
    private TableRuleConfiguration userRuleConfig() {
        // 配置user表规则
        TableRuleConfiguration tableRuleConfig = new TableRuleConfiguration();
        tableRuleConfig.setLogicTable("t_order");
        tableRuleConfig.setActualDataNodes("ds${0..1}.t_order${0..4}");
//        tableRuleConfig.setKeyGeneratorColumnName("id");
        // 配置分库 + 分表策略
        tableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds${user_id % 2}"));
//        tableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "t_order{order_id % 2}"));
        // 自定义的分片算法实现
        StandardShardingStrategyConfiguration standardStrategy = new StandardShardingStrategyConfiguration("user_id",MyPreciseShardingAlgorithm.class.getName());
//        tableRuleConfig.setDatabaseShardingStrategyConfig(standardStrategy);
        tableRuleConfig.setTableShardingStrategyConfig(standardStrategy);
        return tableRuleConfig;
    }

    public DataSource getShardingDataSource() {
        return shardingDataSource;
    }
}
