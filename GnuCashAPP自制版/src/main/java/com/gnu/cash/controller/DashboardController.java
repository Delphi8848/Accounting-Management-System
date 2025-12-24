package com.gnu.cash.controller;

import com.gnu.cash.mapper.DashboardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardMapper dashboardMapper;

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getFinancialSummary(@RequestParam Integer userId) {
        try {
            LocalDate now = LocalDate.now();
            int currentMonth = now.getMonthValue();
            int currentYear = now.getYear();

            // 查询基础数据
            Map<String, Object> rawData = dashboardMapper.getFinancialSummary(userId, currentMonth, currentYear);

            // 转换为BigDecimal
            BigDecimal assets = getBigDecimal(rawData, "assets");
            BigDecimal liabilities = getBigDecimal(rawData, "liabilities");
            BigDecimal equity = getBigDecimal(rawData, "equity");
            BigDecimal income = getBigDecimal(rawData, "income");
            BigDecimal expenses = getBigDecimal(rawData, "expenses");
            BigDecimal diff = getBigDecimal(rawData, "diff");
            BigDecimal monthIncome = getBigDecimal(rawData, "monthIncome");
            BigDecimal monthExpense = getBigDecimal(rawData, "monthExpense");
            BigDecimal cashBalance = getBigDecimal(rawData, "cashBalance");
            BigDecimal depositBalance = getBigDecimal(rawData, "depositBalance");
            BigDecimal liquidAssets = getBigDecimal(rawData, "liquidAssets");
            BigDecimal annualIncome = getBigDecimal(rawData, "annualIncome");
            BigDecimal avgMonthlyExpense = getBigDecimal(rawData, "avgMonthlyExpense");

            // 构建嵌套JSON响应
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("timestamp", now.toString());
            response.put("userId", userId);
            response.put("period", currentYear + "-" + String.format("%02d", currentMonth));

            // 第一层：财务概览（使用全量数据计算净资产）
            Map<String, Object> overview = new HashMap<>();
            BigDecimal netWorth = assets.subtract(liabilities);
            overview.put("netWorth", netWorth);
            overview.put("monthIncome", monthIncome); // 本月收入
            overview.put("monthExpense", monthExpense); // 本月支出
            overview.put("cashFlow", monthIncome.subtract(monthExpense)); // 本月净现金流
            response.put("overview", overview);

            // 第二层：财务健康度
            Map<String, Object> health = new HashMap<>();

            // 资产占比 = 资产 / 净资产
            BigDecimal assetRatio = netWorth.compareTo(BigDecimal.ZERO) > 0 ?
                    assets.divide(netWorth, 4, RoundingMode.HALF_UP).multiply(new BigDecimal("100")) : BigDecimal.ZERO;
            health.put("assetRatio", assetRatio.setScale(0, RoundingMode.HALF_UP));

            // 负债占比 = 负债 / 净资产
            BigDecimal liabilityRatio = netWorth.compareTo(BigDecimal.ZERO) > 0 ?
                    liabilities.divide(netWorth, 4, RoundingMode.HALF_UP).multiply(new BigDecimal("100")) : BigDecimal.ZERO;
            health.put("liabilityRatio", liabilityRatio.setScale(0, RoundingMode.HALF_UP));

            // 应急资金月数 = 流动资产 / 月均支出
            BigDecimal emergencyFund = avgMonthlyExpense.compareTo(BigDecimal.ZERO) > 0 ?
                    liquidAssets.divide(avgMonthlyExpense, 1, RoundingMode.HALF_UP) : BigDecimal.ZERO;
            health.put("emergencyFund", emergencyFund);

            // 负债收入比 = 负债 / 年收入
            BigDecimal debtToIncome = annualIncome.compareTo(BigDecimal.ZERO) > 0 ?
                    liabilities.divide(annualIncome, 4, RoundingMode.HALF_UP) : BigDecimal.ZERO;
            health.put("debtToIncome", debtToIncome.setScale(2, RoundingMode.HALF_UP));
            response.put("health", health);

            // 第三层：月度收支快览
            Map<String, Object> monthly = new HashMap<>();
            monthly.put("income", monthIncome);
            monthly.put("expense", monthExpense);
            monthly.put("netCashFlow", monthIncome.subtract(monthExpense));
            monthly.put("savingRate", monthIncome.compareTo(BigDecimal.ZERO) > 0 ?
                    monthIncome.subtract(monthExpense).divide(monthIncome, 4, RoundingMode.HALF_UP).multiply(new BigDecimal("100")) : BigDecimal.ZERO);
            response.put("monthly", monthly);

            // 第四层：账户余额总览
            Map<String, Object> balances = new HashMap<>();
            balances.put("cash", cashBalance);
            balances.put("deposit", depositBalance);
            balances.put("totalLiquid", liquidAssets);
            balances.put("investment", assets.subtract(liquidAssets));
            response.put("balances", balances);

            // 第五层：会计恒等验证（使用全量数据，与图片一致）
            Map<String, Object> accounting = new HashMap<>();
            accounting.put("assets", assets);
            accounting.put("liabilities", liabilities);
            accounting.put("equity", equity);
            accounting.put("income", income);
            accounting.put("expenses", expenses);
            accounting.put("calculatedNetWorth", netWorth);
            accounting.put("calculatedEquity", equity.add(income).subtract(expenses));
            accounting.put("difference", diff);
            accounting.put("isBalanced", diff.compareTo(BigDecimal.ZERO) == 0);
            accounting.put("formula", "资产 = 负债 + 权益 + (收入 - 支出)");
            response.put("accounting", accounting);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("error", "查询失败");
            error.put("message", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    private BigDecimal getBigDecimal(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        } else if (value instanceof Number) {
            return new BigDecimal(value.toString());
        }
        return BigDecimal.ZERO;
    }
}