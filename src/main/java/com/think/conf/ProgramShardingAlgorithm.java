//package com.think.conf;
//
//import java.util.Collection;
//import java.util.LinkedHashSet;
//
//import com.google.common.collect.Range;
//import com.think.util.HashUtil;
//
///**
// * table 分片算法
// *
// * @author donghuating
// *
// */
//public class ProgramShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Integer> {
//
//    public String doEqualSharding(Collection<String> availableTargetNames, ShardingValue<Integer> shardingValue) {
//        for (String each : availableTargetNames) {
//            int hash = HashUtil.rsHash(String.valueOf(shardingValue.getValue()));
//            if (each.endsWith(hash % 5 + "")) {
//                return each;
//            }
//        }
//        throw new UnsupportedOperationException();
//    }
//
//    public Collection<String> doInSharding(Collection<String> availableTargetNames,
//            ShardingValue<Integer> shardingValue) {
//        Collection<String> result = new LinkedHashSet<String>(availableTargetNames.size());
//        Collection<Integer> values = shardingValue.getValues();
//        for (Integer value : values) {
//            for (String tableNames : availableTargetNames) {
//                if (tableNames.endsWith(value % 2 + "")) {
//                    result.add(tableNames);
//                }
//            }
//        }
//        return result;
//    }
//
//    public Collection<String> doBetweenSharding(Collection<String> availableTargetNames,
//            ShardingValue<Integer> shardingValue) {
//        Collection<String> result = new LinkedHashSet<String>(availableTargetNames.size());
//        Range<Integer> range = shardingValue.getValueRange();
//        for (Integer i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
//            for (String each : availableTargetNames) {
//                if (each.endsWith(i % 2 + "")) {
//                    result.add(each);
//                }
//            }
//        }
//        return result;
//    }
//
//}
