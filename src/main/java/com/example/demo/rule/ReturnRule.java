package com.example.demo.rule;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

/**
 * @ClassName: ReturnRule
 * @Author: sxl
 * @Description:
 * @Date: 2022/9/23 16:44
 * @Version: 1.0
 */
@Rule
public class ReturnRule {
    @Condition
    public boolean itRains(@Fact("rain") boolean rain) {
        return rain;
    }

    @Action
    public String takeAnUmbrella(Facts facts,String name) {
       return facts.get(name);
    }
}
