package cn.edu.jxau.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.jxau.common.entity.Member;
import cn.edu.jxau.common.entity.Student;
import cn.edu.jxau.common.entity.User;
import cn.edu.jxau.common.entity.UserActive;
import cn.edu.jxau.common.utils.EncryptUtil;
import cn.edu.jxau.service.MemberService;
import cn.edu.jxau.service.UserService;
import cn.edu.jxau.web.core.BaseController;
import cn.edu.jxau.web.util.CustomDateConverter;
import cn.edu.jxau.web.util.ValidUtils;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{

	private final static String USER_SESSION = "user_session";
	@Autowired
	private UserService userService;
	@Autowired
	private MemberService memberService;

	/*
     * 输入校验 1，创建一个Map,用来封装错误信息，其中key为表单字段名称，值为错误信息
     */
    private Map<String, String> errors = new HashMap<String, String>();
    

	@RequestMapping("/login")
	public @ResponseBody Object login(String username, String password, String vcode, String rememberMe, HttpServletRequest req) {

		/**
		 * 检验验证码
		 */
		if (!ValidUtils.vcodeVerify(vcode, req)) {
            return renderError("验证码错误!");
        }
		// 登录验证
		if (!ValidUtils.loginVerify(username, password)) {
			return renderError("信息不为空或者格式错误");
		}
		
//		Subject subject= SecurityUtils.getSubject();  
//        if (subject.isAuthenticated()) {  
//            return;  
//        }  
//        //如果用户已登录，先踢出  
//        ShiroSecurityHelper.kickOutUser(user.getUsername()); 
        
		User loginUser = new User();
		loginUser.setEmail(username);
		try {
//			loginUser = userService.findUserByEmail(username);
			loginUser = userService.selectUserBySid(Integer.parseInt(username));
			
			if(loginUser == null) {
				return renderError("用户名错误");
			}
			
			// 判断密码是否正确
			if (!EncryptUtil.md5Encode(password, loginUser.getPassword(), loginUser.getSalt())) {
				return renderError("密码错误");
			}
			
			// 是否记住我
			boolean rememberMe1 = false;
			if("true".equals(rememberMe)) {
				rememberMe1 = true;
			}
			Subject currentUser = SecurityUtils.getSubject();
			if (!currentUser.isAuthenticated()) {
				UsernamePasswordToken token = new UsernamePasswordToken(username, loginUser.getPassword(), rememberMe1);
				currentUser.login(token);
			}
		} catch (AuthenticationException e) {
			// logger.warn(e.getMessage());
			throw e;
		}

		// 将密码设置为null，确保安全
		loginUser.setPassword(null);
		loginUser.setSalt(null);

		saveSessionUser(loginUser, req.getSession());
		return renderSuccess(loginUser.getUserName() + "登录成功" + (loginUser.getRoleId()==3?"t":"f"));
	}
    
	/**
	 * 注册
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody Object register(@RequestBody User user, HttpServletRequest req) {
		if(user == null) {
			return renderError("注册失败！");
		}
		
		if(userService.selectUserBySid(user.getStudentNumber()) != null) {
			return renderError("该用户已存在！");
		}
		
		// 注册时间
        Date gmtModified = CustomDateConverter.getNowDate();
        user.setGmtModified(gmtModified);
        user.setGmtCreate(gmtModified);
        //对密码进行加密
        Map<String, String> info = EncryptUtil.md5Decode(user.getPassword());
        user.setPassword(info.get("password"));
        user.setSalt(info.get("salt"));
        user.setStartyear(Integer.parseInt((user.getStudentNumber()+"").substring(0, 4)));
        user.setMajor(user.getClasss().substring(0, 4));
        user.setEnrollType(0);
        user.setRoleId(1l);
        user.setSubmitStatus(0);
        if (userService.register(user)) {
            return renderSuccess("注册成功!");
        }
        return renderError("注册失败!");
    }
	
	@RequestMapping("/findUserList")
	@RequiresPermissions("/user/findUserList") // 执行findUserList方法需要/user/findUserList权限
    public String findUserList(Model model, HttpServletRequest request) throws Exception {
    	if(userService == null) {
    		System.out.println("null");
    	}else {
    		List<User> userList = userService.findUserList();
    		if(userList != null) {
    			model.addAttribute("userList", userList);
    		}
    	}
        return "user/listUser";
    }
	
	@RequestMapping("/findUserInfo")
	@RequiresPermissions("/user/findUserInfo")
    public void findUserInfo(Model model, HttpServletRequest req) throws Exception {
    	if(userService == null) {
    		System.out.println("null");
    	}else {
    		User userInfo = userService.findUserById(getUserId(req));
    		if(userInfo != null) {
    			model.addAttribute("user", userInfo);
    		}
    	}
    	//如果页面名和请求名一致可不写，默认转发
//    	return "user/findUserInfo";
    }
	
	/**
	 * 已登录用户查看个人信息
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/userView")
	public String loginUserView(HttpServletRequest request) throws Exception {
		UserActive loginUser = getSessionUser(request);
        if (loginUser == null) {
            return "view/login";
        }
		return "user/modifyUserInfo";
	}
	
	/**
	 * 使用RESTful风格,查看个人信息
	 * 2017年11月14日
	 * zclong
	 * @param userId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userView/{userId}", method = RequestMethod.GET)
//	@RequiresPermissions("/user/findUserInfo") // 执行findUserInfo方法需要/user/findUserInfo权限
	public String userView(@PathVariable("userId") Long userId, Model model) throws Exception {
		User userInfo = userService.findUserById(userId);
		if(userInfo != null) {
			model.addAttribute("user", userInfo);
		}
		return "user/modifyUserInfo";
	}
	
	/**
	 * 更新用户信息
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/modifyInfo", method = RequestMethod.POST)
    public @ResponseBody Object updateInfo(@RequestBody User user, HttpServletRequest req) {
		Long userId = getUserId(req);
		if(userId == null) {
			return renderError("请先登录！");
		}
        user.setUserId(userId);
        // 修改时间
        Date gmtModified = CustomDateConverter.getNowDate();
        user.setGmtModified(gmtModified);
        if (userService.updateUser(user)) {
            saveSessionUser(userService.findUserById(user.getUserId()), req.getSession());
            return renderSuccess("更新信息成功");
        }
        return renderError("更新信息失败!");
    }
	
	/**
	 * 修改密码
	 * @param password
	 * @param newPassword
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
    public @ResponseBody Object modifyPassword(String oldPassword, String newPassword, String rePassword, String vcode, HttpServletRequest req) {
        if (!ValidUtils.vcodeVerify(vcode, req)) {
            return renderError("验证码错误!");
        }
        User loginUser = userService.findUserById(getUserId(req));
        if (!EncryptUtil.md5Encode(oldPassword, loginUser.getPassword(), loginUser.getSalt())) {
            return renderError("原密码错误!");
        }
        if (StringUtils.isBlank(newPassword)) {
            return renderError("新密码不能为空!");
        }
        if (oldPassword.equals(newPassword)) {
            return renderError("原密码与新密码相同");
        }
        if (!newPassword.equals(rePassword)) {
            return renderError("两次密码不一致!");
        }
        if (userService.modifyPassword(loginUser.getUserId(), newPassword)) {
//        	req.getSession().invalidate();
            return renderSuccess("修改密码成功,请重新登录");
        }
        return renderError("修改密码失败!");
    }
	
	/**
	  * 根据学号通过学生信息表查询学生信息
	  * @param sid
	  * @return
	  */
	@RequestMapping("/queryStudent")
	public @ResponseBody Object queryStudent(Integer sid, HttpServletRequest req) {
		
		Student stu = userService.selectStuBySid(sid);
		
        if (stu == null) {
            return renderError("查询无果，请手动输入!");
        }
        
        return renderSuccess(stu);
	}
	
	/**
	  * 根据邮箱通过学生信息表查询学生信息
	  * @param sid
	  * @return
	  */
	@RequestMapping("/queryEmail")
	public @ResponseBody Object queryEmail(String email, HttpServletRequest req) {
		
		User loginUser = userService.findUserByEmail(email);
       if (loginUser != null) {
           return renderError("该邮箱已被注册!");
       }
       return renderSuccess();
	}
	
	/**
	  * 忘记密码
	  * @param sid
	  * @return
	  */
	@RequestMapping("/forgetPassword")
	public @ResponseBody Object forgetPassword(String email, String tel, HttpServletRequest req) {
	
		if (!ValidUtils.forgetVerify(email, tel)) {
			return renderError("信息不为空或者格式错误");
		}
		
	   User loginUser = userService.findUserByEmail(email);
	   
       if (loginUser == null || !tel.equals(loginUser.getTel())) {
           return renderError("信息错误，请重新输入!");
       }
       
       String newPassword = UUID.randomUUID().toString().substring(0, 6);
       if (userService.modifyPassword(loginUser.getUserId(), newPassword)) {
           return renderSuccess("你的新密码为"+newPassword+",如有需要请及时修改");
       }
       return renderError("操作有误，请重试！");
	}
	
	/**
	 * 存储用户信息到session中
	 * @param user
	 * @param session
	 */
	private void saveSessionUser(User user, HttpSession session) {
		UserActive userActive = new UserActive();
		Long userId = user.getUserId();
		List<Member> members = memberService.queryMemberByUserId(userId);
		userActive.setMember(null);
		// 单独存储老师信息
		for (Member member : members) {
			if(member.getMemberRole() == 1) {
				userActive.setMember(member);
			}
		}
		userActive.setUser(user);
		userActive.setMembers(members);
		userActive.setId(userId);
		userActive.setLoginName(user.getUserName());
		
		// 存储用户信息
		session.setAttribute(USER_SESSION, userActive);
	}
	
	
}
