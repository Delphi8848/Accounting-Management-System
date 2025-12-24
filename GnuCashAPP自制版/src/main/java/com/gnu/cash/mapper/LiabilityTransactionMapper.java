package com.gnu.cash.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gnu.cash.pojo.LiabilityTransaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface LiabilityTransactionMapper extends BaseMapper<LiabilityTransaction> {
    List<LiabilityTransaction> findByLiabilityAndDateRange(
            @Param("userId") Integer userId,
            @Param("liabilityId") Integer liabilityId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    BigDecimal calculateLiabilityBalance(
            @Param("userId") Integer userId,
            @Param("liabilityId") Integer liabilityId);

    List<Map<String, Object>> getLiabilitySummary(@Param("userId") Integer userId);
}