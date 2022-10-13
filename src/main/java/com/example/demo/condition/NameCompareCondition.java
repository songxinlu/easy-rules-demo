package com.example.demo.condition;

import com.example.demo.entity.User;
import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;

/**
 * @ClassName: NameCompareCondition
 * @Author: sxl
 * @Description:
 * @Date: 2022/9/23 17:00
 * @Version: 1.0
 */
public class NameCompareCondition implements Condition {
    private String name;
    public NameCompareCondition(String _name) {
        this.name = _name;
    }
    @Override
    public boolean evaluate(Facts facts) {
        Object obj = facts.get(name);
        System.out.println(obj);
        boolean out = facts.asMap().containsKey(name);
        return out;
    }


}
