package com.snapshot.dto.response;


import lombok.Data;

/**
 * @author Chan
 */
@Data
public class StatisticsTopVo {
    private Integer numberVideo;
    private Integer totalNumberRegistrants;
    private Double menThan;
    private Double womenThan;
    private Integer newRegistrations;
}
