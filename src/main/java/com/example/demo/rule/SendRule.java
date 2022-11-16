package com.example.demo.rule;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

import java.util.LinkedHashMap;

/**
 * @ClassName: SendRule
 * @Author: sxl
 * @Description:
 * @Date: 2022/9/23 16:44
 * @Version: 1.0
 */
@Rule
public class SendRule{
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
            facts.put("out","积分已够，可以落户");
            return true;
        }else {
            facts.put("out","积分不够，无法落户");
        }
        return false;
    }

    @Action(order = 1)
    public void print(Facts facts) {
        System.out.println(facts.get("out").toString());
    }

    @Action(order = 2)
    public void sendSms(Facts facts) {
        //调用发送信息接口
        System.out.println("短信发送成功");
    }

}
