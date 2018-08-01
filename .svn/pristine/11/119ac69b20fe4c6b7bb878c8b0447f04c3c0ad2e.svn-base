package cn.edu.jxau.web.authc;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.jxau.common.entity.Function;
import cn.edu.jxau.common.entity.User;
import cn.edu.jxau.common.entity.UserActive;
import cn.edu.jxau.service.UserService;

/**
 * 自定义realm
 * 
 * @author zclong 2017年12月20日
 */
public class SsmsAuthorizingRealm extends AuthorizingRealm {
	private static final Logger LOGGER = LogManager.getLogger(SsmsAuthorizingRealm.class);
	
	@Autowired
	private UserService userService;

	// realm的认证方法，从数据库查询用户信息
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		LOGGER.info("Shiro开始登录认证");
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        Object principal = upToken.getUsername();
        Object credentials = upToken.getPassword();
        String realmName = getName();
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, realmName);
        return info;
	}

	// 用于授权
	@SuppressWarnings("unused")
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		// 从 principals获取主身份信息
		// 将getPrimaryPrincipal方法返回值转为真实身份类型（在上边的doGetAuthenticationInfo认证通过填充到SimpleAuthenticationInfo中身份类型），
		Object principal =  principals.getPrimaryPrincipal();
		
		User user = userService.findUserByEmail((String) principal);
		// 根据身份信息获取权限信息
		// 从数据库获取到权限数据
		List<Function> functions = null;
		try {
			// 通过service取出菜单
			functions = userService.selectFunctionByUserId(user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 单独定一个集合对象
		List<String> permissions = new ArrayList<String>();
		if (functions != null) {
			for (Function function : functions) {
				// 将数据库中的权限标签 符放入集合
				permissions.add(function.getFunctionUrl());
			}
		}

		// 查到权限数据，返回授权信息(要包括 上边的functionList)
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		// 将上边查询到授权信息填充到simpleAuthorizationInfo对象中
		simpleAuthorizationInfo.addStringPermissions(permissions);

		return simpleAuthorizationInfo;
	}


	public void clearCached() {
		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
		super.clearCache(principals);
	}
}
