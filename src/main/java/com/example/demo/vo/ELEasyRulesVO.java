package com.example.demo.vo;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.RuleWrapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: EasyRulesVO
 * @Author: sxl
 * @Description:
 * @Date: 2022/9/26 14:18
 * @Version: 1.0
 */
@Data
public class ELEasyRulesVO {
    @ApiModelProperty("事实")
    private JSONObject facts;

    @ApiModelProperty("规则集合")
    private List<RuleWrapper> rules;

    @ApiModelProperty("规则监听")
    private List<String> listenersPath;
}
