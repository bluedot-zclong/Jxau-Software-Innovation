package cn.edu.jxau.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.jxau.common.entity.Function;
import cn.edu.jxau.common.entity.Role;
import cn.edu.jxau.common.entity.Student;
import cn.edu.jxau.common.entity.User;
import cn.edu.jxau.common.utils.EncryptUtil;
import cn.edu.jxau.dao.mapper.RoleMapper;
import cn.edu.jxau.dao.mapper.StudentMapper;
import cn.edu.jxau.dao.mapper.UserMapper;
import cn.edu.jxau.dao.mapper.UserRoleFunctionMapper;
import cn.edu.jxau.dao.mapper.UsersMapper;
import cn.edu.jxau.service.UserService;
import cn.edu.jxau.web.exception.CustomException;
import cn.edu.jxau.web.util.CustomDateConverter;
import cn.edu.jxau.web.util.ValidUtils;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private UserRoleFunctionMapper userRoleFunctionMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private StudentMapper studentMapper;
	
	@Override
	@Cacheable(value="user", key="#userId") // 对用户信息缓存
	public User findUserById(Long userId) {
		User userInfo = userMapper.selectByPrimaryKey(userId);
		//处理异常
		if(userInfo == null) {
			throw new CustomException("查询的用户" + userId + "不存在");
		}
		return userInfo;
	}
	
	@Override
	public List<User> findUserList() {
		
		return usersMapper.findUserList();
	}

	@Override
	public User login(User loginUser) {
		List<User> userlist = usersMapper.select(loginUser);
		if(userlist.size() != 1) {
			throw new CustomException("用户名或密码错误!");
		}
		
		User user = userlist.get(0);
		if(!user.getPassword().equals(loginUser.getPassword())) {
			throw new CustomException("密码错误!");
		}
		return user;
	}

	/**
	 * 注册
	 */
	@Override
	public boolean register(User userInfo) {
		int flag = userMapper.insert(userInfo);
		if(flag == 1) {
			return true;	
		}
		return false;
	}

	/**
	 * 修改用户信息
	 */
	@Transactional
    @CacheEvict(value = "user", key = "#user.userId") // 更新缓存
	public boolean updateUser(User user) {
		if (1 == userMapper.updateByPrimaryKeySelective(user)) {
            return true;
        }
        return false;
	}
	
	/**
	 * 修改用户密码
	 * @param userId
	 * @param password
	 * @return
	 */
	@Transactional
    public boolean modifyPassword(Long userId, String password) {
        Map<String, String> map = EncryptUtil.md5Decode(password);
        password = map.get("password");
        String salt = map.get("salt");
		
		User user = new User();
        user.setUserId(userId);
        user.setPassword(password);
        user.setSalt(salt);
        // 修改时间
        Date gmtModified = CustomDateConverter.getNowDate();
        user.setGmtModified(gmtModified);
        return updateUser(user);
    }
	
	
	/**
	 * 根据邮箱查询用户是否存在，用于shiro的登录认证
	 */
	@Override
	public User findUserByEmail(String email) {
		User userInfo = usersMapper.selectByEmail(email);
		return userInfo;
	}

	/**
	 * 根据用户id查询用户的功能权限，用于shiro的权限认证
	 */
	@Override
	public List<Function> selectFunctionByUserId(Long userId) throws CustomException {
		List<Function> functions = userRoleFunctionMapper.selectFunctionByUserId(userId);
		return functions;
	}

	@Override
	public List<Role> selectRoleByUserId(Long roleId) throws CustomException {
		List<Role> roles = (List<Role>) roleMapper.selectByPrimaryKey(roleId);
		return roles;
	}

	/**
	 * 通过学号查询用户信息
	 * 2017年12月21日
	 * zclong
	 * @param email
	 * @return
	 * @throws Exception
	 */
	@Override
	public User findUserBySid(Integer studentNumber) {
		Student student = studentMapper.selectBySid(studentNumber);
		if(student == null) {
			return null;
		}
		User user = new User();
		user.setAcademy(student.getAcademy());
		user.setMajor(ValidUtils.splitNotNumber(student.getClasses()));
		user.setClasss(ValidUtils.getNumbers(student.getClasses()));
		user.setUserName(student.getStudentName());
		if(student.getType() == 1) {
			user.setRoleId((long) 3);
		}else {
			user.setRoleId((long) 1);
		}
		Map<String, String> map = EncryptUtil.md5Decode(student.getPassword());
        String password = map.get("password");
        String salt = map.get("salt");
        user.setPassword(password);
        user.setSalt(salt);
        
		return user;
	}

	/**
	  * 根据学号通过学生信息表查询学生信息
	  * @param sid
	  * @return
	  */
	@Override
	public Student selectStuBySid(Integer sid) {
		Student student = studentMapper.selectBySid(sid);
		return student;
	}
	
	@Override
	public User selectUserBySid(Integer studentNumber) {
		User user = usersMapper.selectBySid(studentNumber);
		return user;
	}
}
