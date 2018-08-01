package cn.edu.jxau.dao.mapper.base;

import org.apache.ibatis.exceptions.PersistenceException;

import java.util.List;

/**
 * BaseSelectMapper
 *
 * @author LongShu 2017/03/10
 */
public interface BaseSelectMapper<T> extends BaseMapper {

    /**
     * 根据主键查询(可以为多主键,多主键是传实体或者Map)
     */
    T selectByPrimaryKey(Object key) throws PersistenceException;

    /**
     * 查询所有
     */
    List<T> selectAll() throws PersistenceException;

    /**
     * 根据条件查询
     */
    List<T> select(T entity) throws PersistenceException;

}
