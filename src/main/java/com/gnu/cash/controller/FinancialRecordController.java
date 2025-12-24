package com.gnu.cash.controller;
import com.gnu.cash.mapper.DashboardMapper;
import com.gnu.cash.mapper.FinancialRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/financial-records")
public class FinancialRecordController {

    @Autowired
    private FinancialRecordMapper financialRecordMapper;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getFinancialRecords(
            @RequestParam Integer userId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("offset", (page - 1) * size);
        params.put("size", size);

        List<Map<String, Object>> records = financialRecordMapper.getAllFinancialRecords(params);
        int total = financialRecordMapper.countAllFinancialRecords(params);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", new HashMap<String, Object>() {{
            put("records", records);
            put("pagination", new HashMap<String, Object>() {{
                put("total", total);
                put("page", page);
                put("size", size);
            }});
        }});

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getFinancialRecordDetail(
            @PathVariable Long id,
            @RequestParam String recordType) {
        
        Map<String, Object> record = financialRecordMapper.getFinancialRecordById(recordType, id);
        if (record == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> account = financialRecordMapper.getAccountInfo(
            recordType, (Integer) record.get(recordType.toLowerCase() + "_id"));
        
        Map<String, Object> pairedRecord = financialRecordMapper.findPairedFinancialRecord(
            (Integer) record.get("user_id"),
            (String) record.get("transaction_date"),
            (String) record.get("description"),
            (BigDecimal) record.get("debit_amount"),
            (BigDecimal) record.get("credit_amount"),
            recordType
        );

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", new HashMap<String, Object>() {{
            put("record", record);
            put("account", account);
            put("pairedRecord", pairedRecord);
        }});

        return ResponseEntity.ok(response);
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getFinancialStatistics(
            @RequestParam Integer userId,
            @RequestParam(defaultValue = "month") String period) {
        
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("periodFormat", 
            period.equals("year") ? "%Y" : 
            period.equals("month") ? "%Y-%m" : "%Y-%m-%d");

        List<Map<String, Object>> byCategory = financialRecordMapper.getFinancialStatsByCategory(params);
        List<Map<String, Object>> byPeriod = financialRecordMapper.getFinancialStatsByPeriod(params);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", new HashMap<String, Object>() {{
            put("byCategory", byCategory);
            put("byPeriod", byPeriod);
        }});

        return ResponseEntity.ok(response);
    }
}