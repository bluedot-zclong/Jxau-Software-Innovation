package cn.edu.jxau.dao.mapper.base;

import org.apache.ibatis.exceptions.PersistenceException;

/**
 * BaseDeleteMapper
 *
 * @author LongShu 2017/03/10
 */
public interface BaseDeleteMapper<T> extends BaseMapper {

    /**
     * 用主键删除(可以为多主键)
     *
     * @param key 主键
     */
    int deleteByPrimaryKey(Object key) throws PersistenceException;

    /**
     * 根据条件删除
     *
     * @param entity 条件
     */
    int delete(T entity) throws PersistenceException;

}
