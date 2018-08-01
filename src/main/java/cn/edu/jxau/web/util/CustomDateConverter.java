package cn.edu.jxau.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class CustomDateConverter implements Converter<String, Date>{
	
	public Date convert(String source) {
		//实现将日期串转化成日期类型（格式为yyyy-MM-dd HH:mm:ss）
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			//转换成功直接返回
			return simpleDateFormat.parse(source);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//如果参数绑定失败返回null
		return null;
	}
	
	/**
	 * 获取系统当前时间
	 * 2017年12月28日
	 * zclong
	 * @param date
	 * @return
	 */
	public static Date getNowDate() {
		Date now = new Date(); 
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowStr = simpleDateFormat.format(now);
		CustomDateConverter customDateConverter = new CustomDateConverter();
		Date date = customDateConverter.convert(nowStr);
		return date;
	}
}
