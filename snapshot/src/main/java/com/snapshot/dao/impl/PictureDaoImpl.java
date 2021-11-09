package com.snapshot.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snapshot.dao.PictureDao;
import com.snapshot.mapper.PictureMapper;
import com.snapshot.pojo.Picture;
import org.springframework.stereotype.Repository;

/**
 * @author Chan
 */
@Repository
public class PictureDaoImpl extends ServiceImpl<PictureMapper, Picture> implements PictureDao {
}
