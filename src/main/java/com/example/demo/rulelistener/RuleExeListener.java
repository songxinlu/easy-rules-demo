package com.example.demo.rulelistener;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.RuleListener;

/**
 * @ClassName: RuleExeListener
 * @Author: sxl
 * @Description: 规则执行监听
 * @Date: 2022/10/12 9:12
 * @Version: 1.0
 */
public class RuleExeListener implements RuleListener {
    public boolean beforeEvaluate(Rule rule, Facts facts) {
        System.out.println("在执行规则"+rule.getName()+"Condition之前执行");
        return true;
    }

    public void afterEvaluate(Rule rule, Facts facts, boolean evaluationResult) {
        System.out.println("在执行完规则"+rule.getName()+"Condition之后执行");
    }

    public void onEvaluationError(Rule rule, Facts facts, Exception exception) {
        System.out.println("在执行规则"+rule.getName()+"Condition中发生异常后执行");
    }

    public void beforeExecute(Rule rule, Facts facts) {
        System.out.println("在执行规则"+rule.getName()+"Action之前执行");
    }

    public void onSuccess(Rule rule, Facts facts) {
        System.out.println("在执行规则"+rule.getName()+"Action完成后执行");
    }

    public void onFailure(Rule rule, Facts facts, Exception exception) {
        System.out.println("在执行规则"+rule.getName()+"Action发生异常之后执行");
    }
}
