package com.example.demo.facade;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.RuleWrapper;
import com.example.demo.rulelistener.RuleExeListener;
import com.example.demo.vo.ELEasyRulesVO;
import com.example.demo.entity.User;
import com.google.common.collect.Lists;
import org.jeasy.rules.api.*;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRule;
import org.jeasy.rules.spel.SpELRule;
import org.jeasy.rules.support.composite.ActivationRuleGroup;
import org.jeasy.rules.support.composite.ConditionalRuleGroup;
import org.jeasy.rules.support.composite.UnitRuleGroup;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName: TestFacade
 * @Author: sxl
 * @Description:
 * @Date: 2022/9/23 16:40
 * @Version: 1.0
 */
@Component
public class RuleFacade {

    /**
     * @Method execute
     * @Author sxl
     * @Description规则执行
     * @param easyRulesVO
     * @Return java.lang.String
     */
    public String execute(ELEasyRulesVO easyRulesVO) {

        //满足规则执行一次
        DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
        //创建规则
        Rules rules = creatRules(easyRulesVO.getRules());
        //添加规则监听
        creatListener(rulesEngine, easyRulesVO.getListenersPath());
        //装载事实
        Facts facts = creatFacts(easyRulesVO.getFacts());
        //执行规则引擎
        rulesEngine.fire(rules, facts);
        return facts.toString();
    }



    private void creatListener(DefaultRulesEngine rulesEngine, List<String> listenersPath) {
        if (CollectionUtil.isEmpty(listenersPath)) {
            return;
        }
        List<RuleListener> ruleListeners = Lists.newArrayList();
        listenersPath.stream().forEach(path -> {
            RuleListener ruleListener = null;
            try {
                ruleListener = (RuleListener) Class.forName(path).newInstance();
                ruleListeners.add(ruleListener);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        });
        rulesEngine.registerRuleListeners(ruleListeners);
    }

    private Facts creatFacts(JSONObject facts) {
        Facts out = new Facts();
        facts.keySet().forEach(key -> {
            out.put(key, facts.get(key));
        });
        return out;
    }

    /**
     * @Method creatRules
     * @Author sxl
     * @Description创建规则
     * @param rulesVO
     * @Return org.jeasy.rules.api.Rules
     */
    private Rules creatRules(List<RuleWrapper> rulesVO) {

        Rules rules = new Rules();
        rulesVO.stream().forEach(ruleVO -> {
            if (CollectionUtil.isNotEmpty(ruleVO.getActions())
                    || CollectionUtil.isNotEmpty(ruleVO.getGroupRules())
                    || !StringUtils.isEmpty(ruleVO.getCustomRulePath())) {

                switch (ruleVO.getRuleType()) {
                    case "mvel":
                        MVELRule mvel = new MVELRule()
                                .name(ruleVO.getName())
                                .description(ruleVO.getDescription())
                                .priority(ruleVO.getPriority())
                                .when(ruleVO.getCondition());
                        ruleVO.getActions().stream().forEach(action -> {
                            mvel.then(action);
                        });
                        if (Objects.nonNull(mvel)) {
                            rules.register(mvel);
                        }
                        break;
                    case "spel":
                        SpELRule spel = new SpELRule()
                                .name(ruleVO.getName())
                                .description(ruleVO.getDescription())
                                .priority(ruleVO.getPriority())
                                .when(ruleVO.getCondition());
                        ruleVO.getActions().stream().forEach(action -> {
                            spel.then(action);
                        });
                        if (Objects.nonNull(spel)) {
                            rules.register(spel);
                        }
                        break;

                    case "custom":  //自定义规则
                        try {
                            Class cl = Class.forName(ruleVO.getCustomRulePath());
                            Object rule = cl.getConstructor(String.class, String.class, int.class).newInstance(ruleVO.getName(),
                                    ruleVO.getDescription(),
                                    ruleVO.getPriority());

                            if (Objects.nonNull(rule)) {
                                rules.register(rule);
                            }
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "unitRuleGroup":  //且条件规则组
                        if (CollectionUtil.isNotEmpty(ruleVO.getGroupRules())) {
                            UnitRuleGroup unitRuleGroup = new UnitRuleGroup(ruleVO.getName(), ruleVO.getDescription(), ruleVO.getPriority());
                            Rules unitGroupRules = creatRules(ruleVO.getGroupRules());
                            if(CollectionUtil.isNotEmpty(unitGroupRules)){
                                unitGroupRules.forEach(temp->{
                                    unitRuleGroup.addRule(temp);
                                    rules.register(unitRuleGroup);
                                });
                            }
                        }
                        break;
                    case "conditionalRuleGroup":  //满足第一条（第一条指的是优先级最高的规则只有一条）的时候，才会执行后面规则
                        if (CollectionUtil.isNotEmpty(ruleVO.getGroupRules())) {
                            ConditionalRuleGroup conditionalRuleGroup = new ConditionalRuleGroup(ruleVO.getName(), ruleVO.getDescription(), ruleVO.getPriority());
                            Rules unitGroupRules = creatRules(ruleVO.getGroupRules());
                            if(CollectionUtil.isNotEmpty(unitGroupRules)){
                                unitGroupRules.forEach(temp->{
                                    conditionalRuleGroup.addRule(temp);
                                    rules.register(conditionalRuleGroup);
                                });
                            }
                        }
                        break;
                    case "activationRuleGroup":  //或条件规则组（执行到满足条件的规则后就不会继续执行后续规则）（类似短路或||）
                        if (CollectionUtil.isNotEmpty(ruleVO.getGroupRules())) {
                            ActivationRuleGroup activationRuleGroup = new ActivationRuleGroup(ruleVO.getName(), ruleVO.getDescription(), ruleVO.getPriority());
                            Rules unitGroupRules = creatRules(ruleVO.getGroupRules());
                            if(CollectionUtil.isNotEmpty(unitGroupRules)){
                                unitGroupRules.forEach(temp->{
                                    activationRuleGroup.addRule(temp);
                                    rules.register(activationRuleGroup);
                                });
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        });

        return rules;
    }

}
