package com.snapshot.service;

import com.snapshot.dto.request.WorkQuery;
import com.snapshot.pojo.PageList;
import com.snapshot.pojo.Work;

/**
 * @author Chan
 */
public interface WorkService {
    /**
     * 添加视频教学
     * @param work 视频
     * @return 视频id
     */
    Long addVideoTeaching(Work work);

    /**
     * 修改视频
     * @param work 视频
     * @return 结果
     */
    Boolean updateVideoTeaching(Work work);

    /**
     * 加载视频
     * @param query 参数
     * @return 视频
     */
    PageList<Work> videoTeachingList(WorkQuery query);

    /**
     *
     * @param id 视频id
     * @return 结果
     */
    Boolean deletedVideoTeaching(Long id);

    /**
     * 根据ID查询视频详情
     * @param id id
     * @return 详情
     */
    Work getVideoTeachingById(Long id);
}