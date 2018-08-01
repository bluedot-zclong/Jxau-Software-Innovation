package cn.edu.jxau.web.interceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.edu.jxau.common.utils.JsonUtil;

/**
 * 一个用户 相同url 同时提交 相同数据 验证 主要通过 session中保存到的url 和 请求参数。如果和上次相同，则是重复提交表单
 * 
 * @author zclong 2017年12月18日
 */
public class SameUrlDataInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			SameUrlData annotation = method.getAnnotation(SameUrlData.class);
			if (annotation != null) {
				// 如果重复相同数据
				if (repeatDataValidator(request)) {
					// request.getRequestDispatcher("user/contestant").forward(request,
					// response);
					//取得根目录所对应的绝对路径
					String currentUrl = request.getRequestURI();
					String contextPath = request.getContextPath();
					System.out.println(currentUrl);
					String targetUrl = currentUrl.substring(contextPath.length(), currentUrl.length());
					String path = "/WEB-INF/pages" + targetUrl + ".html";
					request.getRequestDispatcher("/WEB-INF/pages" + targetUrl + ".html").forward(request, response);
					return false;
				} else {
					return true;
				}
			} else {
				return true;
			}
		} else {
			return super.preHandle(request, response, handler);
		}
	}

	/**
	 * 验证同一个url数据是否相同提交 ,相同返回true 2017年12月18日 zclong
	 * 
	 * @param req
	 * @return
	 */
	public boolean repeatDataValidator(HttpServletRequest req) {
		String params = JsonUtil.toJson(req.getParameterMap());
		System.out.println(params);
		String url = req.getRequestURI();
		Map<String, String> map = new HashMap<String, String>();
		map.put(url, params);
		String nowUrlParams = map.toString();

		Object preUrlParams = req.getSession().getAttribute("repeatData");

		// 如果上一个数据为null,表示还没有访问页面
		if (preUrlParams == null) {
			req.getSession().setAttribute("repeatData", nowUrlParams);
			return false;
		} else {
			System.out.println(preUrlParams.toString());
			// 如果上次 url+数据 和 本次url+数据 相同，则表示重复请求
			if (preUrlParams.toString().equals(nowUrlParams)) {
				return true;
			} else {
				req.getSession().setAttribute("repeatData", nowUrlParams);
				return false;
			}
		}

	}

}
