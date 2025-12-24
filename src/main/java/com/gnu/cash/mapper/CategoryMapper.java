package com.gnu.cash.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CategoryMapper {

    /**
     * 动态插入到对应科目表
     */
    @Insert("INSERT INTO ${tableName} (level1, level2, level3, description) " +
            "VALUES (#{level1}, #{level2}, #{level3}, #{description})")
    int insertIntoCategoryTable(@Param("tableName") String tableName,
                               @Param("level1") String level1,
                               @Param("level2") String level2,
                               @Param("level3") String level3,
                               @Param("description") String description);

    /**
     * 检查科目是否已存在
     */
    @Select("SELECT COUNT(*) FROM ${tableName} WHERE level1 = #{level1} AND level2 = #{level2} " +
            "AND (#{level3} IS NULL OR level3 = #{level3})")
    int checkAccountExistsInTable(@Param("tableName") String tableName,
                                 @Param("level1") String level1,
                                 @Param("level2") String level2,
                                 @Param("level3") String level3);
}