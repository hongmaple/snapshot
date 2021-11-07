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
     *
     */
    private String avatarImage;
}
