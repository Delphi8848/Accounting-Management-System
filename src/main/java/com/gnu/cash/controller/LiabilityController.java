package com.gnu.cash.controller;

import com.gnu.cash.mapper.LiabilityMapper;
import com.gnu.cash.pojo.Liability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/liabilities")
public class LiabilityController {
    
    @Autowired
    private LiabilityMapper liabilityMapper;
    
    @GetMapping
    public List<Liability> getAllLiabilities() {
        return liabilityMapper.selectList(null);
    }
    
    @GetMapping("/{id}")
    public Liability getLiabilityById(@PathVariable Long id) {
        return liabilityMapper.selectById(id);
    }
    
    @PostMapping
    public String createLiability(@RequestBody Liability liability) {
        int result = liabilityMapper.insert(liability);
        return result > 0 ? "创建成功" : "创建失败";
    }
}