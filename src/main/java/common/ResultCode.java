package common;

import lombok.Getter;

/**
 * @Description: 枚举了一些常用API操作码
 * @author: rengb
 * @time: 2022/8/19 10:33
 */
@Getter
public enum ResultCode implements IErrorCode {

    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    WITHOUT_TOKEN(5002, "没有Token"),
    VALIDATE_FAILED(5001, "参数检验失败"),

    TOKEN_EXPIRED(5003, "token失效"),

    API_CALL_FAILED(5004, "第三方接口调用失败"),
    NOT_IN_MEAL_TIME(6001, "当前时间不在用餐时间段内");



    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }
}