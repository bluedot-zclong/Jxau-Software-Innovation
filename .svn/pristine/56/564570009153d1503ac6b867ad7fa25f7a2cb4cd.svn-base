package cn.edu.jxau.common.entity;

import java.io.Serializable;

import cn.edu.jxau.common.utils.JsonUtil;

/**
 * BaseEntity 所有实体的父类
 */
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * @param pretty 是否格式化漂亮
     * @return json
     */
    public String toJson(boolean pretty) {
        return JsonUtil.toJson(this, pretty);
    }

    public String toJson() {
        return toJson(false);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + toJson();
    }

}
