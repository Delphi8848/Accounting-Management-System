package com.gnu.cash.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gnu.cash.mapper.BalanceMapper;
import com.gnu.cash.pojo.AssetTransaction;
import com.gnu.cash.mapper.AssetTransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/balance")
public class BalanceController {

    @Autowired
    private BalanceMapper balanceMapper;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getBalanceOverview(
            @RequestParam Integer userId,
            @RequestParam(defaultValue = "5") Integer recentLimit) {
        
        try {
            // 获取各类总额
            BigDecimal totalAssets = balanceMapper.getTotalAssets(userId);
            BigDecimal totalLiabilities = balanceMapper.getTotalLiabilities(userId);
            BigDecimal totalEquity = balanceMapper.getTotalEquity(userId);
            BigDecimal netWorth = totalAssets.subtract(totalLiabilities);
            
            // 获取明细数据
            List<Map<String, Object>> assets = balanceMapper.getAssetDetails(userId);
            List<Map<String, Object>> liabilities = balanceMapper.getLiabilityDetails(userId);
            List<Map<String, Object>> recentTransactions = balanceMapper.getRecentTransactions(userId, recentLimit);
            
            // 构建响应
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", new HashMap<String, Object>() {{
                put("summary", new HashMap<String, Object>() {{
                    put("totalAssets", totalAssets);
                    put("totalLiabilities", totalLiabilities);
                    put("totalEquity", totalEquity);
                    put("netWorth", netWorth);
                }});
                put("assets", assets);
                put("liabilities", liabilities);
                put("recentTransactions", recentTransactions);
            }});
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new HashMap<String, Object>() {{
                put("success", false);
                put("error", "获取余额信息失败: " + e.getMessage());
            }});
        }
    }
}