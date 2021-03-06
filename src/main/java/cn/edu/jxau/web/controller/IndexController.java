package cn.edu.jxau.web.controller;


import java.util.Properties;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.jxau.web.core.BaseController;
import cn.edu.jxau.web.exception.CustomException;
import cn.edu.jxau.web.util.VerifyCodeUtils;
import cn.edu.jxau.web.util.vcode.Captcha;
import cn.edu.jxau.web.util.vcode.GifCaptcha;

@Controller
public class IndexController extends BaseController{

	private Properties views;

    public void setViews(Properties views) {
        logger.debug("views:{}", views);
        this.views = views;
    }
    
    @GetMapping("/error/{code}")
    public String error(@PathVariable(required = false) String code, HttpServletResponse response) {
        logger.debug("code:{}", code);
        try {
            Integer keyCode = Integer.valueOf(code);
            String view = views.getProperty(code, "/error/404");
            if ("/error/404".equals(view)) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else {
                response.setStatus(keyCode);
            }
            return view;
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "/error/404";
        }
    }
    
    @GetMapping("/view/{v}")
    public String view(@PathVariable(value = "v", required = false) String v) {
        String view = views.getProperty(v, "/error/404");
        logger.debug("v:{}, view:[{}]", v, view);
        return view;
    }
	
	/**
     * 首页
     *
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(Model model) {
        return "user/login";
    }
    
//    @GetMapping("/view/login")
//    public String loginView() {
//        return "user/login";
//    }
    
    /**
	 * 退出
	 * @param request
	 * @return
	 */
	@GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        SecurityUtils.getSubject().logout();
        request.getSession().invalidate();
        return "redirect:/view/login";
    }
    
    /**
	 * 获取验证码（Gif版本）
	 * @param response
	 * @throws CustomException 
	 */
    @GetMapping("/user/getGifCode")
	public void getGifCode(HttpServletResponse resp,HttpServletRequest req) throws CustomException {
		try {
			//存入session  
	        HttpSession session = req.getSession(); 
	        
			resp.setHeader("Pragma", "No-cache");  
			resp.setHeader("Cache-Control", "no-cache");  
			resp.setDateHeader("Expires", 0);  
			resp.setContentType("image/gif");  
	        /**
	         * gif格式动画验证码
	         * 宽，高，位数。
	         */
	        Captcha captcha = new GifCaptcha(146,42,4);
	        //输出
	        ServletOutputStream out = resp.getOutputStream();
	        captcha.out(out);
	        out.flush();
	        
	        session.setAttribute(VerifyCodeUtils.V_CODE, captcha.text().toLowerCase());  
		} catch (Exception e) {
			throw new CustomException("获取验证码异常！");
		}
	}
}