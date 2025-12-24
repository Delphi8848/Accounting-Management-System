package com.gnu.cash.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gnu.cash.mapper.ExpenseTransactionMapper;
import com.gnu.cash.pojo.ExpenseTransaction;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/expense-transactions")
public class ExpenseTransactionController {

    @Resource
    private ExpenseTransactionMapper expenseTransactionMapper;

    @PostMapping
    public Integer createExpenseTransaction(@RequestBody ExpenseTransaction transaction) {
        expenseTransactionMapper.insert(transaction);
        return transaction.getId();
    }

    @GetMapping("/{id}")
    public ExpenseTransaction getById(@PathVariable Integer id) {
        return expenseTransactionMapper.selectById(id);
    }

    @GetMapping("/user/{userId}")
    public List<ExpenseTransaction> getByUser(
            @PathVariable Integer userId,
            @RequestParam(required = false) Integer expenseId) {
        QueryWrapper<ExpenseTransaction> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        if (expenseId != null) {
            wrapper.eq("expense_id", expenseId);
        }
        wrapper.orderByDesc("transaction_date");
        return expenseTransactionMapper.selectList(wrapper);
    }

    @GetMapping("/stats")
    public List<ExpenseTransaction> getStatsByDateRange(
            @RequestParam Integer userId,
            @RequestParam Date startDate,
            @RequestParam Date endDate) {
        return expenseTransactionMapper.findByCategoryAndDateRange(
            userId, null, startDate, endDate);
    }

    @GetMapping("/page")
    public Page<ExpenseTransaction> getPage(
            @RequestParam Integer userId,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<ExpenseTransaction> page = new Page<>(current, size);
        QueryWrapper<ExpenseTransaction> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
               .orderByDesc("transaction_date");
        return expenseTransactionMapper.selectPage(page, wrapper);
    }

    @PutMapping("/{id}")
    public Boolean updateExpenseTransaction(
            @PathVariable Integer id,
            @RequestBody ExpenseTransaction transaction) {
        transaction.setId(id);
        return expenseTransactionMapper.updateById(transaction) > 0;
    }

    @DeleteMapping("/{id}")
    public Boolean deleteExpenseTransaction(@PathVariable Integer id) {
        return expenseTransactionMapper.deleteById(id) > 0;
    }
}