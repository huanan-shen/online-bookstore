package org.anthony.bs.advice;

import org.anthony.bs.common.BsResponse;
import org.anthony.bs.exception.BsException;
import org.anthony.bs.interceptor.LoginInterceptor;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * trans 2 standard error response
 *
 * @author Anthony
 */
@ControllerAdvice
@ResponseBody
public class BsExceptionAdvice {

    private static final Logger logger = Logger.getLogger(BsExceptionAdvice.class);

    @ExceptionHandler(Throwable.class)
    public BsResponse doBsException(Throwable e) {
        BsResponse bsResponse;
        if (e instanceof BsException) {
            BsException bsException = (BsException) e;
            bsResponse = BsResponse.ofFailed(bsException.getErrCode());
        } else {
            bsResponse = BsResponse.ofFailed(e);
        }
        logger.error(String.format("request err:%s", bsResponse.getErrMsg()), e);
        return bsResponse;
    }

}
