package com.withpat.dto.request;


import com.withpat.pojo.PageDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 教学视频查询
 * @author haiyan
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WorkQuery extends PageDomain {
    private String title;
    private Long creatorId;
    private Integer creatorType;
    private Integer status;
}
