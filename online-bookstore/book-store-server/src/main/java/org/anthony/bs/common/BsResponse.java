package org.anthony.bs.common;

import lombok.Data;
import org.anthony.bs.utils.SpringContextUtils;

import java.util.Date;
import java.util.Locale;

/**
 * @param <T>
 * @author Anthony
 */
@Data
public class BsResponse<T> {
    private final static int FAILED_CODE = 400;

    private final static int SUCCESS_CODE = 200;

    private final static String FAILED = "FAILED";

    private final static String SUCCESS = "SUCCESS";


    /**
     * status code,200:success,400:failed
     */
    private Integer status;
    /**
     * description
     */
    private String description;
    /**
     * error code
     */
    private String errorCode;
    /**
     * error message
     */
    private String errMsg;
    /**
     * response data
     */
    private T data;
    /**
     * response time
     */
    private Date timestamp = new Date();

    private BsResponse() {

    }

    public static BsResponse ofFailed(String errorCode) {
        BsResponse result = new BsResponse();
        result.setStatus(FAILED_CODE);
        result.setDescription(FAILED);
        result.setErrorCode(errorCode);
        result.setErrMsg(SpringContextUtils.getApplicationContext().getMessage(errorCode, null, Locale.getDefault()));
        return result;
    }

    public static BsResponse ofFailed(Throwable e) {
        BsResponse result = new BsResponse();
        result.setStatus(FAILED_CODE);
        result.setDescription(FAILED);
        result.setErrMsg(e.getMessage());
        return result;
    }

    public static BsResponse ofSuccess(Object data) {
        BsResponse result = new BsResponse();
        result.setStatus(SUCCESS_CODE);
        result.setDescription(SUCCESS);
        result.setData(data);
        return result;
    }

}
