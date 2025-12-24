package com.gnu.cash.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("equity_transactions")
public class EquityTransaction {
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    @TableField("user_id")
    private Integer userId;
    
    @TableField("equity_id")
    private Integer equityId;
    
    @TableField("transaction_date")
    private Date transactionDate;
    
    private String description;
    
    @TableField("debit_amount")
    private BigDecimal debitAmount;
    
    @TableField("credit_amount")
    private BigDecimal creditAmount;
    
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private Date createdAt;
}