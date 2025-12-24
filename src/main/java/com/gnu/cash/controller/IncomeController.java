package com.gnu.cash.controller;

import com.gnu.cash.mapper.IncomeMapper;
import com.gnu.cash.pojo.Income;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/income")
public class IncomeController {
    
    @Autowired
    private IncomeMapper incomeMapper;
    
    @GetMapping
    public List<Income> getAllIncome() {
        return incomeMapper.selectList(null);
    }
    
    @GetMapping("/categories")
    public List<String> getIncomeCategories() {
        return incomeMapper.selectIncomeCategories();
    }
    
    @GetMapping("/{id}")
    public Income getIncomeById(@PathVariable Long id) {
        return incomeMapper.selectById(id);
    }
    
    @PostMapping
    public String createIncome(@RequestBody Income income) {
        int result = incomeMapper.insert(income);
        return result > 0 ? "创建成功" : "创建失败";
    }
}