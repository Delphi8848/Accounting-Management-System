package com.gnu.cash.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gnu.cash.mapper.EquityTransactionMapper;
import com.gnu.cash.pojo.EquityTransaction;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/equity-transactions")
public class EquityTransactionController {

    @Resource
    private EquityTransactionMapper equityTransactionMapper;

    @PostMapping
    public Integer create(@RequestBody EquityTransaction transaction) {
        equityTransactionMapper.insert(transaction);
        return transaction.getId();
    }

    @GetMapping("/{id}")
    public EquityTransaction getById(@PathVariable Integer id) {
        return equityTransactionMapper.selectById(id);
    }

    @GetMapping("/user/{userId}")
    public List<EquityTransaction> getByUser(@PathVariable Integer userId) {
        QueryWrapper<EquityTransaction> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
               .orderByDesc("transaction_date");
        return equityTransactionMapper.selectList(wrapper);
    }

    @GetMapping("/page")
    public Page<EquityTransaction> getPage(
            @RequestParam Integer userId,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<EquityTransaction> page = new Page<>(current, size);
        QueryWrapper<EquityTransaction> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
               .orderByDesc("transaction_date");
        return equityTransactionMapper.selectPage(page, wrapper);
    }

    @PutMapping("/{id}")
    public Boolean update(@PathVariable Integer id, @RequestBody EquityTransaction transaction) {
        transaction.setId(id);
        return equityTransactionMapper.updateById(transaction) > 0;
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return equityTransactionMapper.deleteById(id) > 0;
    }
}