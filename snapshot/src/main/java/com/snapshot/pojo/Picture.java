package com.snapshot.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 轮播图
 * @author Chan
 */
@Data
@TableName("picture")
public class Picture {
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 轮播图地址
     */
    private String pictureUrl;

    /**
     * 排序
     */
    private Integer pictureIndex;

    /**
     * 1: 有效，2：无效
     */
    private Integer pictureStatus;

    /**
     * 发布图片的管理员ID
     */
    private Long creatorId;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;
    /**
     * 逻辑删除
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Boolean isDeleted;
}
