package cn.edu.jxau.dao.mapper;

import cn.edu.jxau.common.entity.Student;
import cn.edu.jxau.dao.mapper.base.Mapper;

public interface StudentMapper extends Mapper<Student> {
	Student selectBySid(Integer studentNumber);
	
}