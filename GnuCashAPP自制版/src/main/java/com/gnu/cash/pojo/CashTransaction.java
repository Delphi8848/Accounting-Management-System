package com.gnu.cash.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CashTransaction {
    private LocalDate transactionDate;
    private String description;
    private BigDecimal debitAmount;
    private BigDecimal creditAmount;
    private String level1;
    private String level2;
    private String level3;
    
    // getter/setter
}