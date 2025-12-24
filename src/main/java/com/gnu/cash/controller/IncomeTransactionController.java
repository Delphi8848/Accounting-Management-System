package com.gnu.cash.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gnu.cash.mapper.IncomeTransactionMapper;
import com.gnu.cash.pojo.IncomeTransaction;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/income-transactions")
public class IncomeTransactionController {

    @Resource
    private IncomeTransactionMapper incomeTransactionMapper;

    @PostMapping
    public Integer createIncome(@RequestBody IncomeTransaction transaction) {
        incomeTransactionMapper.insert(transaction);
        return transaction.getId();
    }

    @GetMapping("/{id}")
    public IncomeTransaction getById(@PathVariable Integer id) {
        return incomeTransactionMapper.selectById(id);
    }

    @GetMapping("/user/{userId}")
    public List<IncomeTransaction> getByUser(
            @PathVariable Integer userId,
            @RequestParam(required = false) Integer incomeId) {
        QueryWrapper<IncomeTransaction> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        if (incomeId != null) {
            wrapper.eq("income_id", incomeId);
        }
        wrapper.orderByDesc("transaction_date");
        return incomeTransactionMapper.selectList(wrapper);
    }

    @GetMapping("/monthly-summary")
    public BigDecimal getMonthlySummary(
            @RequestParam Integer userId,
            @RequestParam String yearMonth) {
        return incomeTransactionMapper.sumIncomeByMonth(userId, yearMonth);
    }

    @GetMapping("/stats")
    public List<IncomeTransaction> getStatsByDateRange(
            @RequestParam Integer userId,
            @RequestParam Date startDate,
            @RequestParam Date endDate) {
        return incomeTransactionMapper.findByTypeAndDateRange(
            userId, null, startDate, endDate);
    }

    @GetMapping("/page")
    public Page<IncomeTransaction> getPage(
            @RequestParam Integer userId,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<IncomeTransaction> page = new Page<>(current, size);
        QueryWrapper<IncomeTransaction> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
               .orderByDesc("transaction_date");
        return incomeTransactionMapper.selectPage(page, wrapper);
    }

    @PutMapping("/{id}")
    public Boolean updateIncomeTransaction(
            @PathVariable Integer id,
            @RequestBody IncomeTransaction transaction) {
        transaction.setId(id);
        return incomeTransactionMapper.updateById(transaction) > 0;
    }

    @DeleteMapping("/{id}")
    public Boolean deleteIncomeTransaction(@PathVariable Integer id) {
        return incomeTransactionMapper.deleteById(id) > 0;
    }
}