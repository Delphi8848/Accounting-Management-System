package com.gnu.cash.controller;
import com.gnu.cash.mapper.AccountApplicationMapper;
import com.gnu.cash.mapper.CashFlowMapper;
import com.gnu.cash.mapper.CategoryMapper;
import com.gnu.cash.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class CashFlowController {
    
    @Autowired
    private CashFlowMapper cashFlowMapper;
    
    @GetMapping("/cash-flow")
    public ResponseResult<CashFlowVO> getCashFlowStatement(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam Integer userId) {
        
        try {
            String startStr = startDate.format(DateTimeFormatter.ISO_DATE);
            String endStr = endDate.format(DateTimeFormatter.ISO_DATE);
            
            CashFlowVO cashFlow = new CashFlowVO();
            cashFlow.setPeriod(startStr + " 至 " + endStr);
            
            // 1. 计算现金期初、期末余额
            BigDecimal beginningCash = cashFlowMapper.selectBeginningCashBalance(startStr, userId);
            BigDecimal endingCash = cashFlowMapper.selectEndingCashBalance(endStr, userId);
            cashFlow.setCashAtBeginning(beginningCash);
            cashFlow.setCashAtEnd(endingCash);
            cashFlow.setNetCashIncrease(endingCash.subtract(beginningCash));
            
            // 2. 分析现金交易流水并分类
            analyzeCashTransactions(cashFlow, startStr, endStr, userId);
            
            // 3. 计算各类活动净现金流
            calculateNetCashFlows(cashFlow);
            
            return ResponseResult.success(cashFlow);
            
        } catch (Exception e) {
            return ResponseResult.error("生成现金流量表失败: " + e.getMessage());
        }
    }
    
    /**
     * 分析现金交易并分类到经营活动、投资活动、筹资活动
     */
    private void analyzeCashTransactions(CashFlowVO cashFlow, String startDate, String endDate, Integer userId) {
        List<CashTransaction> transactions = cashFlowMapper.selectCashTransactions(startDate, endDate, userId);
        
        for (CashTransaction transaction : transactions) {
            String description = transaction.getDescription();
            BigDecimal amount = BigDecimal.ZERO;
            String flowDirection = "";
            
            // 确定金额和流向
            if (transaction.getDebitAmount().compareTo(BigDecimal.ZERO) > 0) {
                amount = transaction.getDebitAmount();
                flowDirection = "inflow";
            } else if (transaction.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
                amount = transaction.getCreditAmount();
                flowDirection = "outflow";
            }
            
            if (amount.compareTo(BigDecimal.ZERO) == 0) continue;
            
            // 根据交易描述分类
            String activityType = classifyTransaction(description, amount, flowDirection);
            CashFlowItem item = new CashFlowItem(activityType, description, amount, flowDirection);
            
            switch (activityType) {
                case "经营":
                    cashFlow.getOperatingActivities().add(item);
                    break;
                case "投资":
                    cashFlow.getInvestingActivities().add(item);
                    break;
                case "筹资":
                    cashFlow.getFinancingActivities().add(item);
                    break;
            }
        }
        
        // 补充一些通过其他方式识别的现金流（如工资收入）
        supplementCashFlows(cashFlow, startDate, endDate, userId);
    }
    
    /**
     * 根据交易描述判断活动类型
     */
    private String classifyTransaction(String description, BigDecimal amount, String flowDirection) {
        String descLower = description.toLowerCase();
        
        // 经营活动（日常运营）
        if (descLower.contains("工资") || descLower.contains("薪金") || 
            descLower.contains("购物") || descLower.contains("消费") ||
            descLower.contains("购买") || descLower.contains("支付") ||
            descLower.contains("收入") || descLower.contains("销售")) {
            return "经营";
        }
        
        // 投资活动（长期资产）
        if (descLower.contains("设备") || descLower.contains("房产") || 
            descLower.contains("投资") || descLower.contains("证券")) {
            return "投资";
        }
        
        // 筹资活动（资本和负债）
        if (descLower.contains("借款") || descLower.contains("贷款") || 
            descLower.contains("投资") || descLower.contains("股东") ||
            descLower.contains("还款") || descLower.contains("利息")) {
            return "筹资";
        }
        
        // 默认作为经营活动
        return "经营";
    }
    
    /**
     * 补充其他现金流项目
     */
    private void supplementCashFlows(CashFlowVO cashFlow, String startDate, String endDate, Integer userId) {
        // 补充工资收入（如果还没有在现金交易中体现）
        BigDecimal salaryInflow = cashFlowMapper.selectSalaryCashInflow(startDate, endDate, userId);
        if (salaryInflow.compareTo(BigDecimal.ZERO) > 0) {
            boolean exists = cashFlow.getOperatingActivities().stream()
                .anyMatch(item -> item.getDescription().contains("工资") || item.getDescription().contains("薪金"));
            if (!exists) {
                CashFlowItem salaryItem = new CashFlowItem("经营", "工资薪金收入", salaryInflow, "inflow");
                cashFlow.getOperatingActivities().add(salaryItem);
            }
        }
    }
    
    /**
     * 计算各类活动净现金流
     */
    private void calculateNetCashFlows(CashFlowVO cashFlow) {
        // 经营活动净现金流
        BigDecimal operatingNet = calculateActivityNetCash(cashFlow.getOperatingActivities());
        cashFlow.setOperatingNetCash(operatingNet);
        
        // 投资活动净现金流
        BigDecimal investingNet = calculateActivityNetCash(cashFlow.getInvestingActivities());
        cashFlow.setInvestingNetCash(investingNet);
        
        // 筹资活动净现金流
        BigDecimal financingNet = calculateActivityNetCash(cashFlow.getFinancingActivities());
        cashFlow.setFinancingNetCash(financingNet);
        
        // 验证：经营 + 投资 + 筹资 = 现金净增加额
        BigDecimal calculatedIncrease = operatingNet.add(investingNet).add(financingNet);
        BigDecimal actualIncrease = cashFlow.getNetCashIncrease();
        
        // 如果有差异，可能是分类不完整导致的
        if (calculatedIncrease.compareTo(actualIncrease) != 0) {
            // 可以将差异调整到经营活动中
            BigDecimal adjustment = actualIncrease.subtract(calculatedIncrease);
            if (adjustment.compareTo(BigDecimal.ZERO) != 0) {
                CashFlowItem adjustmentItem = new CashFlowItem("经营", "其他现金收支", adjustment, 
                    adjustment.compareTo(BigDecimal.ZERO) > 0 ? "inflow" : "outflow");
                cashFlow.getOperatingActivities().add(adjustmentItem);
                cashFlow.setOperatingNetCash(operatingNet.add(adjustment));
            }
        }
    }
    
    /**
     * 计算单类活动的净现金流
     */
    private BigDecimal calculateActivityNetCash(List<CashFlowItem> activities) {
        BigDecimal inflow = activities.stream()
            .filter(item -> "inflow".equals(item.getFlowDirection()))
            .map(CashFlowItem::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
            
        BigDecimal outflow = activities.stream()
            .filter(item -> "outflow".equals(item.getFlowDirection()))
            .map(CashFlowItem::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
            
        return inflow.subtract(outflow);
    }
}