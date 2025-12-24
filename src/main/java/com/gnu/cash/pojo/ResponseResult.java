package com.gnu.cash.pojo;

import lombok.Data;

@Data
public class ResponseResult<T> {
    private Boolean success;
    private String message;
    private T data;
    
    // 成功响应
    public static <T> ResponseResult<T> success(T data) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setSuccess(true);
        result.setMessage("成功");
        result.setData(data);
        return result;
    }
    
    // 错误响应
    public static <T> ResponseResult<T> error(String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setSuccess(false);
        result.setMessage(message);
        return result;
    }
    
    // getter/setter
}