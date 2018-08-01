package cn.edu.jxau.web.interceptor;

import java.lang.reflect.Method;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * token拦截器接口
 * 
 * @author zclong 2018年1月2日
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {
	private static final Logger LOG = Logger.getLogger(Token.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			Token annotation = method.getAnnotation(Token.class);
			if (annotation != null) {
				boolean needSaveSession = annotation.save();
				if (needSaveSession) {
					SecurityUtils.getSubject().getSession().setAttribute("token", UUID.randomUUID().toString());
					// request.getSession(true).setAttribute("token", UUID.randomUUID().toString());
				}
				boolean needRemoveSession = annotation.remove();
				if (needRemoveSession) {
					if (isRepeatSubmit(request)) {
						LOG.debug(request.getHeader("referer"));
						LOG.warn("请不要重复提交,url:" + request.getServletPath());
						String retUrl = request.getHeader("referer");
						if(retUrl != null){   
						    response.sendRedirect(retUrl);   
						}
						return false;
					}
//					request.getSession(true).removeAttribute("token");
					SecurityUtils.getSubject().getSession().removeAttribute("token");
				}
			}
			return true;
		} else {
			return super.preHandle(request, response, handler);
		}
	}

	private boolean isRepeatSubmit(HttpServletRequest request) {
		String serverToken = (String) SecurityUtils.getSubject().getSession().getAttribute("token");
		if (serverToken == null) {
			return true;
		}
		String clinetToken = request.getParameter("token");
		if (clinetToken == null) {
			return true;
		}
		if (!serverToken.equals(clinetToken)) {
			return true;
		}
		return false;
	}
}