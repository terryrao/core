package com.raowei.util;

import com.raowei.constant.Charset;
import com.raowei.log.Log;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * HttpServletRequest相关操作
 * @author terryrao
 * @version 2015-09-15
 * @since 1.0
 */
public class RequestUtils {
    private static Log logger = Log.getLogger(StringUtils.class);


    /**
     * 设置文件名的编码为utf-8格式
     *
     * @throws UnsupportedEncodingException when charset is not supported
     */
    public static String encodingFileName(HttpServletRequest request, String fileName) throws
            UnsupportedEncodingException {
        return encondingFileName(request, fileName, Charset.UTF_8);
    }

    /**
     * 将要输出文件的文件名进行字符集设置，防止出现乱码，如果filename为空默认返回 unknow
     *
     * @throws UnsupportedEncodingException when charset is not supported
     */
    public static String encondingFileName(HttpServletRequest request, String fileName, String charset) throws
            UnsupportedEncodingException {
        if (StringUtils.isBlank(fileName)) {
            return "unknow";
        }
        String userAgent = request.getHeader("User-Agent");
        // 判断是否是ie及ie11以上
        if (userAgent.toUpperCase().indexOf("MSIE") > 0 || userAgent.toUpperCase().indexOf("TRIDENT") > 0) {
            fileName = URLEncoder.encode(fileName, charset);
        } else {
            fileName = new String(fileName.getBytes(Charset.UTF_8), Charset.ISO_8859_1);
        }
        return fileName;
    }

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("Proxy-Client-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("WL-Proxy-Client-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getRemoteAddr();
        return ip;
    }

}
