package com.withpat.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.withpat.enums.WorkState;
import lombok.Data;

import java.util.Date;

/**
 * @author haiyan
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
     * pic
     */
    private String pic;
    /**
     * 视频地址
     */
    private String url;

    /**
     * 类型 1：视频，2：照片
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
     * 创建时间 发布时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Boolean isDeleted;
}
