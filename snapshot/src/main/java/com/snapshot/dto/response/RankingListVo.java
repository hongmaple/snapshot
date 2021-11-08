package com.snapshot.dto.response;

import lombok.Data;

@Data
public class RankingListVo {
    /**
     * 排名
     */
    private Integer rankingIndex;
    /**
     * 用户名
     */
    private String username;
    /**
     * 头像
     */
    private String avatarImage;

    /**
     * 积分
     */
    private Integer point;

    /**
     * 是否是自己的数据
     */
    private Boolean isMy;
}
