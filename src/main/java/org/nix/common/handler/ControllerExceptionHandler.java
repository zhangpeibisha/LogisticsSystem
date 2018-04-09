package org.nix.common.handler;

import org.nix.Exception.SelectObjectException;
import org.nix.Exception.WebException;
import org.nix.common.ReturnObject;
import org.nix.common.sysenum.ErrorCodeEnum;
import org.nix.util.ReturnUtil;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = WebException.class)
    public ReturnObject controllerHandle(WebException e) {
        e.printStackTrace();
        return ReturnUtil.fail(e.getCode(), e.getMessage(), null);
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ReturnObject exceptionHandle(Exception e) {
        e.printStackTrace();
        return ReturnUtil
                .fail(ErrorCodeEnum.ERROR_SYS_UNKONWN.getErrorCode()
                        , ErrorCodeEnum.ERROR_SYS_UNKONWN.getMessage()
                        , null);
    }

    @ResponseBody
    @ExceptionHandler(value = SelectObjectException.class)
    public ReturnObject selectObjectExceptionHandle(SelectObjectException e) {
        e.printStackTrace();
        return ReturnUtil.fail(e.getErrorCode(), e.getErrorMessage(), null);
    }

    @ResponseBody
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ReturnObject HttpRequestMethodNotSupportedExceptionHandle(HttpRequestMethodNotSupportedException e){
        e.printStackTrace();
        return ReturnUtil.fail(ErrorCodeEnum.ERROR_SYS_REQUEST_METHON_NOT_SUPPORTED.getErrorCode(),
                ErrorCodeEnum.ERROR_SYS_REQUEST_METHON_NOT_SUPPORTED.getMessage(),null);
    }
}
