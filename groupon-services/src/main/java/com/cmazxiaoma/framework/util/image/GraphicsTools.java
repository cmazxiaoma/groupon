package com.cmazxiaoma.framework.util.image;


import com.cmazxiaoma.common.constant.GlobalConstant;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.core.IdentifyCmd;
import org.im4java.core.InfoException;
import org.im4java.process.ArrayListOutputConsumer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class GraphicsTools {
	
	private static String graphicsMagicKPath = "/usr/local/Cellar/graphicsmagick/1.3.23/bin/";

	/**
	 * 根据尺寸裁剪(缩放)图片
	 * @param width	目标宽度
	 * @param height 目标高度
	 * @param srcPath 源图片路径
	 * @param newPath 目标路径
	 * @param quality 压缩比,0~100之间,数值越大越清晰
     * @return 生成图片路径
     */
	protected static String cutImage(double width, double height, String srcPath, String newPath, String quality) throws Exception {
		// 获取原图信息
		//TODO: from DB maybe better
		HashMap<String, String> imgInfo = getBaseInfo(srcPath);
		double srcWidth = Double.valueOf(imgInfo.get("Width"));
		double srcHeight = Double.valueOf(imgInfo.get("Height"));

		if (width >= srcWidth && height >= srcHeight) {
			width = srcWidth;
			height = srcHeight;
		} else {
			// 查看压缩比
			double temp1 = width / srcWidth;
			double temp2 = height / srcHeight;
			// 宽压缩比小，以宽为准
			if (temp1 < temp2) {
				height = srcHeight * temp1;
			}
			// 高压缩比小，以高为准
			if (temp1 > temp2) {
				width = srcWidth * temp2;
			}
		}
		IMOperation op = new IMOperation();
		// true表示使用GraphicsMagick，false表示是使用ImageMagick
		ConvertCmd cmd = new ConvertCmd(true);
		op.addImage();
		String raw = "";

		// 按像素
		raw = width + "x" + height + "^";
		op.addRawArgs("-sample", raw);
		op.addRawArgs("-gravity", "center");
		op.addRawArgs("-extent", raw);

		if ((quality != null && !quality.equals(""))) {
			op.addRawArgs("-quality", quality);
		}
		op.addImage();

		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.indexOf("win") != -1) {
			cmd.setSearchPath(graphicsMagicKPath);
		}
		try {
			File filePath = new File(newPath.substring(0, newPath.lastIndexOf(GlobalConstant.PATHSEPERATOR)));
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			cmd.run(op, srcPath, newPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newPath;
	}

	/**
	 * 获取图片基本信息
	 * @param imgPath 图片路径
	 * @return 基本信息
	 * @throws InfoException
	 */
	protected static HashMap<String, String> getBaseInfo(String imgPath) throws InfoException {
		HashMap<String, String> iAttributes;
		try {
			IMOperation op = new IMOperation();
			op.ping();
			op.format("%m\n%w\n%h\n%g\n%W\n%H\n%G\n%z\n%r");
			op.addImage(imgPath);

			// true表示使用GraphicsMagick，false表示是使用ImageMagick
			IdentifyCmd identify = new IdentifyCmd(true);
			ArrayListOutputConsumer output = new ArrayListOutputConsumer();
			identify.setOutputConsumer(output);
			identify.setSearchPath(graphicsMagicKPath);
			identify.run(op);

			ArrayList<String> cmdOutput = output.getOutput();
			Iterator<String> iter = cmdOutput.iterator();

			iAttributes = new HashMap<>();
			iAttributes.put("Format", iter.next());
			iAttributes.put("Width", iter.next());
			iAttributes.put("Height", iter.next());
			iAttributes.put("Geometry", iter.next());
			iAttributes.put("PageWidth", iter.next());
			iAttributes.put("PageHeight", iter.next());
			iAttributes.put("PageGeometry", iter.next());
			iAttributes.put("Depth", iter.next());
			iAttributes.put("Class", iter.next());
		} catch (Exception ex) {
			throw new InfoException(ex);
		}
		return iAttributes;
	}
}