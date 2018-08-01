package cn.edu.jxau.common.entity;

import java.util.Date;

/**
 * Syslog -> syslog
 * 2018-02-11
 */
@lombok.Getter
@lombok.Setter
@lombok.EqualsAndHashCode(callSuper = false)
public class Syslog extends BaseEntity {
    private Long logId;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 用户ip地址
     */
    private String userIp;

    /**
     * 操作具体内容
     */
    private String opetateContent;

    /**
     * 操作级别
     */
    private String logLever;

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