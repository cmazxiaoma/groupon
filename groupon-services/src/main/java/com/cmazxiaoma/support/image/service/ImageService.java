package com.cmazxiaoma.support.image.service;

import com.cmazxiaoma.support.image.dao.ImageDAO;
import com.cmazxiaoma.support.image.entity.ImageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * ImageService
 */
@Service
public class ImageService {

    @Autowired
    private ImageDAO imageDAO;

    /**
     * 保存图片信息
     *
     * @param image 图片信息
     * @return 图片ID
     */
    public Long addImage(ImageInfo image) {
        return imageDAO.save(image);
    }

    /**
     * 更新图片
     *
     * @param image
     * @return
     */
    public int updateImage(ImageInfo image) {
        return imageDAO.update(image);
    }

    /**
     * 根据ID查询图片信息
     *
     * @param id 图片ID
     * @return 图片信息
     */
    public ImageInfo getImageById(Long id) {
        return imageDAO.findById(id);
    }

}
