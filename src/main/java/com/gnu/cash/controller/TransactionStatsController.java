package com.gnu.cash.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gnu.cash.mapper.TransactionStatsMapper;
import com.gnu.cash.pojo.AssetTransaction;
import com.gnu.cash.mapper.AssetTransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transaction-stats")
public class TransactionStatsController {

    @Autowired
    private TransactionStatsMapper statsMapper;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getUserTransactionStats(
            @RequestParam Integer userId,
            @RequestParam(required = false) String period) {

        try {
            // 获取分类统计
            List<Map<String, Object>> categoryStats = statsMapper.getUserTransactionStats(userId);

            // 获取时间分布统计
            List<Map<String, Object>> timeDistribution = period != null ?
                    statsMapper.getUserTransactionTimeDistribution(userId, period) : null;

            // 处理数据为前端友好格式
            Map<String, Object> responseData = processStats(categoryStats, timeDistribution);

            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("success", true);
                put("data", responseData);
            }});
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new HashMap<String, Object>() {{
                put("success", false);
                put("error", "获取交易统计失败: " + e.getMessage());
            }});
        }
    }

    private Map<String, Object> processStats(
            List<Map<String, Object>> categoryStats,
            List<Map<String, Object>> timeDistribution) {

        Map<String, Object> result = new HashMap<>();

        // 1. 分类统计处理
        Map<String, List<Map<String, Object>>> statsByType = categoryStats.stream()
                .collect(Collectors.groupingBy(stat -> ((String) stat.get("type")).toLowerCase()));

        // 2. 计算收支总额
        double totalIncome = categoryStats.stream()
                .filter(stat -> "INCOME".equals(stat.get("type")))
                .mapToDouble(stat -> ((Number) stat.get("amount")).doubleValue())
                .sum();

        double totalExpense = categoryStats.stream()
                .filter(stat -> "EXPENSE".equals(stat.get("type")))
                .mapToDouble(stat -> ((Number) stat.get("amount")).doubleValue())
                .sum();

        // 3. 构建响应数据
        result.put("summary", new HashMap<String, Object>() {{
            put("totalIncome", totalIncome);
            put("totalExpense", totalExpense);
            put("netAmount", totalIncome - totalExpense);
        }});

        result.put("categoryStats", statsByType);

        if (timeDistribution != null) {
            result.put("timeDistribution", timeDistribution);
        }

        return result;
    }
}