package com.gnu.cash.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CashFlowItem {
    private String activityType;    // 活动类型：经营、投资、筹资
    private String description;     // 项目描述
    private BigDecimal amount;      // 金额
    private String flowDirection;   // 流向：inflow/outflow
    
     public CashFlowItem() {}
    
    public CashFlowItem(String activityType, String description, BigDecimal amount, String flowDirection) {
        this.activityType = activityType;
        this.description = description;
        this.amount = amount;
        this.flowDirection = flowDirection;
    }
}