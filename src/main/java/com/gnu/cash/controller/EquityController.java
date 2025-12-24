package com.gnu.cash.controller;

import com.gnu.cash.mapper.EquityMapper;
import com.gnu.cash.pojo.Equity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/equity")
public class EquityController {
    
    @Autowired
    private EquityMapper equityMapper;
    
    @GetMapping
    public List<Equity> getAllEquity() {
        return equityMapper.selectList(null);
    }
    
    @GetMapping("/{id}")
    public Equity getEquityById(@PathVariable Long id) {
        return equityMapper.selectById(id);
    }
    
    @PostMapping
    public String createEquity(@RequestBody Equity equity) {
        int result = equityMapper.insert(equity);
        return result > 0 ? "创建成功" : "创建失败";
    }
}