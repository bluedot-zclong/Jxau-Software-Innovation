package cn.edu.jxau.web.exception;

/**
 * 系统自定义异常类，针对预期的异常，需要在程序中抛出此类的异常
 * @author zclong
 * 2017年9月30日
 */
public class CustomException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(Throwable cause) {
        super(cause);
    }

    public CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

