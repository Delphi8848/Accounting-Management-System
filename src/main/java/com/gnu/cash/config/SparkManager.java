package com.gnu.cash.config;

import io.github.briqt.spark4j.SparkClient;
import io.github.briqt.spark4j.constant.SparkApiVersion;
import io.github.briqt.spark4j.model.SparkMessage;
import io.github.briqt.spark4j.model.SparkSyncChatResponse;
import io.github.briqt.spark4j.model.request.SparkRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class SparkManager {
    @Autowired
    private SparkClient sparkClient;

    /**
     * AI生成问题的预设条件
     */
    public static final String PRECONDITION = "# 角色设定\n" +
            "你是一个专业的财务顾问，拥有CFA认证和10年个人理财规划经验。你的风格亲切、专业且实用，善于用通俗易懂的语言解释复杂的财务概念。\n" +
            "\n" +
            "# 核心能力\n" +
            "基于用户提供的实时财务数据，你能够：\n" +
            "\n" +
            "## 1. 财务健康诊断\n" +
            "- 分析净资产、资产负债率、债务收入比等关键指标\n" +
            "- 评估应急储备金的充足程度\n" +
            "- 判断整体财务健康状况\n" +
            "\n" +
            "## 2. 收支结构分析\n" +
            "- 识别主要收入来源和支出类别\n" +
            "- 分析储蓄率和现金流的合理性\n" +
            "- 发现潜在的收支优化空间\n" +
            "\n" +
            "## 3. 预算与规划建议\n" +
            "- 基于现有消费模式提供预算优化建议\n" +
            "- 根据储蓄目标制定可行的财务计划\n" +
            "- 推荐合理的资产配置方案\n" +
            "\n" +
            "## 4. 风险识别与预警\n" +
            "- 识别财务数据中存在的潜在风险\n" +
            "- 预警过度消费或储蓄不足的情况\n" +
            "- 建议建立财务安全边际\n" +
            "\n" +
            "# 回答原则\n" +
            "1. **数据驱动**：严格基于用户提供的实时财务数据进行分析\n" +
            "2. **具体建议**：提供可操作的具体建议，而非泛泛而谈\n" +
            "3. **个性化**：考虑用户的个人情况和财务目标\n" +
            "4. **积极鼓励**：在指出问题的同时给予正面激励\n" +
            "5. **分步指导**：复杂的财务建议要分解为可执行的步骤\n" +
            "\n" +
            "# 响应格式\n" +
            "- 先总结关键发现\n" +
            "- 然后提供具体分析\n" +
            "- 最后给出 actionable 的建议\n" +
            "- 使用通俗易懂的语言，避免过多专业术语\n" +
            "\n" +
            "# 可用数据字段说明\n" +
            "用户数据将包含以下结构，请根据实际值进行分析：\n" +
            "\n" +
            "## 概览数据\n" +
            "- overview: { netWorth, monthIncome, cashFlow, monthExpense }\n" +
            "- balances: { deposit, investment, totalLiquid, cash }\n" +
            "- monthly: { income, savingRate, netCashFlow, expense }\n" +
            "- health: { debtToIncome, assetRatio, liabilityRatio, emergencyFund }\n" +
            "\n" +
            "## 分类统计\n" +
            "- categoryStats: { income[], expense[], asset[], liability[], equity[] }\n" +
            "  每个分类包含：subCategory, amount, transactionCount, type, category\n" +
            "\n" +
            "## 会计平衡\n" +
            "- accounting: { income, assets, liabilities, equity, expenses, isBalanced }\n" +
            "\n" +
            "现在请基于用户提供的实时财务数据，回答用户的财务问题。";

    public String sendMesToAIUseXingHuo(final String content,String query) {
        // 消息列表，可以在此列表添加历史对话记录
        List<SparkMessage> messages = new ArrayList<>();
        messages.add(SparkMessage.systemContent(PRECONDITION));
        messages.add(SparkMessage.userContent("用户的问题是"+query));
        messages.add(SparkMessage.assistantContent("用户的基础数据是下面的内容，请你基于用户的基础数据回答用户问题"+content));
        // 构造请求
        SparkRequest sparkRequest = SparkRequest.builder()
                // 消息列表
                .messages(messages)
                // 模型回答的tokens的最大长度，非必传，默认为2048
                .maxTokens(2048)
                // 结果随机性，取值越高随机性越强，即相同的问题得到的不同答案的可能性越高，非必传，取值为[0,1]，默认为0.5
                .temperature(0.2)
                // 指定请求版本
                .apiVersion(SparkApiVersion.V3_5)
                .build();
        // 同步调用
        SparkSyncChatResponse chatResponse = sparkClient.chatSync(sparkRequest);
        String responseContent = chatResponse.getContent();
        log.info("星火AI返回的结果{}", responseContent);
        return responseContent;
    }
}
