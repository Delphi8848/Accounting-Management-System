package com.gnu.cash.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gnu.cash.mapper.BalanceMapper;
import com.gnu.cash.mapper.ProfitLossMapper;
import com.gnu.cash.pojo.AssetTransaction;
import com.gnu.cash.mapper.AssetTransactionMapper;
import com.gnu.cash.pojo.ProfitLossItem;
import com.gnu.cash.pojo.ProfitLossVO;
import com.gnu.cash.pojo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ProfitLossController {
    
    @Autowired
    private ProfitLossMapper profitLossMapper;
    
    @GetMapping("/profit-loss")
    public ResponseResult<ProfitLossVO> getProfitLossStatement(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam Integer userId) {
        
        try {
            String startStr = startDate.format(DateTimeFormatter.ISO_DATE);
            String endStr = endDate.format(DateTimeFormatter.ISO_DATE);
            
            ProfitLossVO profitLoss = new ProfitLossVO();
            profitLoss.setPeriod(startStr + " 至 " + endStr);
            
            // 1. 获取收入项
            List<ProfitLossItem> revenues = profitLossMapper.selectRevenueItems(startStr, endStr, userId);
            profitLoss.setRevenues(revenues);
            BigDecimal totalRevenue = calculateTotal(revenues);
            profitLoss.setTotalRevenue(totalRevenue);
            
            // 2. 获取支出项
            List<ProfitLossItem> expenses = profitLossMapper.selectExpenseItems(startStr, endStr, userId);
            profitLoss.setExpenses(expenses);
            BigDecimal totalExpense = calculateTotal(expenses);
            profitLoss.setTotalExpense(totalExpense);
            
            // 3. 计算净利润
            BigDecimal netIncome = totalRevenue.subtract(totalExpense);
            profitLoss.setNetIncome(netIncome);
            
            return ResponseResult.success(profitLoss);
            
        } catch (Exception e) {
            return ResponseResult.error("生成利润表失败: " + e.getMessage());
        }
    }
    
    /**
     * 计算列表总额
     */
    private BigDecimal calculateTotal(List<ProfitLossItem> items) {
        return items.stream()
                .map(ProfitLossItem::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }
}