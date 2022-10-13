package com.example.demo.action;

import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Facts;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @ClassName: sendAction
 * @Author: sxl
 * @Description:
 * @Date: 2022/9/26 15:57
 * @Version: 1.0
 */
@Component
public class PrintAction implements Action {

    @Override
    public void execute(Facts facts) throws Exception {
        System.out.println(facts.toString());
    }
}
