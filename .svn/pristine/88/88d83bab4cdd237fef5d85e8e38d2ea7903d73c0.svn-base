package cn.edu.jxau.web.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import cn.edu.jxau.web.util.ResultUtil;

/**
 * 全局异常处理器
 * 
 * @author zclong 2017年9月30日
 */
@ControllerAdvice(annotations = Controller.class)
public class CustomExceptionResolver {

	private static final Logger logger = LoggerFactory.getLogger(CustomExceptionResolver.class);

    /**
     * 统一异常处理
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object resolveException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        if (ex instanceof CustomException) {
            logger.warn(ex.getMessage());
            return handlerError(ex.getMessage(), 500, request, response);
        } else if (ex instanceof ServletRequestBindingException ||
                ex instanceof HttpMessageConversionException) {
            logger.warn("{}:{}", ex.getClass().getName(), ex.getMessage());
            return ResultUtil.renderError("参数错误!");
        } else if (ex instanceof NoHandlerFoundException) {
            return handlerError("请求的资源不存在!", 404, request, response);
        } else if (ex instanceof AuthorizationException) {// 授权
            return handlerError("拒绝请求权限不足!", 403, request, response);
        } else if (ex instanceof AuthenticationException) {// 认证
            return handlerError("请求需要身份验证!", 401, request, response);
        }

        logger.error(ex.getMessage(), ex);
        return handlerError("系统错误!", 500, request, response);
    }

    private Object handlerError(String msg, int errorCode, HttpServletRequest request, HttpServletResponse response) {
        showUrl(request, errorCode);
        if (ResultUtil.isAjax(request)) {
            return ResultUtil.renderError(msg);
        }
        response.setStatus(errorCode);
        ModelAndView mv = new ModelAndView("/error/" + errorCode);
        mv.setStatus(HttpStatus.valueOf(errorCode));
        mv.addObject("message", msg);
        return mv;
    }

    static void showUrl(HttpServletRequest request, int errorCode) {
        if (logger.isDebugEnabled()) {
            logger.debug("{}:{}?{} -> {}", request.getMethod(), request.getRequestURI(), request.getQueryString(), errorCode);
        }
    }
}
