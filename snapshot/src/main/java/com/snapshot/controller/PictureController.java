package com.snapshot.controller;

import com.snapshot.pojo.AjaxResult;
import com.snapshot.pojo.Picture;
import com.snapshot.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 轮播图
 *
 * @author Chan
 */
@Slf4j
@RestController("/picture")
public class PictureController {
    private final PictureService pictureService;

    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    /**
     * add
     * @param picture 参数
     * @return 结果
     */
    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping
    public AjaxResult addPicture(@RequestBody Picture picture) {
        AjaxResult ajaxResult = AjaxResult.success("添加成功",pictureService.addPicture(picture));
        return ajaxResult;
    }
}
