package com.cmazxiaoma.support.image.entity;

import com.cmazxiaoma.framework.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 图像信息类
 */
public class ImageInfo extends BaseEntity {

    private static final long serialVersionUID = -5037705314686839190L;

    /**
     * 图片宽
     */
    @Getter
    @Setter
    private Integer width;

    /**
     * 图片高
     */
    @Getter
    @Setter
    private Integer height;

    /**
     * 图片路径
     */
    @Getter
    @Setter
    private String sourcePath;

}
