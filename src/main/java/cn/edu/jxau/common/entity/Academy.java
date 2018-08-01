package cn.edu.jxau.common.entity;

import java.util.Date;

/**
 * Academy -> academy
 * 2018-02-11
 */
@lombok.Getter
@lombok.Setter
@lombok.EqualsAndHashCode(callSuper = false)
public class Academy extends BaseEntity {
    private Long academyId;

    /**
     * 院系名称
     */
    private String academyName;

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