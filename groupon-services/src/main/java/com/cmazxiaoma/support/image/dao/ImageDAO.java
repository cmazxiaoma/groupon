package com.cmazxiaoma.support.image.dao;

import com.cmazxiaoma.framework.common.persistence.BaseMybatisDAO;
import com.cmazxiaoma.support.image.entity.ImageInfo;
import org.springframework.stereotype.Repository;


/**
 * 图片
 */
@Repository
public class ImageDAO extends BaseMybatisDAO {

    private static final String IMAGE_MAPPER_NAMESPACE = "com.cmazxiaoma.support.image.entity.ImageInfoMapper";

    /**
     * 保存图片信息
     *
     * @param image 图片信息
     * @return 图片ID
     */
    public Long save(ImageInfo image) {
        super.save(IMAGE_MAPPER_NAMESPACE + ".insertImage", image);
        return image.getId();
    }

    /**
     * 根据ID查询图片信息
     *
     * @param id 图片ID
     * @return 图片信息
     */
    public ImageInfo findById(Long id) {
        return super.findOne(IMAGE_MAPPER_NAMESPACE + ".selectImageById", id);
    }

    /**
     * 更新图片信息
     *
     * @param image
     * @return
     */
    public int update(ImageInfo image) {
        int updateCount = 0;
        updateCount = update(IMAGE_MAPPER_NAMESPACE + ".updateByPrimaryKeySelective", image);
        return updateCount;
    }

}
