package com.gnu.cash.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
@Mapper
public interface BalanceMapper {
    // 获取用户资产总额
    BigDecimal getTotalAssets(@Param("userId") Integer userId);
    
    // 获取用户负债总额
    BigDecimal getTotalLiabilities(@Param("userId") Integer userId);
    
    // 获取用户权益总额
    BigDecimal getTotalEquity(@Param("userId") Integer userId);
    
    // 获取各类资产明细
    List<Map<String, Object>> getAssetDetails(@Param("userId") Integer userId);
    
    // 获取各类负债明细
    List<Map<String, Object>> getLiabilityDetails(@Param("userId") Integer userId);
    
    // 获取最近交易记录
    List<Map<String, Object>> getRecentTransactions(
        @Param("userId") Integer userId,
        @Param("limit") Integer limit);
}