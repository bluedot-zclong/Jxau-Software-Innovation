package cn.edu.jxau.dao.mapper.base;

/**
 * Mapper
 *
 * @param <T> 不能为空，传具体实体
 * @author LongShu 2017/03/10
 */
public interface Mapper<T> extends
        BaseInsertMapper<T>,
        BaseDeleteMapper<T>,
        BaseUpdateMapper<T>,
        BaseSelectMapper<T> {
}
