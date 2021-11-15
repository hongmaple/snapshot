package com.snapshot.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.snapshot.enums.EvaluationState;
import lombok.Data;

import java.util.Date;

/**
 * @author Chan
 */
@Data
@TableName("evaluation")
public class Evaluation {
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 内容
     */
    private String content;

    /**
     * 作品id
     */
    private Long workId;

    /**
     * 状态
     */
    private EvaluationState status;

    /**
     *评论者
     */
    private Long creatorId;
    /**
     * 用户类型，0：C端，1：后台
     */
    private Integer creatorType;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 逻辑删除
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Boolean isDeleted;

}
