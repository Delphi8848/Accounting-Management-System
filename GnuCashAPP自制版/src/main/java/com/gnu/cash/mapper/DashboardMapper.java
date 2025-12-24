package com.gnu.cash.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Map;

@Mapper
public interface DashboardMapper {
    
    /**
     * 获取完整的财务概览数据
     * @param userId 用户ID
     * @param month 月份
     * @param year 年份
     * @return 嵌套的财务数据
     */
    Map<String, Object> getFinancialSummary(
        @Param("userId") Integer userId,
        @Param("month") Integer month,
        @Param("year") Integer year
    );
}