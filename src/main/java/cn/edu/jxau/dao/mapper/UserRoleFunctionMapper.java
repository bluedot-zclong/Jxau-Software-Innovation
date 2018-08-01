package cn.edu.jxau.dao.mapper;

import java.util.List;

import cn.edu.jxau.common.entity.Function;

public interface UserRoleFunctionMapper {

    /**
     * 根据userID查询对应的功能权限
     * 2017年12月21日
     * zclong
     * @param userId
     * @return
     */
	List<Function> selectFunctionByUserId(Long userId);
}