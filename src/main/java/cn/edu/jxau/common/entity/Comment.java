package cn.edu.jxau.common.entity;

import java.util.Date;

/**
 * Comment -> comment
 * 2018-02-11
 */
@lombok.Getter
@lombok.Setter
@lombok.EqualsAndHashCode(callSuper = false)
public class Comment extends BaseEntity {
    private Long commentId;

    /**
     * 学号(教工号）（唯一）
     */
    private Integer studentNumber;

    /**
     * 评论者
     */
    private String commentName;

    /**
     * 评论人类型，1：表示普通用户评论，2表示专家点评
     */
    private Integer commentNameType;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 投票状态,0代表未投，1代表已投
     */
    private Integer voteType;

    /**
     * 作品编号
     */
    private Long productionId;

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