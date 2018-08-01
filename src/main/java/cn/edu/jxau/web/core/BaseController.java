package cn.edu.jxau.web.core;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import cn.edu.jxau.common.entity.Member;
import cn.edu.jxau.common.entity.User;
import cn.edu.jxau.common.entity.UserActive;
import cn.edu.jxau.common.utils.BeanUtil;
import cn.edu.jxau.common.utils.StringEscapeEditor;
import cn.edu.jxau.web.util.Result;
import cn.edu.jxau.web.util.WebUtils;
/**
 * BaseController
 * @author zclong
 * 2017年12月29日
 */
public abstract class BaseController{
	// 控制器本来就是单例，这样似乎更加合理
    protected Logger logger = LoggerFactory.getLogger(getClass());
    
    public static final String MULTIPART = "multipart/";

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;

    protected Cookie[] cookies;
    protected String userAgent;

    void init(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;

    }
    
    
    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        /**
         * 自动转换日期类型的字段格式
         */
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
        /**
         * 防止XSS攻击
         */
        binder.registerCustomEditor(String.class, new StringEscapeEditor());
    }
    
    /**
     * 判断该队员是否属于该用户
     * @param req
     * @param memberId
     * @return
     */
    public boolean getMember(HttpServletRequest req, Long memberId) {
    	UserActive loginUser = this.getSessionUser(req);
    	List<Member> lists = loginUser.getMembers();
    	for (Member member : lists) {
			if(member.getMemberId().equals(memberId)) {
				return true;
			}
		}
    	return false;
    }
    
    /**
     * 获取用户session
     * @param session
     * @return
     */
	@SuppressWarnings("unchecked")
	public UserActive getSessionUser(HttpServletRequest req) {
        return (UserActive) req.getSession().getAttribute("user_session");
    }
	
	/**
     * 获取User信息
     * @param session
     * @return
     */
	@SuppressWarnings("unchecked")
	public User getSessionLoginUser(HttpServletRequest req) {
		UserActive loginUser = this.getSessionUser(req);
    	if (loginUser != null) {
            return loginUser.getUser();
        }
        return null;
    }
    
    /**
     * 获取当前登录用户对象
     * @return {UserActive}
     */
    public User getLoginUser() {
        return (User) request.getSession().getAttribute("user_session");
    }
    
    /**
     * 获取当前登录用户id
     * @return {Long}
     */
    public Long getUserId(HttpServletRequest req) {
    	UserActive loginUser = this.getSessionUser(req);
    	if (loginUser != null) {
            return loginUser.getUser().getUserId();
        }
        return null;
    }
    
    /**
     * 获取当前登录用户名
     * @return {String}
     */
    public String getStaffName() {
        return this.getLoginUser().getUserName();
    }
    
    /**
     * ajax失败
     * @param msg 失败的消息
     * @return {Object}
     */
    public Object renderError(String msg) {
        Result result = new Result();
        result.setMsg(msg);
        return result;
    }
    
    /**
     * ajax成功
     * @return {Object}
     */
    public Object renderSuccess() {
        Result result = new Result();
        result.setSuccess(true);
        return result;
    }
    
    /**
     * ajax成功
     * @param msg 消息
     * @return {Object}
     */
    public Object renderSuccess(String msg) {
        Result result = new Result();
        result.setSuccess(true);
        result.setMsg(msg);
        return result;
    }

    /**
     * ajax成功
     * @param obj 成功时的对象
     * @return {Object}
     */
    public Object renderSuccess(Object obj) {
        Result result = new Result();
        result.setSuccess(true);
        result.setObj(obj);
        return result;
    }
    
    public <T> T getBean(Class<T> beanClass, HttpServletRequest req) {
        return BeanUtil.mapToBean(getParameterMap(req), beanClass);
    }

    public String getHeader(String name) {
        return request.getHeader(name);
    }

    public String getParameter(String name) {
        return request.getParameter(name);
    }

    public Enumeration<String> getParameterNames() {
        return request.getParameterNames();
    }

    public String[] getParameterValues(String name) {
        return request.getParameterValues(name);
    }

    public Map<String, String[]> getParameterMap(HttpServletRequest req) {
        return req.getParameterMap();
    }

    @SuppressWarnings("unchecked")
    public <T> T getRequestAttribute(String name) {
        return (T) request.getAttribute(name);
    }

    public void setRequestAttribute(String name, Object value) {
        request.setAttribute(name, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T getSessionAttribute(String name) {
        return (T) request.getSession().getAttribute(name);
    }

    public void setSessionAttribute(String name, Object value) {
        request.getSession().setAttribute(name, value);
    }



    /**
     * Get cookie object by cookie name.
     */
    public Cookie getCookieObject(String name) {
        Cookie[] cookies = getCookieObjects();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }
        return null;
    }

    /**
     * Get all cookie objects.
     */
    public Cookie[] getCookieObjects() {
        if (null == cookies) {
            cookies = request.getCookies();
        }
        return cookies != null ? cookies : new Cookie[0];
    }

    /**
     * Get cookie value by cookie name.
     */
    public String getCookie(String name, String defaultValue) {
        Cookie cookie = getCookieObject(name);
        return cookie != null ? cookie.getValue() : defaultValue;
    }

    /**
     * Get cookie value by cookie name.
     */
    public String getCookie(String name) {
        return getCookie(name, null);
    }

    /**
     * Set Cookie to response.
     *
     * @param name            cookie name
     * @param value           cookie value
     * @param maxAgeInSeconds -1: clear cookie when close browser. 0: clear cookie immediately.  n>0 : max age in n seconds.
     * @param path            see Cookie.setPath(String)
     * @param domain          the domain name within which this cookie is visible; form is according to RFC 2109
     * @param isHttpOnly      true if this cookie is to be marked as HttpOnly, false otherwise
     */
    public BaseController setCookie(String name, String value, int maxAgeInSeconds, String path, String domain, boolean isHttpOnly) {
        return doSetCookie(name, value, maxAgeInSeconds, path, domain, isHttpOnly);
    }

    /**
     * {@link #setCookie(String, String, int, String, String, boolean)}
     */
    public BaseController setCookie(String name, String value, int maxAgeInSeconds, String path, boolean isHttpOnly) {
        return doSetCookie(name, value, maxAgeInSeconds, path, null, isHttpOnly);
    }

    /**
     * {@link #setCookie(String, String, int, String, String, boolean)}
     */
    public BaseController setCookie(String name, String value, int maxAgeInSeconds, String path) {
        return doSetCookie(name, value, maxAgeInSeconds, path, null, null);
    }

    /**
     * {@link #setCookie(String, String, int, String, String, boolean)}
     */
    public BaseController setCookie(String name, String value, int maxAgeInSeconds, boolean isHttpOnly) {
        return doSetCookie(name, value, maxAgeInSeconds, null, null, isHttpOnly);
    }

    /**
     * {@link #setCookie(String, String, int, String, String, boolean)}
     */
    public BaseController setCookie(String name, String value, int maxAgeInSeconds) {
        return doSetCookie(name, value, maxAgeInSeconds, null, null, null);
    }

    private BaseController doSetCookie(String name, String value, int maxAgeInSeconds, String path, String domain, Boolean isHttpOnly) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAgeInSeconds);
        // set the default path value to "/"
        if (path == null) {
            path = "/";
        }
        cookie.setPath(path);

        if (domain != null) {
            cookie.setDomain(domain);
        }
        if (isHttpOnly != null) {
            cookie.setHttpOnly(isHttpOnly);
        }
        response.addCookie(cookie);
        return this;
    }
    

}