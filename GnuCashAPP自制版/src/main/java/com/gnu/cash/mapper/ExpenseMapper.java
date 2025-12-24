package com.gnu.cash.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gnu.cash.pojo.Expense;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface ExpenseMapper extends BaseMapper<Expense> {
    
    @Select("SELECT DISTINCT level2 FROM expenses WHERE level1 = '支出'")
    List<String> selectExpenseCategories();
    
    @Select("SELECT * FROM expenses WHERE level2 = #{category}")
    List<Expense> selectByCategory(String category);
}