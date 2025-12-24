package com.gnu.cash.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("asset_transactions")
public class AssetTransaction {
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    @TableField("user_id")
    private Integer userId;
    
    @TableField("asset_id")
    private Integer assetId;
    
    @TableField("transaction_date")
    private Date transactionDate;
    
    @TableField("description")
    private String description;
    
    @TableField("debit_amount")
    private BigDecimal debitAmount;
    
    @TableField("credit_amount")
    private BigDecimal creditAmount;
    
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private Date createdAt;
}