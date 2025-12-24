package com.gnu.cash.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gnu.cash.mapper.LiabilityTransactionMapper;
import com.gnu.cash.pojo.LiabilityTransaction;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/liability-transactions")
public class LiabilityTransactionController {

    @Resource
    private LiabilityTransactionMapper liabilityTransactionMapper;

    @PostMapping
    public Integer createLiabilityTransaction(@RequestBody LiabilityTransaction transaction) {
        liabilityTransactionMapper.insert(transaction);
        return transaction.getId();
    }

    @GetMapping("/{id}")
    public LiabilityTransaction getById(@PathVariable Integer id) {
        return liabilityTransactionMapper.selectById(id);
    }

    @GetMapping("/user/{userId}")
    public List<LiabilityTransaction> getByUser(
            @PathVariable Integer userId,
            @RequestParam(required = false) Integer liabilityId) {
        QueryWrapper<LiabilityTransaction> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        if (liabilityId != null) {
            wrapper.eq("liability_id", liabilityId);
        }
        wrapper.orderByDesc("transaction_date");
        return liabilityTransactionMapper.selectList(wrapper);
    }

    @GetMapping("/balance")
    public BigDecimal getLiabilityBalance(
            @RequestParam Integer userId,
            @RequestParam Integer liabilityId) {
        return liabilityTransactionMapper.calculateLiabilityBalance(userId, liabilityId);
    }

    @GetMapping("/summary")
    public List<Map<String, Object>> getLiabilitySummary(
            @RequestParam Integer userId) {
        return liabilityTransactionMapper.getLiabilitySummary(userId);
    }

    @GetMapping("/page")
    public Page<LiabilityTransaction> getPage(
            @RequestParam Integer userId,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<LiabilityTransaction> page = new Page<>(current, size);
        QueryWrapper<LiabilityTransaction> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
               .orderByDesc("transaction_date");
        return liabilityTransactionMapper.selectPage(page, wrapper);
    }

    @PutMapping("/{id}")
    public Boolean updateLiabilityTransaction(
            @PathVariable Integer id,
            @RequestBody LiabilityTransaction transaction) {
        transaction.setId(id);
        return liabilityTransactionMapper.updateById(transaction) > 0;
    }

    @DeleteMapping("/{id}")
    public Boolean deleteLiabilityTransaction(@PathVariable Integer id) {
        return liabilityTransactionMapper.deleteById(id) > 0;
    }
}