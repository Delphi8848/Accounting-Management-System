package com.gnu.cash.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProfitLossVO {
    private String period;
    private List<ProfitLossItem> revenues = new ArrayList<>();     // 收入项
    private List<ProfitLossItem> expenses = new ArrayList<>();     // 支出项
    private BigDecimal totalRevenue = BigDecimal.ZERO;            // 总收入
    private BigDecimal totalExpense = BigDecimal.ZERO;            // 总支出
    private BigDecimal netIncome = BigDecimal.ZERO;               // 净利润
    
    // getter/setter
}