package com.snapshot.dto.response;

import com.snapshot.pojo.Work;
import lombok.Data;

@Data
public class WorkHomeVo extends Work {
    /**
     * 发布者
     */
    private String username;
    /**
     *发布者头像
     */
    private String avatarImage;

    /**
     *评论数
     */
    private Integer comments;
}
