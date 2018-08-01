package cn.edu.jxau.common.entity;

import java.util.Date;

/**
 * Role -> role
 * 2018-02-11
 */
@lombok.Getter
@lombok.Setter
@lombok.EqualsAndHashCode(callSuper = false)
public class Role extends BaseEntity {
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 最后修改时间
     */
    private Date gmtModified;

    private static final long serialVersionUID = 1L;
}