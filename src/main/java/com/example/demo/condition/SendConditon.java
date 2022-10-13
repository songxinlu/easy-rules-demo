package com.example.demo.condition;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.User;
import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;
import org.springframework.stereotype.Component;

/**
 * @ClassName: SendConditon
 * @Author: sxl
 * @Description:
 * @Date: 2022/9/26 16:03
 * @Version: 1.0
 */
@Component
public class SendConditon implements Condition {
    @Override
    public boolean evaluate(Facts facts) {
        return JSONObject.parseObject(JSONObject.toJSONString(facts.get("user")), User.class).getAge()>17;
    }
}
