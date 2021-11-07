package com.snapshot.controller;

import com.snapshot.dto.request.WorkQuery;
import com.snapshot.enums.WorkState;
import com.snapshot.pojo.AjaxResult;
import com.snapshot.pojo.Work;
import com.snapshot.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Chan
 */
@RestController
@RequestMapping("/work")
public class WorkController {
    @Autowired
    private WorkService workService;

    /**
     * 添加作品
     * @param work 作品
     * @return 作品id
     */
    @PostMapping
    public AjaxResult addWork(@RequestBody Work work) {
        Long id = this.workService.addWork(work);
        AjaxResult ajaxResult = AjaxResult.success("添加作品成功",id);
        return ajaxResult;
    }

    /**
     * 添加作品
     * @param work 作品
     * @return 作品id
     */
    @PostMapping("/create")
    public AjaxResult addCVideoTeaching(@RequestBody Work work) {
        Long id = this.workService.addWork(work);
        AjaxResult ajaxResult = AjaxResult.success("添加作品成功",id);
        return ajaxResult;
    }

    /**
     * 修改作品
     * @param work 作品
     * @return 结果
     */
    @PutMapping
    public AjaxResult updateVideoTeaching(@RequestBody Work work) {
        Boolean aBoolean = workService.updateVideoTeaching(work);
        AjaxResult ajaxResult;
        if (aBoolean){
            ajaxResult = AjaxResult.success("添加作品成功",aBoolean);
        }else {
            ajaxResult = AjaxResult.success("添加作品失败", aBoolean);
        }
        return ajaxResult;
    }

    /**
     * 加载作品
     * @param query 作品
     * @return 作品
     */
    @PostMapping("/list")
    public AjaxResult videoTeachingList(@RequestBody WorkQuery query) {
        AjaxResult ajaxResult = AjaxResult.success("加载作品", workService.videoTeachingList(query));
        return ajaxResult;
    }

    /**
     *
     * @param id 作品id
     * @return 结果
     */
    @DeleteMapping("/{id}")
    public AjaxResult deletedVideoTeaching(@PathVariable Long id) {
        Boolean aBoolean = workService.deletedVideoTeaching(id);
        AjaxResult ajaxResult;
        if (aBoolean){
            ajaxResult = AjaxResult.success("删除作品成功",aBoolean);
        }else {
            ajaxResult = AjaxResult.success("删除作品失败", aBoolean);
        }
        return ajaxResult;
    }

    /**
     * 根据ID查询作品详情
     * @param id id
     * @return 详情
     */
    @GetMapping("/{id}")
    public AjaxResult getVideoTeachingById(@PathVariable Long id) {
        AjaxResult ajaxResult = AjaxResult.success(workService.getVideoTeachingById(id));
        return ajaxResult;
    }
    @PutMapping("/{id}/{workState}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public AjaxResult updateStatus(@PathVariable Long id, @PathVariable WorkState workState) {
        AjaxResult ajaxResult = AjaxResult.success(workService.updateStatus(id,workState));
        return ajaxResult;
    }

    @PostMapping("/queryWorkListByWorkType")
    public AjaxResult queryWorkListByWorkType(@RequestBody WorkQuery query) {
        AjaxResult ajaxResult = AjaxResult.success(workService.queryWorkListByWorkType(query));
        return ajaxResult;
    }
}
