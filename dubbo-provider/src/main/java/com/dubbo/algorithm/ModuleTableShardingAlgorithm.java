package com.dubbo.algorithm;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;

import java.util.Collection;

public class ModuleTableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<String> {
    @Override
    public String doEqualSharding(Collection<String> tableNames, ShardingValue<String> shardingValue) {
        for (String each : tableNames)
            if(each.endsWith(shardingValue.getValue()))
                return each;
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<String> doInSharding(Collection collection, ShardingValue shardingValue) {
        return null;
    }

    @Override
    public Collection<String> doBetweenSharding(Collection collection, ShardingValue shardingValue) {
        return null;
    }
}
