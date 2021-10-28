package com.withpat.dto.response;


import lombok.Data;

/**
 * @author haiyan
 */
@Data
public class StatisticsTopVo {
    private Integer numberVideo;
    private Integer totalNumberRegistrants;
    private Double menThan;
    private Double womenThan;
    private Integer newRegistrations;
}
