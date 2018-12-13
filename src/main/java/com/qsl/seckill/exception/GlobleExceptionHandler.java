package com.qsl.seckill.exception;

import com.mysql.fabric.Server;
import com.qsl.seckill.common.ResponseCode;
import com.qsl.seckill.common.ServerResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author DanielQSL
 * @date 2018-10-09
 */
@ControllerAdvice
@ResponseBody
public class GlobleExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ServerResponse<String> exceptionHandler(HttpServletRequest request, Exception e) {
        if (e instanceof BindException) {
            BindException ex = (BindException) e;
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);
            String defaultMessage = error.getDefaultMessage();
            return ServerResponse.createByErrorMessage(defaultMessage);
        } else {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.SERVER_ERROR.getCode(), ResponseCode.SERVER_ERROR.getDesc());
        }
    }
}
