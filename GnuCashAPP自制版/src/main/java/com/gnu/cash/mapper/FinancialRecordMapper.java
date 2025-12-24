package com.gnu.cash.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface FinancialRecordMapper {
    
    List<Map<String, Object>> getAllFinancialRecords(Map<String, Object> params);
    
    int countAllFinancialRecords(Map<String, Object> params);
    
    Map<String, Object> getFinancialRecordById(@Param("recordType") String recordType, @Param("id") Long id);
    
    Map<String, Object> findPairedFinancialRecord(
        @Param("userId") Integer userId,
        @Param("recordDate") String recordDate,
        @Param("description") String description,
        @Param("debitValue") BigDecimal debitValue,
        @Param("creditValue") BigDecimal creditValue,
        @Param("excludeRecordType") String excludeRecordType
    );
    
    Map<String, Object> getAccountInfo(@Param("accountType") String accountType, @Param("accountId") Integer accountId);
    
    List<Map<String, Object>> getFinancialStatsByCategory(Map<String, Object> params);
    
    List<Map<String, Object>> getFinancialStatsByPeriod(Map<String, Object> params);
}