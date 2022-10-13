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
public class sendAction implements Action {

    @Override
    public void execute(Facts facts) throws Exception {
        if (Objects.nonNull(facts.get("sendType"))) {
            switch ((String) facts.get("sendType")) {
                case "tcp":
                    System.out.println("tcp");
                    break;
                case "udp":
                    System.out.println("udp");
                    break;
                case "mqtt":
                    System.out.println("mqtt");
                    break;
                case "http":
                    System.out.println("http");
                    break;
                default:
                    break;
            }
        }
    }
}
