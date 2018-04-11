package com.cmazxiaoma.framework.util.image;

import com.cmazxiaoma.common.constant.GlobalConstant;
import com.cmazxiaoma.framework.base.context.SpringApplicationContext;
import com.cmazxiaoma.framework.base.exception.FrameworkException;
import org.im4java.core.InfoException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * 图像工具类
 */
public class ImageUtil {

    private static ImageConfig imageConfig = SpringApplicationContext.getBean(ImageConfig.class);

    private static final long SALT = 1233251;

    /**
     * 依据原始图像 生成多个尺寸图像
     *
     * @param moduleName
     * @param imgId
     * @param srcPath
     * @throws Exception
     */
    public static void generateImage(String moduleName, Long imgId, String srcPath) throws Exception {
        List<Integer> imageIndexList = imageConfig.getImageIndexList(moduleName);
        if (imageIndexList != null && imageIndexList.size() > 0) {
            for (Integer imageIndex : imageIndexList) {
                String newPath = imageConfig.getDestinationBasePath() + GlobalConstant.PATHSEPERATOR +
                        getImageGenerateFileRelativePath(imgId, moduleName, imageIndex);
                doGenerateImage(moduleName, imageIndex, srcPath, newPath, "75");
            }
        }
    }

    /**
     * 依据原始图像 生成多个尺寸图像
     *
     * @param moduleName
     * @param imgId
     * @param srcPath
     * @throws Exception
     */
    public static void generateDetailImage(String moduleName, Long imgId, String srcPath) throws Exception {
        List<Integer> imageIndexList = imageConfig.getImageIndexList(moduleName);
        if (imageIndexList != null && imageIndexList.size() > 0) {
            for (Integer imageIndex : imageIndexList) {
                String newPath = imageConfig.getDetailDestinationBasePath() + getDetailImageGenerateFileRelativePath(imgId, moduleName, imageIndex);
                doGenerateImage(moduleName, imageIndex, srcPath, newPath, "75");
            }
        }
    }

    /**
     * 生成图像生成图像
     *
     * @param moduleName
     * @param imageIndex
     * @param srcPath
     * @param newPath
     * @param quality
     * @return
     * @throws Exception
     */
    private static String doGenerateImage(String moduleName, Integer imageIndex, String srcPath,
                                          String newPath, String quality) throws Exception {
        String imageSize = imageConfig.getImageSize(moduleName, imageIndex);
        String[] imageWidthHeight = imageSize.split("[*]");
        double width = Double.valueOf(imageWidthHeight[0]);
        double height = Double.valueOf(imageWidthHeight[1]);
        GraphicsTools.cutImage(width, height, srcPath, newPath, quality);
        return newPath.substring(newPath.indexOf("images") + 7, newPath.length());

    }

    /**
     * 获取图片url
     *
     * @param domain
     * @param imgId
     * @param moduleName
     * @param imageIndex
     * @return
     */
    public static String getImageUrl(String domain, Long imgId, String moduleName, Integer imageIndex) {
        return domain + getImageGenerateFileRelativePath(imgId, moduleName, imageIndex);
    }

    /**
     * 获取图片生成相对目录
     *
     * @param imgId
     * @param moduleName
     * @param imageIndex
     * @return
     */
    public static String getImageGenerateFileRelativePath(Long imgId, String moduleName, Integer imageIndex) {
        StringBuilder path = new StringBuilder();
        path.append(toHex32(XOR(imgId / 1000000))).append(GlobalConstant.PATHSEPERATOR).append(toHex32(XOR(imgId.intValue() % 1000000 / 100)))
                .append(GlobalConstant.PATHSEPERATOR).append(generateFileName(imageConfig.getImageModuleKey(moduleName), imgId, imageIndex));
        return path.toString().toLowerCase();
    }

    /**
     * 获取图片上传相对目录
     *
     * @return srcPath D0/IMX0000/001.png
     */
    public static String getImageSourceFileRelativePath(Long imgId) {
        StringBuilder path = new StringBuilder();
        path.append(imgId / 1000000).append(GlobalConstant.PATHSEPERATOR).append(imgId % 1000000 / 100)
                .append(GlobalConstant.PATHSEPERATOR).append(imgId % 100).append(".jpg");
        return path.toString().toLowerCase();
    }

    /**
     * 获取图片上传绝对目录
     *
     * @param imgId
     * @return
     */
    public static String getImageSourceFileAbsolutePath(Long imgId) {
        return imageConfig.getSourceBasePath() + getImageSourceFileRelativePath(imgId);
    }

    /**
     * 获取详情图片上传相对目录
     *
     * @param imgFileName
     * @return
     */
    public static String getDetailImageSourceFileRelativePath(String imgFileName) {
        StringBuilder sb = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        sb.append(calendar.get(Calendar.YEAR)).append(GlobalConstant.PATHSEPERATOR)
                .append(getMonthDay(calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))).append("/").append(imgFileName);
        return sb.toString();
    }

    /**
     * 获取详情图片上传绝对目录
     *
     * @param imgFileName
     * @return
     */
    public static String getDetailImageSourceFileAbsolutePath(String imgFileName) {
        return imageConfig.getDetailSourceBasePath() + getDetailImageSourceFileRelativePath(imgFileName);
    }

    /**
     * 获取详情图片生成相对目录
     *
     * @param imgId
     * @param moduleName
     * @param imageIndex
     * @return
     */
    public static String getDetailImageGenerateFileRelativePath(Long imgId, String moduleName, Integer imageIndex) {
        Calendar calendar = Calendar.getInstance();
        String imgFileName = generateFileName(imageConfig.getImageModuleKey(moduleName), imgId, imageIndex);
        StringBuilder sb = new StringBuilder();
        sb.append(calendar.get(Calendar.YEAR)).append(GlobalConstant.PATHSEPERATOR)
                .append(getMonthDay(calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))).append("/").append(imgFileName);
        return sb.toString();
    }

    /**
     * @param moduleName
     * @param imageId
     * @param imageIndex
     * @return
     */
    public static String generateFileName(String moduleName, Long imageId, Integer imageIndex) {
        String imageIndexHex = toHex32(imageIndex.longValue());
        String fileNameHex = toHex32(imageId ^ 4376823).toLowerCase();
        return (moduleName + imageIndexHex + fileNameHex).toLowerCase() + ".jpg";
    }

    private static long XOR(long source) {
        return source ^ SALT;
    }

    /**
     * 数字转成32位
     *
     * @param number
     * @return
     */
    //十进制:32 => 32进制:10
    private static String toHex32(Long number) {
        String result = "";
        if (number <= 0) {
            return "0";
        } else {
            while (number != 0) {
                result = "0123456789ABCDEFGHIJKLMNOPQRSTUV".substring((int) (number % 32), (int) (number % 32 + 1)) + result;
                number = number / 32;
            }
            return result;
        }
    }

    public static HashMap<String, String> getBaseInfo(String imgPath) {
        try {
            return GraphicsTools.getBaseInfo(imgPath);
        } catch (InfoException e) {
            e.printStackTrace();
            throw new FrameworkException(e.getMessage(), e);
        }
    }

    private static String getMonthDay(int month, int day) {
        String monthDay;
        if (month < 9) {
            monthDay = "0" + (month + 1);
        } else {
            monthDay = (month + 1) + "";
        }
        if (day < 10) {
            monthDay = monthDay + "0" + day;
        } else {
            monthDay = monthDay + day;
        }
        return monthDay;
    }
}