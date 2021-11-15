package com.snapshot.dto.response;

import lombok.Data;

/**
 * @author Chan
 */
@Data
public class WxinMineInfoVo {
    /**
     * 曝光台待审核数
     */
    private Integer bgtToAuditNum;

    /**
     * 曝光台审核通过数
     */
    private Integer bgtPassNum;

    /**
     * 曝光台作品总数
     */
    private Integer bgtAllNum;

    /**
     * 文明点赞待审核数
     */
    private Integer cultureToAuditNum;

    /**
     * 文明点赞审核通过数
     */
    private Integer culturePassNum;

    /**
     * 文明点赞作品总数
     */
    private Integer cultureAllNum;

}
