package com.gnu.cash.mapper;

import com.gnu.cash.pojo.BalanceSheetItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
@Mapper
public interface ReportMapper {

    // 资产余额查询
    @Select("SELECT '资产' as category, a.level1, a.level2, a.level3, " +
            "COALESCE(SUM(at.debit_amount) - SUM(at.credit_amount), 0) as balance " +
            "FROM assets a " +
            "LEFT JOIN asset_transactions at ON a.id = at.asset_id " +
            "AND at.transaction_date <= #{reportDate} AND at.user_id = #{userId} " +
            "GROUP BY a.id, a.level1, a.level2, a.level3 " +
            "HAVING balance != 0")
    List<BalanceSheetItem> selectAssetBalances(@Param("reportDate") String reportDate,
                                               @Param("userId") Integer userId);

    // 负债余额查询
    @Select("SELECT '负债' as category, l.level1, l.level2, l.level3, " +
            "COALESCE(SUM(lt.credit_amount) - SUM(lt.debit_amount), 0) as balance " +
            "FROM liabilities l " +
            "LEFT JOIN liability_transactions lt ON l.id = lt.liability_id " +
            "AND lt.transaction_date <= #{reportDate} AND lt.user_id = #{userId} " +
            "GROUP BY l.id, l.level1, l.level2, l.level3 " +
            "HAVING balance != 0")
    List<BalanceSheetItem> selectLiabilityBalances(@Param("reportDate") String reportDate,
                                                   @Param("userId") Integer userId);

    // 权益余额查询
    @Select("SELECT '权益' as category, e.level1, e.level2, e.level3, " +
            "COALESCE(SUM(et.credit_amount) - SUM(et.debit_amount), 0) as balance " +
            "FROM equity e " +
            "LEFT JOIN equity_transactions et ON e.id = et.equity_id " +
            "AND et.transaction_date <= #{reportDate} AND et.user_id = #{userId} " +
            "GROUP BY e.id, e.level1, e.level2, e.level3 " +
            "HAVING balance != 0")
    List<BalanceSheetItem> selectEquityBalances(@Param("reportDate") String reportDate,
                                                @Param("userId") Integer userId);

    // 收入总额
    @Select("SELECT COALESCE(SUM(credit_amount) - SUM(debit_amount), 0) " +
            "FROM income_transactions " +
            "WHERE transaction_date <= #{reportDate} AND user_id = #{userId}")
    BigDecimal selectTotalIncome(@Param("reportDate") String reportDate,
                                 @Param("userId") Integer userId);

    // 支出总额
    @Select("SELECT COALESCE(SUM(debit_amount) - SUM(credit_amount), 0) " +
            "FROM expense_transactions " +
            "WHERE transaction_date <= #{reportDate} AND user_id = #{userId}")
    BigDecimal selectTotalExpense(@Param("reportDate") String reportDate,
                                  @Param("userId") Integer userId);
}