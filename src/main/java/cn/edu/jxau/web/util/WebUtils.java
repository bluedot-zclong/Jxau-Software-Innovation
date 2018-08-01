package cn.edu.jxau.web.util;


import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import cn.edu.jxau.web.core.HttpMethod;

/**
 * WebUtils
 */
public class WebUtils {

    public static final String MULTIPART = "multipart/";

    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

    public static boolean isIE(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        return isIE(userAgent);
    }

    public static boolean isIE(String userAgent) {
        return userAgent.contains("MSIE ") || userAgent.contains("Trident/");
    }

    /**
     * 判断是否为异步请求
     */
    public static boolean isAjax(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(header)) {
            return true;
        }
        return false;
    }

    /**
     * multipart
     */
    public static boolean isMultipartContent(HttpServletRequest request) {
        if (HttpMethod.POST.name().equals(request.getMethod().toUpperCase())) {
            String contentType = request.getContentType();
            if(contentType!=null)
            if (contentType.toLowerCase(Locale.ENGLISH).startsWith(MULTIPART)) {
                return true;
            }
        }
        return false;
    }

}
