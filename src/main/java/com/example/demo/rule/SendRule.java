package com.example.demo.rule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;

/**
 * @ClassName: SendRule
 * @Author: sxl
 * @Description:
 * @Date: 2022/9/23 16:44
 * @Version: 1.0
 */
@Rule
public class SendRule implements Rule{
    private String name;
    private String description;
    private int priority;

    public SendRule(String name, String description, int priority){
        this.name = name;
        this.description = description;
        this.priority = priority;
    }
    @Condition
    public boolean sendCondition(Facts facts) {
        //用户积分
        LinkedHashMap map = facts.get("user");
        map.get("integral");
        int integral = (int) map.get("integral");;
        if(integral>125){
            facts.put("lh","积分已够，可以落户");
        }else {
            facts.put("lh","积分不够，无法落户");
        }
        return true;
    }

    @Action
    public void send(Facts facts) {
        System.out.println(facts.get("lh").toString());
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public int priority() {
        return priority;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
