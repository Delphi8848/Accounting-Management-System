package com.gnu.cash.controller;
import com.gnu.cash.mapper.AccountApplicationMapper;
import com.gnu.cash.mapper.CategoryMapper;
import com.gnu.cash.mapper.ReportMapper;
import com.gnu.cash.pojo.AccountApplication;
import com.gnu.cash.pojo.BalanceSheetItem;
import com.gnu.cash.pojo.BalanceSheetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/reports")
public class ReportController {
    
    @Autowired
    private ReportMapper reportMapper;
    
    @GetMapping("/balance-sheet")
    public ResponseEntity<BalanceSheetVO> getBalanceSheet(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate reportDate,
            @RequestParam Integer userId) {
        
        try {
            String dateStr = reportDate.format(DateTimeFormatter.ISO_DATE);
            BalanceSheetVO balanceSheet = new BalanceSheetVO();
            balanceSheet.setReportDate(dateStr);
            
            // 1. 获取资产项
            List<BalanceSheetItem> assets = reportMapper.selectAssetBalances(dateStr, userId);
            balanceSheet.setAssets(assets);
            BigDecimal assetsTotal = calculateTotal(assets);
            balanceSheet.setAssetsTotal(assetsTotal);
            
            // 2. 获取负债项
            List<BalanceSheetItem> liabilities = reportMapper.selectLiabilityBalances(dateStr, userId);
            balanceSheet.setLiabilities(liabilities);
            BigDecimal liabilitiesTotal = calculateTotal(liabilities);
            balanceSheet.setLiabilitiesTotal(liabilitiesTotal);
            
            // 3. 计算权益项（包含净利润）
            List<BalanceSheetItem> equityItems = buildEquityItems(dateStr, userId);
            balanceSheet.setEquity(equityItems);
            BigDecimal equityTotal = calculateTotal(equityItems);
            balanceSheet.setEquityTotal(equityTotal);
            
            // 4. 检查资产负债表平衡
            BigDecimal liabilitiesAndEquityTotal = liabilitiesTotal.add(equityTotal);
            balanceSheet.setIsBalanced(assetsTotal.compareTo(liabilitiesAndEquityTotal) == 0);
            
            return ResponseEntity.ok(balanceSheet);
            
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 构建权益项目（期初权益 + 净利润）
     */
    private List<BalanceSheetItem> buildEquityItems(String reportDate, Integer userId) {
        List<BalanceSheetItem> equityItems = new ArrayList<>();
        
        // 添加期初权益
        List<BalanceSheetItem> baseEquity = reportMapper.selectEquityBalances(reportDate, userId);
        equityItems.addAll(baseEquity);
        
        // 计算净利润并添加到权益
        BigDecimal totalIncome = reportMapper.selectTotalIncome(reportDate, userId);
        BigDecimal totalExpense = reportMapper.selectTotalExpense(reportDate, userId);
        BigDecimal netIncome = totalIncome.subtract(totalExpense);
        
        if (netIncome.compareTo(BigDecimal.ZERO) != 0) {
            BalanceSheetItem netIncomeItem = new BalanceSheetItem(
                "权益", "净利润", "当期损益", null, netIncome
            );
            equityItems.add(netIncomeItem);
        }
        
        return equityItems;
    }
    
    /**
     * 计算列表总额
     */
    private BigDecimal calculateTotal(List<BalanceSheetItem> items) {
        return items.stream()
                .map(BalanceSheetItem::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }
}