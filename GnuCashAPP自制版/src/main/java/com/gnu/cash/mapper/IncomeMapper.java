package com.gnu.cash.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gnu.cash.pojo.Income;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface IncomeMapper extends BaseMapper<Income> {
    
    @Select("SELECT DISTINCT level2 FROM income WHERE level1 = '收入'")
    List<String> selectIncomeCategories();
}