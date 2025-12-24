package com.gnu.cash.mapper;

import com.gnu.cash.pojo.ProfitLossItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface ProfitLossMapper {
    
    /**
     * 获取收入类科目发生额
     */
    @Select("SELECT '收入' as category, i.level1, i.level2, i.level3, " +
            "COALESCE(SUM(it.credit_amount) - SUM(it.debit_amount), 0) as amount " +
            "FROM income i " +
            "LEFT JOIN income_transactions it ON i.id = it.income_id " +
            "AND it.transaction_date BETWEEN #{startDate} AND #{endDate} " +
            "AND it.user_id = #{userId} " +
            "GROUP BY i.id, i.level1, i.level2, i.level3 " +
            "HAVING amount != 0 " +
            "ORDER BY i.level1, i.level2, i.level3")
    List<ProfitLossItem> selectRevenueItems(@Param("startDate") String startDate,
                                            @Param("endDate") String endDate,
                                            @Param("userId") Integer userId);
    
    /**
     * 获取支出类科目发生额
     */
    @Select("SELECT '支出' as category, e.level1, e.level2, e.level3, " +
            "COALESCE(SUM(et.debit_amount) - SUM(et.credit_amount), 0) as amount " +
            "FROM expenses e " +
            "LEFT JOIN expense_transactions et ON e.id = et.expense_id " +
            "AND et.transaction_date BETWEEN #{startDate} AND #{endDate} " +
            "AND et.user_id = #{userId} " +
            "GROUP BY e.id, e.level1, e.level2, e.level3 " +
            "HAVING amount != 0 " +
            "ORDER BY e.level1, e.level2, e.level3")
    List<ProfitLossItem> selectExpenseItems(@Param("startDate") String startDate,
                                            @Param("endDate") String endDate,
                                            @Param("userId") Integer userId);
    
    /**
     * 获取收入总额
     */
    @Select("SELECT COALESCE(SUM(credit_amount) - SUM(debit_amount), 0) " +
            "FROM income_transactions " +
            "WHERE transaction_date BETWEEN #{startDate} AND #{endDate} " +
            "AND user_id = #{userId}")
    BigDecimal selectTotalRevenue(@Param("startDate") String startDate,
                                  @Param("endDate") String endDate,
                                  @Param("userId") Integer userId);
    
    /**
     * 获取支出总额
     */
    @Select("SELECT COALESCE(SUM(debit_amount) - SUM(credit_amount), 0) " +
            "FROM expense_transactions " +
            "WHERE transaction_date BETWEEN #{startDate} AND #{endDate} " +
            "AND user_id = #{userId}")
    BigDecimal selectTotalExpense(@Param("startDate") String startDate,
                                 @Param("endDate") String endDate,
                                 @Param("userId") Integer userId);
}