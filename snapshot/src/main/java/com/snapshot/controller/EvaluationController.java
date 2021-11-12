package com.snapshot.controller;


import com.snapshot.dto.request.EvaluationQuery;
import com.snapshot.dto.response.EvaluationRowVo;
import com.snapshot.pojo.AjaxResult;
import com.snapshot.pojo.Evaluation;
import com.snapshot.pojo.PageList;
import com.snapshot.service.EvaluationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 评论
 *
 * @author Chan
 */
@Slf4j
@RestController
@RequestMapping("/evaluation")
public class EvaluationController {

    private final EvaluationService evaluationService;

    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    /**
     * 新增留言
     * @param evaluation 留言
     * @return 留言id
     */
    @PostMapping()
    public AjaxResult saveEvaluation(@RequestBody Evaluation evaluation) {
        AjaxResult ajaxResult = AjaxResult.success("评论成功，审核通过后可见",evaluationService.saveEvaluation(evaluation));
       return ajaxResult;
    }


    /**
     * 分页查询 留言
     * @return 结果
     */
    @PostMapping("/list")
    public AjaxResult queryEvaluationList(@RequestBody EvaluationQuery query) {
        AjaxResult ajaxResult = AjaxResult.success(evaluationService.queryEvaluationList(query));
        return ajaxResult;
    }

    /**
     * 根据商品id 查询 留言数
     * @param prodId 商品id
     * @return 留言数
     */
    @GetMapping("/count/{prodId}")
    public AjaxResult countEvaluationByProdId(@PathVariable Long prodId) {
        AjaxResult ajaxResult = AjaxResult.success(evaluationService.countEvaluationByProdId(prodId));
        return ajaxResult;
    }

}
