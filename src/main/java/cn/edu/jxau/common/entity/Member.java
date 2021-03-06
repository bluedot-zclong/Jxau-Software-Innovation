package cn.edu.jxau.common.entity;

import java.util.Date;

/**
 * Member -> member
 * 2018-02-11
 */
@lombok.Getter
@lombok.Setter
@lombok.EqualsAndHashCode(callSuper = false)
public class Member extends BaseEntity {
    /**
     * 团队的id
     */
    private Long memberId;

    /**
     * 队员姓名
     */
    private String memberName;

    /**
     * 院系
     */
    private String memberAcademy;

    /**
     * 专业
     */
    private String memberMajor;

    /**
     * 班级
     */
    private String memberClasss;

    /**
     * 电话，可以为空
     */
    private String memberTel;

    /**
     * 邮箱
     */
    private String memberEmail;

    /**
     * 队长id
     */
    private Long userId;

    /**
     * 0:代表普通学生，1：代表指导老师
     */
    private Integer memberRole;

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