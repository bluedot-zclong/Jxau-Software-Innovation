package cn.edu.jxau.common.entity;

import java.util.Date;

/**
 * Production -> production
 * 2018-02-11
 */
@lombok.Getter
@lombok.Setter
@lombok.EqualsAndHashCode(callSuper = false)
public class Production extends BaseEntity {
    private Long productionId;

    /**
     * 作品类别
     */
    private String productionType;

    /**
     * 作品名
     */
    private String productionName;

    /**
     * 作品介绍
     */
    private String productionContent;

    /**
     * 作品路径
     */
    private String productionPath;

    /**
     * 提交作品人
     */
    private Long userId;

    /**
     * 作品获得投票总数
     */
    private Integer productionPoll;

    /**
     * 作品是否通过，1 表示通过，0 表示未通过。 
     */
    private Integer isPass;

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