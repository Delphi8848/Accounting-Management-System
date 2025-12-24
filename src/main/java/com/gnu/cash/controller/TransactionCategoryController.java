package com.gnu.cash.controller;

import com.gnu.cash.mapper.AssetMapper;
import com.gnu.cash.mapper.TransactionCategoryMapper;
import com.gnu.cash.pojo.Asset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transaction-categories")
public class TransactionCategoryController {

    @Autowired
    private TransactionCategoryMapper categoryMapper;

    // 获取所有交易分类
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllCategories(
            @RequestParam Integer userId) {
        try {
            List<Map<String, Object>> categories = categoryMapper.getAllCategories(userId);
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("success", true);
                put("data", categories);
            }});
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new HashMap<String, Object>() {{
                put("success", false);
                put("error", "获取分类列表失败: " + e.getMessage());
            }});
        }
    }

    // 获取特定类型交易分类
    @GetMapping("/{type}")
    public ResponseEntity<Map<String, Object>> getCategoriesByType(
            @PathVariable String type,
            @RequestParam Integer userId) {
        try {
            // 验证类型有效性
            if (!Arrays.asList("ASSET", "LIABILITY", "EQUITY", "INCOME", "EXPENSE").contains(type)) {
                return ResponseEntity.badRequest().body(new HashMap<String, Object>() {{
                    put("success", false);
                    put("error", "无效的分类类型");
                }});
            }

            List<Map<String, Object>> categories = categoryMapper.getCategoriesByType(userId, type);
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("success", true);
                put("data", categories);
            }});
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new HashMap<String, Object>() {{
                put("success", false);
                put("error", "获取分类列表失败: " + e.getMessage());
            }});
        }
    }

    // 交易分类统计
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getCategoryStatistics(
            @RequestParam Integer userId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        try {
            List<Map<String, Object>> statistics = categoryMapper.getCategoryStatistics(
                userId, startDate, endDate);
            
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("success", true);
                put("data", statistics);
            }});
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new HashMap<String, Object>() {{
                put("success", false);
                put("error", "获取统计信息失败: " + e.getMessage());
            }});
        }
    }

    // 检查分类是否存在
    @GetMapping("/exists")
    public ResponseEntity<Map<String, Object>> checkCategoryExists(
            @RequestParam String type,
            @RequestParam String level1,
            @RequestParam String level2,
            @RequestParam(required = false) String level3,
            @RequestParam Integer userId) {
        try {
            int count = categoryMapper.checkCategoryExists(type, level1, level2, level3, userId);
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("success", true);
                put("exists", count > 0);
            }});
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new HashMap<String, Object>() {{
                put("success", false);
                put("error", "检查分类存在性失败: " + e.getMessage());
            }});
        }
    }
}