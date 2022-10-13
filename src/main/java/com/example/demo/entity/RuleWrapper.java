package com.example.demo.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: RuleWrapper
 * @Author: sxl
 * @Description: 规则包装类
 * @Date: 2022/9/26 13:45
 * @Version: 1.0
 */
@Data
public class RuleWrapper {
    @ApiModelProperty("规则名称")
    protected String name;

    @ApiModelProperty("规则描述")
    protected String description;

    @ApiModelProperty("规则优先级")
    protected int priority;

    @ApiModelProperty("规则类型：mvel-mv表达式 " +
            "spel-sp表达式 " +
            "custom-自定义（自定义要填写自定义规则路径）" +
            "unitRuleGroup-且条件规则组 " +
            "conditionalRuleGroup-满足第一条（第一条指的是优先级最高的规则只有一条）的时候，才会执行后面规则"+
            "activationRuleGroup-或条件规则组（执行到满足条件的规则后就不会继续执行后续规则）（类似短路或||） ")
    String ruleType;

    @ApiModelProperty("条件")
    String condition;

    @ApiModelProperty("执行动作")
    List<String> actions;

    @ApiModelProperty("自定义规则路径")
    String customRulePath;

    @ApiModelProperty("规则组")
    List<RuleWrapper> groupRules;

}
