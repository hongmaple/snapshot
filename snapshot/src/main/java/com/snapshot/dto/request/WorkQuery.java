package com.snapshot.dto.request;


import com.snapshot.enums.WorkState;
import com.snapshot.pojo.PageDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询
 * @author Chan
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WorkQuery extends PageDomain {
    private String title;
    private Long creatorId;
    private Integer creatorType;
    private WorkState status;
    private Integer workType;
}
