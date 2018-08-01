package cn.edu.jxau.dao.mapper.base;

import org.apache.ibatis.exceptions.PersistenceException;

/**
 * BaseUpdateMapper
 *
 * @author LongShu 2017/03/10
 */
public interface BaseUpdateMapper<T> extends BaseMapper {

    /**
     * 根据主键更新实体全部字段，null值会被更新
     */
    int updateByPrimaryKey(T entity) throws PersistenceException;

    /**
     * 根据主键更新属性不为null的值
     */
    int updateByPrimaryKeySelective(T entity) throws PersistenceException;

}
