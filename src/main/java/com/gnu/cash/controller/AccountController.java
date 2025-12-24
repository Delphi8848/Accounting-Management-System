package com.gnu.cash.controller;

import com.gnu.cash.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountMapper accountMapper;

    /**
     * 获取常用科目列表
     */
    @GetMapping("/common")
    public ResponseEntity<Map<String, Object>> getCommonAccounts() {
        try {
            List<Map<String, Object>> accounts = accountMapper.getCommonAccounts();
            
            // 按科目类型分组
            Map<String, List<Map<String, Object>>> groupedAccounts = accounts.stream()
                .collect(Collectors.groupingBy(account -> (String) account.get("accountType")));
            
            // 构建响应结构
            Map<String, Object> response = new HashMap<>();

            // 为每个科目类型构建完整列表
            response.put("assets", formatAccounts(groupedAccounts.getOrDefault("ASSET", new ArrayList<>())));
            response.put("liabilities", formatAccounts(groupedAccounts.getOrDefault("LIABILITY", new ArrayList<>())));
            response.put("equity", formatAccounts(groupedAccounts.getOrDefault("EQUITY", new ArrayList<>())));
            response.put("income", formatAccounts(groupedAccounts.getOrDefault("INCOME", new ArrayList<>())));
            response.put("expenses", formatAccounts(groupedAccounts.getOrDefault("EXPENSE", new ArrayList<>())));
            

            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("error", "查询科目列表失败");
            error.put("message", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }
    
    /**
     * 格式化科目信息
     */
    private List<Map<String, Object>> formatAccounts(List<Map<String, Object>> accounts) {
        return accounts.stream()
            .map(this::formatSingleAccount)
            .sorted((a, b) -> {
                // 按level1、level2、level3排序
                int level1Compare = getStringValue(a, "level1").compareTo(getStringValue(b, "level1"));
                if (level1Compare != 0) return level1Compare;
                
                int level2Compare = getStringValue(a, "level2").compareTo(getStringValue(b, "level2"));
                if (level2Compare != 0) return level2Compare;
                
                return getStringValue(a, "level3").compareTo(getStringValue(b, "level3"));
            })
            .collect(Collectors.toList());
    }
    
    /**
     * 格式化单个科目
     */
    private Map<String, Object> formatSingleAccount(Map<String, Object> account) {
        Map<String, Object> formatted = new HashMap<>();
        formatted.put("id", account.get("id"));
        formatted.put("accountType", account.get("accountType"));
        formatted.put("name", buildAccountName(account));
        formatted.put("level1", account.get("level1"));
        formatted.put("level2", account.get("level2"));
        formatted.put("level3", account.get("level3"));
        formatted.put("description", account.get("description"));
        formatted.put("hasLevel3", account.get("level3") != null && !((String) account.get("level3")).isEmpty());
        formatted.put("fullPath", buildFullPath(account));
        return formatted;
    }
    
    /**
     * 构建科目完整名称
     */
    private String buildAccountName(Map<String, Object> account) {
        StringBuilder name = new StringBuilder();
        name.append(account.get("level1"));
        
        if (account.get("level2") != null && !((String) account.get("level2")).isEmpty()) {
            name.append(" - ").append(account.get("level2"));
        }
        
        if (account.get("level3") != null && !((String) account.get("level3")).isEmpty()) {
            name.append(" - ").append(account.get("level3"));
        }
        
        return name.toString();
    }
    
    /**
     * 构建科目完整路径（用于前端筛选）
     */
    private String buildFullPath(Map<String, Object> account) {
        return String.format("%s/%s/%s", 
            account.get("level1"), 
            account.get("level2") != null ? account.get("level2") : "",
            account.get("level3") != null ? account.get("level3") : ""
        );
    }
    
    /**
     * 构建统计信息
     */
    private Map<String, Object> buildStatistics(Map<String, List<Map<String, Object>>> groupedAccounts) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("assetCount", groupedAccounts.getOrDefault("ASSET", new ArrayList<>()).size());
        stats.put("liabilityCount", groupedAccounts.getOrDefault("LIABILITY", new ArrayList<>()).size());
        stats.put("equityCount", groupedAccounts.getOrDefault("EQUITY", new ArrayList<>()).size());
        stats.put("incomeCount", groupedAccounts.getOrDefault("INCOME", new ArrayList<>()).size());
        stats.put("expenseCount", groupedAccounts.getOrDefault("EXPENSE", new ArrayList<>()).size());
        
        // 计算有三级科目的比例
        long totalWithLevel3 = groupedAccounts.values().stream()
            .flatMap(List::stream)
            .filter(account -> account.get("level3") != null && !((String) account.get("level3")).isEmpty())
            .count();
        stats.put("level3Ratio", totalWithLevel3);
        
        return stats;
    }
    
    /**
     * 安全获取字符串值
     */
    private String getStringValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return value != null ? value.toString() : "";
    }
}