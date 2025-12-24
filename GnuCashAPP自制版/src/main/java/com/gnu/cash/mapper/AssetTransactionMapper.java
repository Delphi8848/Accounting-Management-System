package com.gnu.cash.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gnu.cash.pojo.AssetTransaction;
        import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AssetTransactionMapper extends BaseMapper<AssetTransaction> {
    // 自定义方法可以根据需要添加
    // 例如：根据用户ID和日期范围查询
    // List<AssetTransaction> findByUserIdAndDateRange(Integer userId, Date startDate, Date endDate);
}