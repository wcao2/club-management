package com.ascending.training.club.service;

import com.ascending.training.club.model.Image;
import com.ascending.training.club.respository.ImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {
    @Autowired
    ImageDao imageDao;

    public Image save(Image image){return imageDao.save(image);}
    public int delByUserId(Long id){return imageDao.delByUserId(id);}
    public List<Image> getByUserId(Long id){return imageDao.getByUserId(id);}
}
