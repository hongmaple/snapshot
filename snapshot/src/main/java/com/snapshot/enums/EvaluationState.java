package com.snapshot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EvaluationState implements CodedEnum{
    /**
     *
     */
    UNKNOWN(0, "未知"),
    TO_AUDIT(1, "待审核"),
    PASS(2, "通过"),
    NO_PASS(3, "不通过"),
    LOSE_EFFICACY(4, "失效"),
    ;
    private final Integer code;

    private final String tag;
}
