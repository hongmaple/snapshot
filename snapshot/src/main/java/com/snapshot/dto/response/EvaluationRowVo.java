package com.snapshot.dto.response;

import com.snapshot.pojo.Evaluation;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Chan
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EvaluationRowVo extends Evaluation {
    private String commentator;
    private String commentatorAvatar;
}
