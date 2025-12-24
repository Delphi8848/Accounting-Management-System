package com.gnu.cash.controller;

import com.gnu.cash.mapper.ExpenseMapper;
import com.gnu.cash.pojo.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    
    @Autowired
    private ExpenseMapper expenseMapper;
    
    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseMapper.selectList(null);
    }
    
    @GetMapping("/categories")
    public List<String> getExpenseCategories() {
        return expenseMapper.selectExpenseCategories();
    }
    
    @GetMapping("/category/{category}")
    public List<Expense> getExpensesByCategory(@PathVariable String category) {
        return expenseMapper.selectByCategory(category);
    }
    
    @GetMapping("/{id}")
    public Expense getExpenseById(@PathVariable Long id) {
        return expenseMapper.selectById(id);
    }
    
    @PostMapping
    public String createExpense(@RequestBody Expense expense) {
        int result = expenseMapper.insert(expense);
        return result > 0 ? "创建成功" : "创建失败";
    }
    
    @PutMapping("/{id}")
    public String updateExpense(@PathVariable Long id, @RequestBody Expense expense) {
        expense.setId(id);
        int result = expenseMapper.updateById(expense);
        return result > 0 ? "更新成功" : "更新失败";
    }
    
    @DeleteMapping("/{id}")
    public String deleteExpense(@PathVariable Long id) {
        int result = expenseMapper.deleteById(id);
        return result > 0 ? "删除成功" : "删除失败";
    }
}