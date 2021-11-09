package com.snapshot.service.impl;

import com.snapshot.dao.PictureDao;
import com.snapshot.pojo.Picture;
import com.snapshot.service.PictureService;
import org.springframework.stereotype.Service;

/**
 * @author Chan
 */
@Service
public class PictureServiceImpl implements PictureService {

    private final PictureDao pictureDao;

    public PictureServiceImpl(PictureDao pictureDao) {
        this.pictureDao = pictureDao;
    }

    @Override
    public Boolean addPicture(Picture picture) {
        return pictureDao.save(picture);
    }
}
