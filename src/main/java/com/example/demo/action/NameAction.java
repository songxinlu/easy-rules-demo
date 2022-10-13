package com.example.demo.action;

import com.example.demo.entity.User;
import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Facts;

/**
 * @ClassName: NameAction
 * @Author: sxl
 * @Description:
 * @Date: 2022/9/23 17:05
 * @Version: 1.0
 */
public class NameAction implements Action {
    private String name;
    public NameAction(String _name) {
        this.name = _name;
    }
    @Override
    public void execute(Facts facts) throws Exception {
        System.out.println(facts);
    }
}
