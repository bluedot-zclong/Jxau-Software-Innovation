package cn.edu.jxau.web.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * 自定义注解
 * 一个用户 相同url 同时提交 相同数据 验证 
 * @author zclong
 * 2017年12月18日
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SameUrlData {
	
}
