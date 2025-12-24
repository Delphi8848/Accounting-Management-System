package com.gnu.cash.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gnu.cash.pojo.IncomeTransaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Mapper
public interface IncomeTransactionMapper extends BaseMapper<IncomeTransaction> {
    // 自定义方法：按收入类型和时间范围统计
    List<IncomeTransaction> findByTypeAndDateRange(
        @Param("userId") Integer userId,
        @Param("incomeId") Integer incomeId,
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate);

    // 按月统计收入总额
    BigDecimal sumIncomeByMonth(
        @Param("userId") Integer userId,
        @Param("yearMonth") String yearMonth);
}