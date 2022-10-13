package com.example.demo.vo;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.RuleWrapper;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @ClassName: EasyRulesVO
 * @Author: sxl
 * @Description:
 * @Date: 2022/9/26 15:48
 * @Version: 1.0
 */
public class EasyRulesVO {
    @ApiModelProperty("事实")
    private JSONObject facts;

    @ApiModelProperty("条件和执行动作")
    private List<RuleWrapper> conditionAndActions;
}
