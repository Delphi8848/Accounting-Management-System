package com.gnu.cash.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TransactionStatsMapper {
    // 获取用户交易分类统计
    List<Map<String, Object>> getUserTransactionStats(@Param("userId") Integer userId);

    // 获取用户交易时间分布统计
    List<Map<String, Object>> getUserTransactionTimeDistribution(
            @Param("userId") Integer userId,
            @Param("period") String period);
}