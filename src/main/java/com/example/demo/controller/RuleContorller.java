package com.example.demo.controller;

import com.example.demo.vo.ELEasyRulesVO;
import com.example.demo.facade.RuleFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ClassName: RuleContorller
 * @Author: sxl
 * @Description: 规则控制类
 * @Date: 2022/9/23 16:33
 * @Version: 1.0
 */
@Api(tags = "规则API")
@RestController
@RequestMapping("/rule")
public class RuleContorller {
    @Autowired
    RuleFacade testFacade;

    @PostMapping("/execute")
    @ApiOperation("规则执行")
    public String execute(@RequestBody ELEasyRulesVO easyRulesVO) {
        return testFacade.execute(easyRulesVO);
    }

}
