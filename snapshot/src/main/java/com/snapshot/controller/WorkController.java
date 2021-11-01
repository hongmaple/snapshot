package com.snapshot.controller;

import com.snapshot.dto.request.WorkQuery;
import com.snapshot.pojo.AjaxResult;
import com.snapshot.pojo.Work;
import com.snapshot.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Chan
 */
@RestController
@RequestMapping("/video")
public class WorkController {
    @Autowired
    private WorkService workService;

    /**
     * 添加视频
     * @param work 视频
     * @return 视频id
     */
    @PostMapping
    public AjaxResult addVideoTeaching(@RequestBody Work work) {
        Long id = this.workService.addVideoTeaching(work);
        AjaxResult ajaxResult = AjaxResult.success("添加视频教学成功",id);
        return ajaxResult;
    }

    /**
     * 添加视频
     * @param work 视频
     * @return 视频id
     */
    @PostMapping("/create")
    public AjaxResult addCVideoTeaching(@RequestBody Work work) {
        Long id = this.workService.addVideoTeaching(work);
        AjaxResult ajaxResult = AjaxResult.success("添加视频教学成功",id);
        return ajaxResult;
    }

    /**
     * 修改视频
     * @param work 视频
     * @return 结果
     */
    @PutMapping
    public AjaxResult updateVideoTeaching(@RequestBody Work work) {
        Boolean aBoolean = workService.updateVideoTeaching(work);
        AjaxResult ajaxResult;
        if (aBoolean){
            ajaxResult = AjaxResult.success("添加视频教学成功",aBoolean);
        }else {
            ajaxResult = AjaxResult.success("添加视频教学失败", aBoolean);
        }
        return ajaxResult;
    }

    /**
     * 加载视频
     * @param query 视频
     * @return 视频
     */
    @PostMapping("/list")
    public AjaxResult videoTeachingList(@RequestBody WorkQuery query) {
        AjaxResult ajaxResult = AjaxResult.success("添加视频教学成功", workService.videoTeachingList(query));
        return ajaxResult;
    }

    /**
     *
     * @param id 视频id
     * @return 结果
     */
    @DeleteMapping("/{id}")
    public AjaxResult deletedVideoTeaching(@PathVariable Long id) {
        Boolean aBoolean = workService.deletedVideoTeaching(id);
        AjaxResult ajaxResult;
        if (aBoolean){
            ajaxResult = AjaxResult.success("添加视频教学成功",aBoolean);
        }else {
            ajaxResult = AjaxResult.success("添加视频教学失败", aBoolean);
        }
        return ajaxResult;
    }

    /**
     * 根据ID查询视频详情
     * @param id id
     * @return 详情
     */
    @GetMapping("/{id}")
    public AjaxResult getVideoTeachingById(@PathVariable Long id) {
        AjaxResult ajaxResult = AjaxResult.success(workService.getVideoTeachingById(id));
        return ajaxResult;
    }
}
