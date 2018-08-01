package cn.edu.jxau.common.entity;

import java.util.Date;

/**
 * Major -> major
 * 2018-02-11
 */
@lombok.Getter
@lombok.Setter
@lombok.EqualsAndHashCode(callSuper = false)
public class Major extends BaseEntity {
    /**
     * 专业id
     */
    private Long majorId;

    /**
     * 专业名称
     */
    private String majorName;

    /**
     * 对应的学院id
     */
    private Long academyId;

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