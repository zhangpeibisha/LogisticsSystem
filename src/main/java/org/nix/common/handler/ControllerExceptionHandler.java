package org.nix.common.handler;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.hibernate.exception.ConstraintViolationException;
import org.nix.Exception.*;
import org.nix.common.ReturnObject;
import org.nix.common.sysenum.ErrorCodeEnum;
import org.nix.util.ReturnUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {
    /**
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = WebException.class)
    public ReturnObject controllerHandle(WebException e) {
        e.printStackTrace();
        return ReturnUtil.fail(e.getCode(), e.getMessage(), null);
    }

    /**
     * 未知的错误，还没有被发现的错误
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ReturnObject exceptionHandle(Exception e) {
        e.printStackTrace();
        return ReturnUtil
                .fail(ErrorCodeEnum.ERROR_SYS_UNKONWN.getErrorCode()
                        , ErrorCodeEnum.ERROR_SYS_UNKONWN.getMessage()
                        , null);
    }

    /**
     * 查询数据库信息时，没有找到想要查找的信息而抛出的错误
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = SelectObjectException.class)
    public ReturnObject selectObjectExceptionHandle(SelectObjectException e) {
        e.printStackTrace();
        return ReturnUtil.fail(e.getErrorCode(), e.getErrorMessage(), null);
    }

    /**
     * 请求路径的方法不对，比如一个接口为POST请求，但是客户端使用了GET方法
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ReturnObject HttpRequestMethodNotSupportedExceptionHandle(HttpRequestMethodNotSupportedException e) {
        e.printStackTrace();
        return ReturnUtil.fail(ErrorCodeEnum.ERROR_SYS_REQUEST_METHON_NOT_SUPPORTED.getErrorCode(),
                ErrorCodeEnum.ERROR_SYS_REQUEST_METHON_NOT_SUPPORTED.getMessage(), null);
    }

    /**
     * 违反字段约束而抛出的异常
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ReturnObject constraintViolationExceptionHandle(ConstraintViolationException e) {
        e.printStackTrace();
        return ReturnUtil.fail(ErrorCodeEnum.ERROR_DB_CONSTRAINT_VIOLATION.getErrorCode(),
                ErrorCodeEnum.ERROR_DB_CONSTRAINT_VIOLATION.getMessage(), null);
    }

    /**
     * 将输入持久化到数据库时，主键重复而违反了数据库约束抛出的错误
     * @param e
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = MySQLIntegrityConstraintViolationException.class)
    public ReturnObject mySQLIntegrityConstraintViolationExceptionHandle(MySQLIntegrityConstraintViolationException e){
        e.printStackTrace();
        return ReturnUtil.fail(ErrorCodeEnum.ERROR_DB_CONSTRAINT_VIOLATION.getErrorCode(),
                ErrorCodeEnum.ERROR_DB_CONSTRAINT_VIOLATION.getMessage(), null);
    }


    @ResponseBody
    @ExceptionHandler(value = LoginErrorException.class)
    public ReturnObject loginErrorException(LoginErrorException e){
        e.printStackTrace();
        return ReturnUtil.fail(ErrorCodeEnum.ERROR_SYS_LOGIN.getErrorCode(),
                ErrorCodeEnum.ERROR_SYS_LOGIN.getMessage(), null);
    }

    @ResponseBody
    @ExceptionHandler(value = OrderProcessFlowException.class)
    public ReturnObject orderProcessFlowException(OrderProcessFlowException e){
        e.printStackTrace();
        return ReturnUtil.fail(ErrorCodeEnum.ERROR_SYS_ORDER_HANDLE_FLOW.getErrorCode(),
                ErrorCodeEnum.ERROR_SYS_ORDER_HANDLE_FLOW.getMessage(), null);
    }

    @ResponseBody
    @ExceptionHandler(value = PaymentException.class)
    public ReturnObject paymentException(PaymentException e){
        e.printStackTrace();
        return ReturnUtil.fail(ErrorCodeEnum.ERROR_SYS_PAYMENT.getErrorCode(),
                ErrorCodeEnum.ERROR_SYS_PAYMENT.getMessage(), null);
    }

    /**
     * 权限不足，被禁止使用
     * @param e
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = AuthInterceptorException.class)
    public ReturnObject authInterceptorException(AuthInterceptorException e){
        e.printStackTrace();
        return ReturnUtil.fail(ErrorCodeEnum.ERROR_SYS_AUTHINTERCEPTOR.getErrorCode(),
                ErrorCodeEnum.ERROR_SYS_AUTHINTERCEPTOR.getMessage(), null);
    }
}
