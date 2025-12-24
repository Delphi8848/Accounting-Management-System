package com.gnu.cash.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gnu.cash.pojo.ExpenseTransaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import java.util.List;

@Mapper
public interface ExpenseTransactionMapper extends BaseMapper<ExpenseTransaction> {
    // 自定义方法：按支出类别和时间范围查询
    List<ExpenseTransaction> findByCategoryAndDateRange(
        @Param("userId") Integer userId,
        @Param("expenseId") Integer expenseId,
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate);
}