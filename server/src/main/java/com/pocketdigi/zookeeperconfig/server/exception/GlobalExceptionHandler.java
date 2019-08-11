package com.pocketdigi.zookeeperconfig.server.exception;

import com.pocketdigi.zookeeperconfig.server.model.ErrorEnum;
import com.pocketdigi.zookeeperconfig.server.vo.ResultVO;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Web异常拦截.
 * @author hugh
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public ResultVO exceptionHandler(HttpServletRequest request, Exception e) {
        log.error("未处理异常:", e);
        return ResultVO.wrapError(ErrorEnum.UNKNOWN);
    }

    /**
     * 参数校验不过
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultVO argumentNotValid(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        Optional<String> messageOptional = fieldErrors.stream()
            .map(fieldError -> fieldError.getField()+":"+fieldError.getDefaultMessage())
            .reduce((s, s2) -> String.join(";", s, s2));

        log.info("参数校验不过:{}",messageOptional.toString());
        return messageOptional.map(s -> ResultVO.wrapError(ErrorEnum.CLIENT_PARAM_ERROR.getErrorCode(), s))
            .orElseGet(() -> ResultVO.wrapError(ErrorEnum.CLIENT_PARAM_ERROR));

    }
    /**
     * 参数为空
     * @param e
     * @return
     */
    @ExceptionHandler(value = ServletRequestBindingException.class)
    @ResponseBody
    public ResultVO requestBindingException(ServletRequestBindingException e) {
        log.info("客户端错误请求:{}",e.getMessage());
        return ResultVO.wrapError(ErrorEnum.CLIENT_PARAM_NULL);
    }

    /**
     * 业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public ResultVO bizException(BizException e) {
        log.info("业务异常,输出code {},message:{}",e.getCode(),e.getMessage());
        return ResultVO.wrapError(e.getCode(),e.getMessage());
    }


    /**
     * 业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResultVO httpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        log.info(e.getMessage());
        return ResultVO.wrapError(ErrorEnum.CLIENT_METHOD_ERROR.getErrorCode(),"该接口不支持"+e.getMethod()+"请求");
    }

}
