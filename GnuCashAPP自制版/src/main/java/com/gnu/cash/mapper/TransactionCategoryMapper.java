package com.gnu.cash.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gnu.cash.pojo.Asset;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

@Mapper
public interface TransactionCategoryMapper {
    // 获取所有交易分类（从五张科目表合并）
    List<Map<String, Object>> getAllCategories(@Param("userId") Integer userId);
    
    // 获取特定类型交易分类
    List<Map<String, Object>> getCategoriesByType(
        @Param("userId") Integer userId,
        @Param("type") String type
    );
    
    // 交易分类统计
    List<Map<String, Object>> getCategoryStatistics(
        @Param("userId") Integer userId,
        @Param("startDate") String startDate,
        @Param("endDate") String endDate
    );
    
    // 检查分类是否存在
    int checkCategoryExists(
        @Param("type") String type,
        @Param("level1") String level1,
        @Param("level2") String level2,
        @Param("level3") String level3,
        @Param("userId") Integer userId
    );
}