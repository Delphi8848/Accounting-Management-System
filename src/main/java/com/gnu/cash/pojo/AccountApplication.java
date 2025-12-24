package com.gnu.cash.pojo;

import java.util.Date;

public class AccountApplication {
    private Integer id;
    private String category;
    private String level1;
    private String level2;
    private String level3;
    private String applicationReason;
    private String status;
    private Integer applicantId;
    private String applicantName;
    private Date applicationTime;
    private Integer reviewerId;
    private String reviewerName;
    private Date reviewTime;
    private String reviewComment;

    // 构造函数
    public AccountApplication() {
    }

    public AccountApplication(String category, String level1, String level2, String level3, 
                            String applicationReason, Integer applicantId, String applicantName) {
        this.category = category;
        this.level1 = level1;
        this.level2 = level2;
        this.level3 = level3;
        this.applicationReason = applicationReason;
        this.applicantId = applicantId;
        this.applicantName = applicantName;
        this.status = "待审批";
    }

    // Getter和Setter方法
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getLevel1() { return level1; }
    public void setLevel1(String level1) { this.level1 = level1; }

    public String getLevel2() { return level2; }
    public void setLevel2(String level2) { this.level2 = level2; }

    public String getLevel3() { return level3; }
    public void setLevel3(String level3) { this.level3 = level3; }

    public String getApplicationReason() { return applicationReason; }
    public void setApplicationReason(String applicationReason) { this.applicationReason = applicationReason; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getApplicantId() { return applicantId; }
    public void setApplicantId(Integer applicantId) { this.applicantId = applicantId; }

    public String getApplicantName() { return applicantName; }
    public void setApplicantName(String applicantName) { this.applicantName = applicantName; }

    public Date getApplicationTime() { return applicationTime; }
    public void setApplicationTime(Date applicationTime) { this.applicationTime = applicationTime; }

    public Integer getReviewerId() { return reviewerId; }
    public void setReviewerId(Integer reviewerId) { this.reviewerId = reviewerId; }

    public String getReviewerName() { return reviewerName; }
    public void setReviewerName(String reviewerName) { this.reviewerName = reviewerName; }

    public Date getReviewTime() { return reviewTime; }
    public void setReviewTime(Date reviewTime) { this.reviewTime = reviewTime; }

    public String getReviewComment() { return reviewComment; }
    public void setReviewComment(String reviewComment) { this.reviewComment = reviewComment; }
}