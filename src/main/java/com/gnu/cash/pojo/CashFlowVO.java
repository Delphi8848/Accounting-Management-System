package com.gnu.cash.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class CashFlowVO {
    private String period;
    private List<CashFlowItem> operatingActivities = new ArrayList<>();  // 经营活动
    private List<CashFlowItem> investingActivities = new ArrayList<>();  // 投资活动  
    private List<CashFlowItem> financingActivities = new ArrayList<>();  // 筹资活动
    private BigDecimal operatingNetCash;    // 经营活动净现金流
    private BigDecimal investingNetCash;    // 投资活动净现金流
    private BigDecimal financingNetCash;    // 筹资活动净现金流
    private BigDecimal netCashIncrease;     // 现金净增加额
    private BigDecimal cashAtBeginning;     // 期初现金余额
    private BigDecimal cashAtEnd;           // 期末现金余额
    
 }