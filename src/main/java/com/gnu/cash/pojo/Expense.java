package com.gnu.cash.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("expenses")
public class Expense {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    @TableField("level1")
    private String level1;
    
    @TableField("level2")
    private String level2;
    
    @TableField("level3")
    private String level3;
    
    @TableField("description")
    private String description;
}