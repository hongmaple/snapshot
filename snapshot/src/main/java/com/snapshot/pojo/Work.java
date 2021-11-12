package com.snapshot.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.snapshot.enums.WorkState;
import lombok.Data;

import java.util.Date;

/**
 * @author Chan
 */
@Data
@TableName("work")
public class Work {
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 视频地址
     */
    private String url;
    /**
     * 作品类型 1：文明点赞，2：曝光台
     */
    private Integer workType;
    /**
     * 类型 1：，照片 2：视频
     */
    private Integer type;

    /**
     * 状态
     */
    private WorkState status;
    /**
     * 创建者
     */
    private Long creatorId;
    /**
     * 创建类型
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
