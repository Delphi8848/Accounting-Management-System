package com.gnu.cash.controller;

import com.gnu.cash.mapper.AssetMapper;
import com.gnu.cash.pojo.Asset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/assets")
public class AssetController {
    
    @Autowired
    private AssetMapper assetMapper;
    
    @GetMapping
    public List<Asset> getAllAssets() {
        return assetMapper.selectList(null);
    }
    
    @GetMapping("/{id}")
    public Asset getAssetById(@PathVariable Long id) {
        return assetMapper.selectById(id);
    }
    
    @PostMapping
    public String createAsset(@RequestBody Asset asset) {
        int result = assetMapper.insert(asset);
        return result > 0 ? "创建成功" : "创建失败";
    }
    
    @PutMapping("/{id}")
    public String updateAsset(@PathVariable Long id, @RequestBody Asset asset) {
        asset.setId(id);
        int result = assetMapper.updateById(asset);
        return result > 0 ? "更新成功" : "更新失败";
    }
    
    @DeleteMapping("/{id}")
    public String deleteAsset(@PathVariable Long id) {
        int result = assetMapper.deleteById(id);
        return result > 0 ? "删除成功" : "删除失败";
    }
    
    @GetMapping("/level/{level1}")
    public List<Asset> getAssetsByLevel1(@PathVariable String level1) {
        return assetMapper.selectByLevel1(level1);
    }
}