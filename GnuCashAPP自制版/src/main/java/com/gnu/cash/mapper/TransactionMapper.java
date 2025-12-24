package com.gnu.cash.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.math.BigDecimal;
import java.util.Map;

@Mapper
public interface TransactionMapper {
    
    /**
     * 验证会计恒等式平衡性
     * @param userId 用户ID
     * @return 当前各科目余额和恒等式差额
     */
    Map<String, Object> validateAccountingEquation(@Param("userId") Integer userId);
    
    /**
     * 插入资产交易记录
     */
    int insertAssetTransaction(@Param("transaction") Map<String, Object> transaction);
    
    /**
     * 插入负债交易记录
     */
    int insertLiabilityTransaction(@Param("transaction") Map<String, Object> transaction);
    
    /**
     * 插入权益交易记录
     */
    int insertEquityTransaction(@Param("transaction") Map<String, Object> transaction);
    
    /**
     * 插入收入交易记录
     */
    int insertIncomeTransaction(@Param("transaction") Map<String, Object> transaction);
    
    /**
     * 插入支出交易记录
     */
    int insertExpenseTransaction(@Param("transaction") Map<String, Object> transaction);
}