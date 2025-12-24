package com.gnu.cash.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gnu.cash.pojo.CashTransaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CashFlowMapper {
    
    /**
     * 获取现金账户的期初余额
     */
    @Select("SELECT COALESCE(SUM(debit_amount) - SUM(credit_amount), 0) " +
            "FROM asset_transactions at " +
            "JOIN assets a ON at.asset_id = a.id " +
            "WHERE at.user_id = #{userId} " +
            "AND a.level3 = '现金' " +
            "AND at.transaction_date < #{startDate}")
    BigDecimal selectBeginningCashBalance(@Param("startDate") String startDate,
                                          @Param("userId") Integer userId);
    
    /**
     * 获取现金账户的期末余额
     */
    @Select("SELECT COALESCE(SUM(debit_amount) - SUM(credit_amount), 0) " +
            "FROM asset_transactions at " +
            "JOIN assets a ON at.asset_id = a.id " +
            "WHERE at.user_id = #{userId} " +
            "AND a.level3 = '现金' " +
            "AND at.transaction_date <= #{endDate}")
    BigDecimal selectEndingCashBalance(@Param("endDate") String endDate, 
                                      @Param("userId") Integer userId);
    
    /**
     * 获取期间内所有现金交易流水
     */
    @Select("SELECT at.transaction_date, at.description, at.debit_amount, at.credit_amount, " +
            "a.level1, a.level2, a.level3 " +
            "FROM asset_transactions at " +
            "JOIN assets a ON at.asset_id = a.id " +
            "WHERE at.user_id = #{userId} " +
            "AND a.level3 = '现金' " +
            "AND at.transaction_date BETWEEN #{startDate} AND #{endDate} " +
            "ORDER BY at.transaction_date")
    List<CashTransaction> selectCashTransactions(@Param("startDate") String startDate,
                                                 @Param("endDate") String endDate,
                                                 @Param("userId") Integer userId);
    
    /**
     * 获取销售收入相关的现金流入（简化处理）
     */
    @Select("SELECT COALESCE(SUM(credit_amount), 0) " +
            "FROM income_transactions it " +
            "JOIN income i ON it.income_id = i.id " +
            "WHERE it.user_id = #{userId} " +
            "AND it.transaction_date BETWEEN #{startDate} AND #{endDate} " +
            "AND i.level1 = '收入' AND i.level2 = '薪金'")
    BigDecimal selectSalaryCashInflow(@Param("startDate") String startDate,
                                     @Param("endDate") String endDate,
                                     @Param("userId") Integer userId);
}