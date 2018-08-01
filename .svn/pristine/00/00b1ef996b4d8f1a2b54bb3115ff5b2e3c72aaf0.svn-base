package cn.edu.jxau.dao.mapper.base;

import org.apache.ibatis.exceptions.PersistenceException;

/**
 * BaseInsertMapper
 *
 * @author LongShu 2017/03/10
 */
public interface BaseInsertMapper<T> extends BaseMapper {

    /**
     * 保存,null的属性也会保存
     */
    int insert(T entity) throws PersistenceException;

    /**
     * 保存,null的属性不会保存
     */
    int insertSelective(T entity) throws PersistenceException;

}
