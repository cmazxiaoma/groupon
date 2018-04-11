package com.cmazxiaoma.admin.common.image.controller;

import com.cmazxiaoma.admin.base.constant.Constants;
import com.cmazxiaoma.admin.base.controller.BaseAdminController;
import com.cmazxiaoma.framework.base.exception.BusinessException;
import com.cmazxiaoma.framework.util.image.ImageUtil;
import com.cmazxiaoma.support.image.entity.ImageInfo;
import com.cmazxiaoma.support.image.service.ImageService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * 图像上传
 */
@Controller
@RequestMapping(value = "/image")
public class ImageController extends BaseAdminController {

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/ckeuploadfile")
    public void ckeUpload(HttpServletRequest request, String module, HttpServletResponse response) {
        MultipartFile file = ((StandardMultipartHttpServletRequest) request).getFile("upload");
        if (file.isEmpty()) {
            return;
        }

        PrintWriter out = null;
        try {
            // 保存图片信息
            ImageInfo image = new ImageInfo();
            long imgId = imageService.addImage(image);

            // 将上传的图片文件写入image.properties配置文件中detailSourceBasePath指定的文件目录
            String fileName = file.getOriginalFilename();
            String imgFileName = imgId + fileName.substring(fileName.lastIndexOf("."), fileName.length());
            String imgSourceFileAbsolutePath = ImageUtil.getDetailImageSourceFileAbsolutePath(imgFileName);
            File imgFile = new File(imgSourceFileAbsolutePath);
            if (!imgFile.getParentFile().exists()) {
                imgFile.getParentFile().mkdirs();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), imgFile);

            // 更新图片信息
            image.setId(imgId);
            HashMap<String, String> imageInfo = ImageUtil.getBaseInfo(imgSourceFileAbsolutePath);
            image.setHeight(Integer.valueOf(imageInfo.get("Height")));
            image.setWidth(Integer.valueOf(imageInfo.get("Width")));
            image.setSourcePath(ImageUtil.getDetailImageSourceFileRelativePath(imgFileName)); // 上传图片源文件的相对路径
            imageService.updateImage(image);

            // 生成图片
            ImageUtil.generateDetailImage(module, imgId, imgSourceFileAbsolutePath);

            // 将生成的图片回显到CKEditor
            String callback = request.getParameter("CKEditorFuncNum");
            out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            String detailImageUrl = getImgDomain() + "/images/detail/" + ImageUtil.getDetailImageGenerateFileRelativePath(imgId, module, 1);
            out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + detailImageUrl + "',''" + ")");
            out.println("</script>");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private String getImgDomain() {
        return Constants.IMAGE_DOMAIN;
    }
}