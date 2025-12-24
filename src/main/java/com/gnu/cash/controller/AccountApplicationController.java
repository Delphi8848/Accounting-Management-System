package com.gnu.cash.controller;

import com.gnu.cash.mapper.AccountApplicationMapper;
import com.gnu.cash.mapper.CategoryMapper;
import com.gnu.cash.pojo.AccountApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/account-application")
public class AccountApplicationController {

    @Autowired
    private AccountApplicationMapper applicationMapper;


    @Autowired
    private CategoryMapper categoryMapper;

    // 表名映射
    private String getTableNameByCategory(String category) {
        switch (category) {
            case "资产": return "assets";
            case "负债": return "liabilities";
            case "所有者权益": return "equity";
            case "收入": return "income";
            case "支出": return "expenses";
            default: throw new IllegalArgumentException("未知的科目分类: " + category);
        }
    }

    // 审批申请
    @PutMapping("/{id}/review")
    public ResponseEntity<?> reviewApplication(@PathVariable Integer id,
                                               @RequestBody Map<String, String> reviewRequest) {
        try {
                String status = reviewRequest.get("status");
            String reviewComment = reviewRequest.get("reviewComment");

            AccountApplication application = applicationMapper.selectById(id);
            if (application == null) {
                return ResponseEntity.notFound().build();
            }

            // 更新申请状态
            application.setStatus(status);
            application.setReviewComment(reviewComment);
            application.setReviewerId(0011);
            application.setReviewerName("系统管理员");

            int result = applicationMapper.updateStatus(application);

            if (result > 0) {
                // 如果审批通过，插入数据到对应表
                if ("已通过".equals(status)) {
                    try {
                        // 获取对应的表名
                        String tableName = getTableNameByCategory(application.getCategory());

                        // 检查目标表中是否已存在该科目
                        int exists = applicationMapper.checkAccountExistsInTable(
                                tableName,
                                application.getLevel1(),
                                application.getLevel2(),
                                application.getLevel3()
                        );

                        if (exists == 0) {
                            // 插入到对应表
                            int insertResult = applicationMapper.insertIntoCategoryTable(
                                    tableName,
                                    application.getLevel1(),
                                    application.getLevel2(),
                                    application.getLevel3(),
                                    application.getApplicationReason()
                            );
                            if (insertResult > 0) {
                                Map<String, String> response = new HashMap<>();
                                response.put("message", "审批操作成功，科目已添加到系统");
                                return ResponseEntity.ok(response);
                            } else {
                                Map<String, String> response = new HashMap<>();
                                response.put("message", "审批操作成功，但科目添加失败，请联系管理员");
                                return ResponseEntity.ok(response);
                            }
                        } else {
                            Map<String, String> response = new HashMap<>();
                            response.put("message", "审批操作成功，但该科目已存在系统中");
                            return ResponseEntity.ok(response);
                        }
                    } catch (Exception e) {
                        Map<String, String> response = new HashMap<>();
                        response.put("message", "审批操作成功，但科目添加过程出现异常");
                        return ResponseEntity.ok(response);
                    }
                } else {
                    // 审批不通过的情况
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "审批操作成功");
                    return ResponseEntity.ok(response);
                }
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "审批操作失败");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "服务器错误: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }


    // 提交新申请
    @PostMapping
    public ResponseEntity<?> createApplication(@RequestBody AccountApplication application) {
        try {

            // 检查科目是否已存在
            int exists = applicationMapper.checkAccountExists(
                    application.getLevel1(),
                    application.getLevel2(),
                    application.getLevel3()
            );

            if (exists > 0) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "该科目已存在，无需重复申请");
                return ResponseEntity.badRequest().body(response);
            }

            application.setStatus("待审批");
            int result = applicationMapper.insert(application);

            if (result > 0) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "申请提交成功");
                response.put("applicationId", application.getId().toString());
                return ResponseEntity.ok(response);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "申请提交失败");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "服务器错误");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // 获取所有申请
    @GetMapping
    public ResponseEntity<List<AccountApplication>> getAllApplications() {
        List<AccountApplication> applications = applicationMapper.selectAll();
        return ResponseEntity.ok(applications);
    }

    // 根据ID获取申请
    @GetMapping("/{id}")
    public ResponseEntity<AccountApplication> getApplicationById(@PathVariable Integer id) {
        AccountApplication application = applicationMapper.selectById(id);
        if (application != null) {
            return ResponseEntity.ok(application);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 根据状态获取申请
    @GetMapping("/status/{status}")
    public ResponseEntity<List<AccountApplication>> getApplicationsByStatus(@PathVariable String status) {
        List<AccountApplication> applications = applicationMapper.selectByStatus(status);
        return ResponseEntity.ok(applications);
    }

    // 根据申请人获取申请
    @GetMapping("/applicant/{applicantId}")
    public ResponseEntity<List<AccountApplication>> getApplicationsByApplicant(@PathVariable Integer applicantId) {
        List<AccountApplication> applications = applicationMapper.selectByApplicant(applicantId);
        return ResponseEntity.ok(applications);
    }
    /**
     * 直接添加科目到数据库
     */
    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody Map<String, String> request) {
        try {
            String category = request.get("category");
            String level1 = request.get("level1");
            String level2 = request.get("level2");
            String level3 = request.get("level3");
            String description = request.get("description");

            // 参数校验
            if (category == null || level1 == null ) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "分类、一级科目为必填项");
                return ResponseEntity.badRequest().body(response);
            }

            // 获取表名
            String tableName = getTableNameByCategory(category);

            // 检查科目是否已存在
            int exists = categoryMapper.checkAccountExistsInTable(tableName, level1, level2, level3);
            if (exists > 0) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "该科目已存在");
                return ResponseEntity.badRequest().body(response);
            }

            // 插入到数据库
            int result = categoryMapper.insertIntoCategoryTable(tableName, level1, level2, level3, description);

            if (result > 0) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "科目添加成功");
                return ResponseEntity.ok(response);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "科目添加失败");
                return ResponseEntity.badRequest().body(response);
            }

        } catch (IllegalArgumentException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "服务器错误: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

}