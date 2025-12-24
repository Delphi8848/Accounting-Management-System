package com.gnu.cash.mapper;

import com.gnu.cash.pojo.AccountApplication;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AccountApplicationMapper {


    // 动态插入到对应表
    @Insert("INSERT INTO ${tableName} (level1, level2, level3, description) " +
            "VALUES (#{level1}, #{level2}, #{level3}, #{description})")
    int insertIntoCategoryTable(@Param("tableName") String tableName,
                                @Param("level1") String level1,
                                @Param("level2") String level2,
                                @Param("level3") String level3,
                                @Param("description") String description);



    // 检查科目是否已存在
    @Select("SELECT COUNT(*) FROM ${tableName} WHERE level1 = #{level1} AND level2 = #{level2} " +
            "AND (#{level3} IS NULL OR level3 = #{level3})")
    int checkAccountExistsInTable(@Param("tableName") String tableName,
                                  @Param("level1") String level1,
                                  @Param("level2") String level2,
                                  @Param("level3") String level3);

    // 插入新申请
    @Insert("INSERT INTO account_application (category, level1, level2, level3, application_reason, status, applicant_id, applicant_name) " +
            "VALUES (#{category}, #{level1}, #{level2}, #{level3}, #{applicationReason}, #{status}, #{applicantId}, #{applicantName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AccountApplication application);

    // 根据ID查询
    @Select("SELECT * FROM account_application WHERE id = #{id}")
    AccountApplication selectById(Integer id);

    // 查询所有申请
    @Select("SELECT * FROM account_application ORDER BY application_time DESC")
    List<AccountApplication> selectAll();

    // 根据状态查询
    @Select("SELECT * FROM account_application WHERE status = #{status} ORDER BY application_time DESC")
    List<AccountApplication> selectByStatus(String status);

    // 根据申请人查询
    @Select("SELECT * FROM account_application WHERE applicant_id = #{applicantId} ORDER BY application_time DESC")
    List<AccountApplication> selectByApplicant(Integer applicantId);

    // 更新申请状态
    @Update("UPDATE account_application SET status = #{status}, reviewer_id = #{reviewerId}, " +
            "reviewer_name = #{reviewerName}, review_time = NOW(), review_comment = #{reviewComment} " +
            "WHERE id = #{id}")
    int updateStatus(AccountApplication application);

    // 检查科目是否已存在
    @Select("<script>" +
            "SELECT COUNT(*) FROM (" +
            "  SELECT level1, level2, level3 FROM assets WHERE level1 = #{level1} AND level2 = #{level2} " +
            "  <if test='level3 != null'> AND level3 = #{level3}</if>" +
            "  UNION ALL " +
            "  SELECT level1, level2, level3 FROM liabilities WHERE level1 = #{level1} AND level2 = #{level2} " +
            "  <if test='level3 != null'> AND level3 = #{level3}</if>" +
            "  UNION ALL " +
            "  SELECT level1, level2, level3 FROM equity WHERE level1 = #{level1} AND level2 = #{level2} " +
            "  <if test='level3 != null'> AND level3 = #{level3}</if>" +
            "  UNION ALL " +
            "  SELECT level1, level2, level3 FROM income WHERE level1 = #{level1} AND level2 = #{level2} " +
            "  <if test='level3 != null'> AND level3 = #{level3}</if>" +
            "  UNION ALL " +
            "  SELECT level1, level2, level3 FROM expenses WHERE level1 = #{level1} AND level2 = #{level2} " +
            "  <if test='level3 != null'> AND level3 = #{level3}</if>" +
            ") t" +
            "</script>")
    int checkAccountExists(@Param("level1") String level1, @Param("level2") String level2, @Param("level3") String level3);
}