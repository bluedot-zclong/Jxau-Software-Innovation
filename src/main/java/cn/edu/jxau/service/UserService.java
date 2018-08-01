package cn.edu.jxau.service;

import java.util.List;

import cn.edu.jxau.common.entity.Function;
import cn.edu.jxau.common.entity.Role;
import cn.edu.jxau.common.entity.Student;
import cn.edu.jxau.common.entity.User;

public interface UserService {

	/**
	 * 根据用户id查询用户信息
	 * 2017年11月5日
	 * zclong
	 * @param id
	 * @return
	 */
	 User findUserById(Long id);
	
	 List<User> findUserList();
	
	 User login(User loginUser);
	
	 /**
	  * 注册
	  * @param userInfo
	  * @return
	  */
	 boolean register(User userInfo);
	
	 /**
	  * 修改用户信息
	  * @param userFrom
	  * @return
	  */
	 boolean updateUser(User userFrom);
	 
	 /**
	  * 修改用户密码
	  * @param userId
	  * @param password
	  * @return
	  */
	 boolean modifyPassword(Long userId, String password);
	
	/**
	 * 通过邮箱查询用户信息
	 * 2017年12月21日
	 * zclong
	 * @param email
	 * @return
	 * @throws Exception
	 */
	 User findUserByEmail(String email);
	 
	 /**
	 * 通过学号查询用户信息
	 * 2017年12月21日
	 * zclong
	 * @param email
	 * @return
	 * @throws Exception
	 */
	 User findUserBySid(Integer studentNumber);
	 User selectUserBySid(Integer studentNumber);
	/**
     * 根据userID查询对应的功能权限
     * 2017年12月21日
     * zclong
     * @param userId
     * @return
     */
	 List<Function> selectFunctionByUserId(Long userId);
	
	/**
	 * 根据userID查询对应的角色
	 * 2017年12月31日
	 * zclong
	 * @param userId
	 * @return
	 */
	 List<Role> selectRoleByUserId(Long roleId);
	 
	 /**
	  * 根据学号通过学生信息表查询学生信息
	  * @param sid
	  * @return
	  */
	 Student selectStuBySid(Integer sid);
	 
}
