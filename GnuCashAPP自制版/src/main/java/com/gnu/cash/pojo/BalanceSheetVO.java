package com.gnu.cash.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class BalanceSheetVO {
    private String reportDate;
    private List<BalanceSheetItem> assets;          // 资产项
    private List<BalanceSheetItem> liabilities;     // 负债项
    private List<BalanceSheetItem> equity;          // 权益项
    private BigDecimal assetsTotal;                 // 资产总额
    private BigDecimal liabilitiesTotal;            // 负债总额
    private BigDecimal equityTotal;                 // 权益总额
    private Boolean isBalanced;                     // 是否平衡
    
 }