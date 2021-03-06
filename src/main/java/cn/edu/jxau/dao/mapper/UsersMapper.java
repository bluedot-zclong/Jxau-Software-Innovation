package cn.edu.jxau.dao.mapper;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;

import cn.edu.jxau.common.entity.Function;
import cn.edu.jxau.common.entity.User;
import cn.edu.jxau.dao.mapper.base.Mapper;

public interface UsersMapper{

	public List<User> findUserList();
	/**
     * 根据条件查询
     */
    public List<User> select(User user);
    /**
     * 根据邮箱查询
     */
    public User selectByEmail(String email);
    /**
     * 根据学号查询
     * @param sid
     * @return
     */
    public User selectBySid(Integer sid);
    
}
