package com.gnu.cash.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProfitLossItem {
    private String category;    // 类别：收入、支出
    private String level1;      // 一级科目
    private String level2;      // 二级科目
    private String level3;      // 三级科目
    private BigDecimal amount;  // 金额
    
    // 构造器、getter/setter
    public ProfitLossItem() {}
    
    public ProfitLossItem(String category, String level1, String level2, String level3, BigDecimal amount) {
        this.category = category;
        this.level1 = level1;
        this.level2 = level2;
        this.level3 = level3;
        this.amount = amount;
    }
}