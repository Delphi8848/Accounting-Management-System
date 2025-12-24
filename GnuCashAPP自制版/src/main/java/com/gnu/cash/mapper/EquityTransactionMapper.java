package com.gnu.cash.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gnu.cash.pojo.EquityTransaction;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EquityTransactionMapper extends BaseMapper<EquityTransaction> {
    // 自定义方法示例：按日期范围查询权益交易
    // @Select("SELECT * FROM equity_transactions WHERE user_id = #{userId} AND transaction_date BETWEEN #{start} AND #{end}")
    // List<EquityTransaction> findByDateRange(Integer userId, Date start, Date end);
}