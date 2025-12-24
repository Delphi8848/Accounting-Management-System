package com.gnu.cash.controller;

import com.gnu.cash.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionMapper transactionMapper;

    /**
     * 快速记账提交接口（事务优化版）
     */
    @PostMapping("/quick-record")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Map<String, Object>> quickRecordTransaction(@RequestBody Map<String, Object> request) {
        try {
            // 1. 参数验证
            Integer userId = (Integer) request.get("userId");
            BigDecimal amount = new BigDecimal(request.get("amount").toString());
            String transactionDate = (String) request.get("transactionDate");
            String description = (String) request.get("description");

            Map<String, Object> mainTransaction = (Map<String, Object>) request.get("mainTransaction");
            Map<String, Object> counterTransaction = (Map<String, Object>) request.get("counterTransaction");

            mainTransaction.put("userId",userId);
            counterTransaction.put("userId",userId);


            mainTransaction.put("transaction_date",transactionDate);
            counterTransaction.put("transaction_date",transactionDate);

            // 2. 验证金额一致性
            BigDecimal mainAmount = getBigDecimal(mainTransaction, "amount");
            BigDecimal counterAmount = getBigDecimal(counterTransaction, "amount");

            if (mainAmount.compareTo(amount) != 0 || counterAmount.compareTo(amount) != 0) {
                return ResponseEntity.badRequest().body(createErrorResponse("交易金额不一致"));
            }

            // 3. 验证当前会计恒等式是否平衡
            Map<String, Object> currentState = transactionMapper.validateAccountingEquation(userId);
            BigDecimal currentDiff = getBigDecimal(currentState, "currentDiff");

            if (currentDiff.compareTo(BigDecimal.ZERO) != 0) {
                return ResponseEntity.badRequest().body(createErrorResponse("当前账目不平衡，无法添加新交易"));
            }

            // 4. 直接插入交易记录到数据库
            insertTransactionRecord(mainTransaction);
            insertTransactionRecord(counterTransaction);

            // 5. 验证插入后的实际平衡状态
            Map<String, Object> finalState = transactionMapper.validateAccountingEquation(userId);
            BigDecimal finalDiff = getBigDecimal(finalState, "currentDiff");

            // 6. 如果不平衡，抛出异常触发事务回滚
            if (finalDiff.compareTo(BigDecimal.ZERO) != 0) {
                throw new RuntimeException("记账后账目不平衡，差额：" + finalDiff);
            }

            // 7. 如果平衡，提交事务并返回成功
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "记账成功");
            response.put("transactionId", System.currentTimeMillis());
            response.put("currentDiff", finalDiff);
            response.put("isBalanced", true);
            response.put("newBalances", finalState);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // 事务会自动回滚到插入前的状态
            return ResponseEntity.status(500).body(createErrorResponse("记账失败：" + e.getMessage()));
        }
    }

    /**
     * 插入交易记录
     */
    private void insertTransactionRecord(Map<String, Object> transaction) {
        String accountType = (String) transaction.get("accountType");
        Integer accountId = (Integer) transaction.get("accountId");
        BigDecimal amount = getBigDecimal(transaction, "amount");
        boolean isDebit = (boolean) transaction.get("isDebit");

        Map<String, Object> dbTransaction = new HashMap<>();
        dbTransaction.put("userId", transaction.get("userId"));
        dbTransaction.put("accountId", accountId);
        dbTransaction.put("transactionDate", transaction.get("transaction_date"));
        dbTransaction.put("description", transaction.get("description"));
        dbTransaction.put("debitAmount", isDebit ? amount : BigDecimal.ZERO);
        dbTransaction.put("creditAmount", isDebit ? BigDecimal.ZERO : amount);

        switch (accountType) {
            case "ASSET":
                transactionMapper.insertAssetTransaction(dbTransaction);
                break;
            case "LIABILITY":
                transactionMapper.insertLiabilityTransaction(dbTransaction);
                break;
            case "EQUITY":
                transactionMapper.insertEquityTransaction(dbTransaction);
                break;
            case "INCOME":
                transactionMapper.insertIncomeTransaction(dbTransaction);
                break;
            case "EXPENSE":
                transactionMapper.insertExpenseTransaction(dbTransaction);
                break;
        }
    }

    private BigDecimal getBigDecimal(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value instanceof BigDecimal) return (BigDecimal) value;
        if (value instanceof Number) return new BigDecimal(value.toString());
        return BigDecimal.ZERO;
    }

    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("success", false);
        error.put("error", message);
        return error;
    }
}