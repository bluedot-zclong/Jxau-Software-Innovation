package cn.edu.jxau.web.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登陆认证拦截器
 * 
 * @author zclong 2017年9月30日
 */
public class LoginInterceptor implements HandlerInterceptor {

	// 忽略页面即不被拦截页面
	private List<String> exceptUrls;
	
	public List<String> getExceptUrls() {
		return exceptUrls;
	}
	
	public void setExceptUrls(List<String> exceptUrls) {
		this.exceptUrls = exceptUrls;
	}
	
	// 进入 Handler方法之前执行
	// 用于身份认证、身份授权
	// 比如身份认证，如果认证通过表示当前用户没有登陆，需要此方法拦截不再向下执行
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		/*
		 * // 获取请求的url String url = request.getRequestURI(); // 判断url是否是公开
		 * 地址（实际使用时将公开 地址配置配置文件中） // 这里公开地址是登陆提交的地址 if
		 * (url.indexOf("login.action") >= 0) { // 如果进行登陆提交，放行 return true; }
		 * 
		 * // 判断session HttpSession session = request.getSession(); //
		 * 从session中取出用户身份信息 String username = (String)
		 * session.getAttribute("username");
		 * 
		 * if (username != null) { // 身份存在，放行 return true; }
		 * 
		 * // 执行这里表示用户身份需要认证，跳转登陆页面
		 * request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(
		 * request, response);
		 */
		// return false表示拦截，不向下执行
		// return true表示放行
		
		//取得根目录所对应的绝对路径
		String currentUrl = request.getRequestURI();
		String contextPath = request.getContextPath();
		System.out.println(currentUrl);
		String targetUrl = currentUrl.substring(contextPath.length(), currentUrl.length());
		
		// 放行exceptUrls中配置的URL
		for(String exceptUrl : exceptUrls) {
			if(exceptUrl.equals(targetUrl)) {
				return true;
			}
		}
		
		// 获取session，判断当前页是否是登录页面，如果不是就做session的判断，防止出现未经过登录验证的非法访问
		HttpSession session = request.getSession();
		if(session == null || session.getAttribute("user_session") == null) {
			System.out.println("session为空");
			response.sendRedirect(request.getContextPath() + "/user/login");
//			request.getRequestDispatcher("/user/login").forward(request, response);
			return false;
		}else {
			return true;
		}
		
	}

	// 进入Handler方法之后，返回modelAndView之前执行
	// 应用场景从modelAndView出发：将公用的模型数据(比如菜单导航)在这里传到视图，也可以在这里统一指定视图
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("HandlerInterceptor1...postHandle");
	}

	// 执行Handler完成执行此方法
	// 应用场景：统一异常处理，统一日志处理
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		System.out.println("HandlerInterceptor1...afterCompletion");
	}

}
