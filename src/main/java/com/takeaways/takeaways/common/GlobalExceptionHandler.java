package com.takeaways.takeaways.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@ControllerAdvice(annotations = {Controller.class})
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> handleException(Exception e){
        log.error(e.getMessage(), e);
        if (e.getMessage().contains("Duplicate entry")){
            String[] s = e.getMessage().split(" ");
            return R.error("-------------"+s[2]+"已重复-------------------");
        }
        return R.error("为止错误");
    }
}
