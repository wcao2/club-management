package com.ascending.training.club.respository;


import com.ascending.training.club.model.Image;

import java.util.List;

public interface ImageDao {
    Image save(Image image);
    int delByUserId(Long id);
    List<Image> getByUserId(Long id);
}
