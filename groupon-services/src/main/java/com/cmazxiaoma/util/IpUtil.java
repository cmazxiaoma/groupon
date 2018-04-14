package com.cmazxiaoma.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.cmazxiaoma.framework.base.context.SpringApplicationContext;
import com.cmazxiaoma.support.area.entity.Area;
import com.cmazxiaoma.support.area.service.AreaService;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * IpUtil
 */
public class IpUtil {

    private static CloseableHttpClient client = HttpClients.createDefault();

    private static String baseUrl = "http://ip.taobao.com/service/getIpInfo.php?ip=";

    private static AreaService areaService = SpringApplicationContext.getBean(AreaService.class);

    private IpUtil() {
    }

    private static String getRemoteIp(HttpServletRequest request) {
        //x-forwarded-for:代表客户端，也就是HTTP的请求端真实的IP，只有在通过了HTTP代理或者负载均衡服务器时才会添加该项
        String ip = request.getHeader("x-forwarded-for");
        //Proxy-Client-IP和WL-Proxy-Client-IP: 只有在Apache（Weblogic Plug-In Enable）+WebLogic搭配下出现，其中“WL”就是WebLogic的缩写
        //访问路径是:Client -> Apache WebServer + Weblogic http plugin -> Weblogic Instances
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
//        ip = "61.51.253.90";
//        ip = "218.25.19.153";
        //0:0:0:0:0:0:0:1: IPV6的形式,win7下可能会出现
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

    public static Area getArea(HttpServletRequest request) {
        Area sessionArea = (Area) request.getSession().getAttribute("area");

        if (sessionArea != null) {
            return sessionArea;
        }

        String ip = getRemoteIp(request);
        if (Objects.equals("127.0.0.1", ip)) {
            Area area = new Area();
            area.setId(367l);
            area.setName("北京市");
            return area;
        }
        String ipResp = getIpInfo(ip);
        Response response = JSON.parseObject(ipResp, Response.class);

        if (response.getCode() != 0) {
            //FIXME 记录该用户/浏览器上一次访问时所在的城市,若没有则默认北京
            Area area = new Area();
            area.setId(367l);
            area.setName("北京市");
            return area;
        }

        IpInfo info = response.getData();
        //本网站的业务只在中国开放
        if (!Objects.equals("中国", info.getCountry())) {
            //FIXME 记录该用户/浏览器上一次访问时所在的城市,若没有则默认北京
            Area area = new Area();
            area.setId(367l);
            area.setName("北京市");
            return area;
        }

        Area area = areaService.getByName(info.getCity());

        if (null == area) {
            //FIXME 记录该用户/浏览器上一次访问时所在的城市,若没有则默认北京
            area = new Area();
            area.setId(367l);
            area.setName("北京市");
            return area;
        }
        return area;
    }

    private static String getIpInfo(String ip) {
        HttpGet httpGet = new HttpGet(baseUrl + ip);
        try {
            HttpResponse response = client.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
                String tmp;
                String content = "";
                while ((tmp = reader.readLine()) != null) {
                    content += tmp + "\r\n";
                }
                return content;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            try {
//                httpClient.close();
//            } catch (IOException e) {
//                //防御性容错
//            }
        }
        return null;
    }

    public static class Response {

        @Getter
        @Setter
        private int code;//状态码，正常为0，异常的时候为非0
        @Getter
        @Setter
        private IpInfo data;

    }

    public static class IpInfo {
        @Getter
        @Setter
        private String country; //国家
        @Getter
        @Setter
        @JSONField(name = "country_id")
        private String countryId; //"CN"
        @Getter
        @Setter
        private String area; //地区名称（华南、华北...）
        @Getter
        @Setter
        @JSONField(name = "area_id")
        private String areaId; //地区编号
        @Getter
        @Setter
        private String region; //省名称
        @Getter
        @Setter
        @JSONField(name = "region_id")
        private String regionId; //省编号
        @Getter
        @Setter
        private String city; //市名称
        @Getter
        @Setter
        @JSONField(name = "city_id")
        private String cityId; //市编号
        @Getter
        @Setter
        private String county; //县名称
        @Getter
        @Setter
        @JSONField(name = "county_id")
        private String countyId; //县编号
        @Getter
        @Setter
        private String isp; //ISP服务商名称（电信/联通/铁通/移动...）
        @Getter
        @Setter
        @JSONField(name = "isp_id")
        private String ispId; //ISP服务商编号
        @Getter
        @Setter
        private String ip; //查询的IP地址
    }

}
//{
//    "code":0,
//    "data":{
//        "country":"\u4e2d\u56fd",
//        "country_id":"CN",
//        "area":"\u534e\u5357",
//        "area_id":"800000",
//        "region":"\u5e7f\u4e1c\u7701",
//        "region_id":"440000",
//        "city":"\u6df1\u5733\u5e02",
//        "city_id":"440300",
//        "county":"",
//        "county_id":"-1",
//        "isp":"\u7535\u4fe1",
//        "isp_id":"100017",
//        "ip":"121.35.211.41"
//    }
//}
