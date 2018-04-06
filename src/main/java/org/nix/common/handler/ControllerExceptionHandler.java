package org.nix.common.handler;

import org.nix.Exception.WebException;
import org.nix.common.ReturnObject;
import org.nix.util.ReturnUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = WebException.class)
    public ReturnObject controllerHandle(WebException e) {
        e.printStackTrace();
        return ReturnUtil.fail(e.getCode(),e.getMessage(),null);
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ReturnObject exceptionHandle(Exception e) {
        e.printStackTrace();
        return ReturnUtil.fail(-1,"未知错误",null);
    }
}
