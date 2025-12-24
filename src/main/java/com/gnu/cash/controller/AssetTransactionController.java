package com.gnu.cash.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gnu.cash.pojo.AssetTransaction;
import com.gnu.cash.mapper.AssetTransactionMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/asset-transactions")
public class AssetTransactionController {

    @Resource
    private AssetTransactionMapper assetTransactionMapper;

    @PostMapping
    public Integer create(@RequestBody AssetTransaction transaction) {
        assetTransactionMapper.insert(transaction);
        return transaction.getId();
    }

    @GetMapping("/{id}")
    public AssetTransaction getById(@PathVariable Integer id) {
        return assetTransactionMapper.selectById(id);
    }

    @GetMapping
    public List<AssetTransaction> listByUser(@RequestParam Integer userId) {
        QueryWrapper<AssetTransaction> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                   .orderByDesc("transaction_date");
        return assetTransactionMapper.selectList(queryWrapper);
    }

    @GetMapping("/page")
    public Page<AssetTransaction> pageByUser(
            @RequestParam Integer userId,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<AssetTransaction> page = new Page<>(current, size);
        QueryWrapper<AssetTransaction> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                   .orderByDesc("transaction_date");
        return assetTransactionMapper.selectPage(page, queryWrapper);
    }

    @PutMapping("/{id}")
    public Boolean update(@PathVariable Integer id, @RequestBody AssetTransaction transaction) {
        transaction.setId(id);
        return assetTransactionMapper.updateById(transaction) > 0;
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return assetTransactionMapper.deleteById(id) > 0;
    }
}