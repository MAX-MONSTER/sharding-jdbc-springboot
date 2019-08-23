package com.think.conf;

import com.think.util.HashUtil;
import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;


import java.util.Collection;

public class MyPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Integer> {

    public String doSharding(Collection<String> collection, PreciseShardingValue<Integer> preciseShardingValue) {
        for (String each : collection) {
            int hash = HashUtil.rsHash(String.valueOf(preciseShardingValue.getValue()));
            if (each.endsWith(hash % 5 + "")) {
                return each;
            }
        }
        throw new UnsupportedOperationException();
    }
}
