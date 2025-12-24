package com.gnu.cash.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gnu.cash.pojo.Asset;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface AssetMapper extends BaseMapper<Asset> {
    
    @Select("SELECT * FROM assets WHERE level1 = #{level1}")
    List<Asset> selectByLevel1(String level1);
    
    @Select("SELECT * FROM assets WHERE level1 = #{level1} AND level2 = #{level2}")
    List<Asset> selectByLevel1AndLevel2(String level1, String level2);
}