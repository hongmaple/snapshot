package com.snapshot.service;

import com.snapshot.dto.request.EvaluationQuery;
import com.snapshot.dto.response.EvaluationRowVo;
import com.snapshot.enums.EvaluationState;
import com.snapshot.enums.WorkState;
import com.snapshot.pojo.Evaluation;
import com.snapshot.pojo.PageList;

public interface EvaluationService {

    /**
     * 新增留言
     * @param evaluation 留言
     * @return 留言id
     */
    Long saveEvaluation(Evaluation evaluation);

    /**
     * 分页查询 留言
     * @param query 分页数据
     * @return 结果
     */
    PageList<EvaluationRowVo> queryEvaluationList(EvaluationQuery query);

    /**
     * 分页查询 留言
     * @param query 分页数据
     * @return 结果
     */
    PageList<EvaluationRowVo> queryEvaluationListAdmin(EvaluationQuery query);

    /**
     * 根据商品id 查询 留言数
     * @param prodId 商品id
     * @return 留言数
     */
    Integer countEvaluationByProdId(Long prodId);

    /**
     *
     * @param id
     * @param state
     * @return
     */
    Boolean updateStatus(Long id, EvaluationState state);

}
