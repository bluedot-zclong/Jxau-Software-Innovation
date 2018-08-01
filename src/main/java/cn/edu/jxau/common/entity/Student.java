package cn.edu.jxau.common.entity;

/**
 * Student -> student
 * 2018-02-20
 */
@lombok.Getter
@lombok.Setter
@lombok.EqualsAndHashCode(callSuper = false)
public class Student extends BaseEntity {
    private Long studentId;

    /**
     * 姓名
     */
    private String studentName;

    /**
     * 密码
     */
    private String password;

    /**
     * 院系
     */
    private String academy;

    /**
     * 班级
     */
    private String classes;

    /**
     * 学号
     */
    private Integer studentNumber;

    /**
     * 0:普通学生，1：管理员
     */
    private Integer type;

    private static final long serialVersionUID = 1L;
}