package com.gnu.cash.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface AccountMapper {
    
    /**
     * 获取所有科目列表（包含五大类科目）
     * @return 科目列表
     */
    List<Map<String, Object>> getCommonAccounts();
}