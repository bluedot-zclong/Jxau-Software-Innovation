package cn.edu.jxau.dao.mapper;

import org.apache.ibatis.exceptions.PersistenceException;

import cn.edu.jxau.common.entity.Production;
import cn.edu.jxau.dao.mapper.base.Mapper;

public interface ProductionMapper extends Mapper<Production> {
	/**
     * 根据用户id更新属性不为null的值
     */
    int updateByUserIdSelective(Production entity) throws PersistenceException;
}