package com.gnu.cash.pojo;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class BalanceSheetItem {
    private String category;    // 大类：资产、负债、权益
    private String level1;      // 一级科目
    private String level2;      // 二级科目  
    private String level3;      // 三级科目
    private BigDecimal balance; // 余额
    public BalanceSheetItem(String category, String level1, String level2, String level3, BigDecimal balance) {
        this.category = category;
        this.level1 = level1;
        this.level2 = level2;
        this.level3 = level3;
        this.balance = balance;
    }
 }