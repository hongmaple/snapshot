package com.snapshot.service;

import com.snapshot.dto.request.WorkQuery;
import com.snapshot.dto.response.WorkHomeVo;
import com.snapshot.enums.WorkState;
import com.snapshot.pojo.PageList;
import com.snapshot.pojo.Work;

import java.util.List;

/**
 * @author Chan
 */
public interface WorkService {
    /**
     * 添加作品教学
     * @param work 作品
     * @return 作品id
     */
    Long addWork(Work work);

    /**
     * 修改作品
     * @param work 作品
     * @return 结果
     */
    Boolean updateVideoTeaching(Work work);

    /**
     * 加载作品
     * @param query 参数
     * @return 作品
     */
    PageList<Work> videoTeachingList(WorkQuery query);

    /**
     *
     * @param id 作品id
     * @return 结果
     */
    Boolean deletedVideoTeaching(Long id);

    /**
     * 根据ID查询作品详情
     * @param id id
     * @return 详情
     */
    WorkHomeVo getVideoTeachingById(Long id);

    /**
     *
     * @param id
     * @param state
     * @return
     */
    Boolean updateStatus(Long id, WorkState state);

    /**
     * 按类型加载作品
     * @param query
     * @return
     */
    PageList<WorkHomeVo> queryWorkListByWorkType(WorkQuery query);
}
