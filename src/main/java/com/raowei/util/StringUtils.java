package com.raowei.util;

import com.raowei.log.Log;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HttpServletRequest相关操作
 * @author terryrao
 * @since 1.0 2015-05-15
 */
public class StringUtils {
    private static Log logger = Log.getLogger(StringUtils.class);

    public static boolean isBlank(String string) {
        if (string == null || string.equals("")) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }


    /**
     * 来自 net.sf.ehcache.config.ConfigurationFactoryo类
     * Extracts properties of the form ${...}
     *
     * @param sourceDocument the source document
     * @return a Set of properties. So, duplicates are only counted once.
     */
    public static Set extractPropertyTokens(String sourceDocument) {
        Set propertyTokens = new HashSet();
        Pattern pattern = Pattern.compile("\\$\\{.+?\\}");
        Matcher matcher = pattern.matcher(sourceDocument);
        while (matcher.find()) {
            String token = matcher.group();
            propertyTokens.add(token);
        }
        return propertyTokens;
    }

    /**
     * 来自 net.sf.ehcache.config.ConfigurationFactoryo类
     * Translates system properties which can be added as tokens to the config file using ${token} syntax.
     * <p>
     * So, if the config file contains a character sequence "multicastGroupAddress=${multicastAddress}", and there is
     * a system property
     * multicastAddress=230.0.0.12 then the translated sequence becomes "multicastGroupAddress=230.0.0.12"
     *
     * @param inputStream
     * @return a translated stream
     */
    private static InputStream translateSystemProperties(InputStream inputStream) throws IOException {

        StringBuilder sb = new StringBuilder();
        int c;
        Reader reader = new InputStreamReader(inputStream, "UTF-8");
        while ((c = reader.read()) != -1) {
            sb.append((char) c);
        }
        String configuration = sb.toString();

        Set tokens = extractPropertyTokens(configuration);
        for (Object tokenObject : tokens) {
            String token = (String) tokenObject;
            String leftTrimmed = token.replaceAll("\\$\\{", "");
            String trimmedToken = leftTrimmed.replaceAll("\\}", "");

            String property = System.getProperty(trimmedToken);
            if (property == null) {
                logger.debug("Did not find a system property for the " + token +
                        " token specified in the configuration.Replacing with \"\"");
            } else {
                //replaceAll by default clobbers \ and $
                String propertyWithQuotesProtected = Matcher.quoteReplacement(property);
                configuration = configuration.replaceAll("\\$\\{" + trimmedToken + "\\}", propertyWithQuotesProtected);

                logger.debug("Found system property value of " + property + " for the " + token +
                        " token specified in the configuration.");
            }
        }
        return new ByteArrayInputStream(configuration.getBytes("UTF-8"));
    }


    /**
     * 从类似于 key&value的字符串中提出map键值对出来，重复的key值后面将覆盖前面
     *
     * @param paramsString 要做提取操作的字符串
     * @return 包含键值对的Map
     */
    public static Map<String, String> extractPropertiesFromParams(String paramsString) {
        String[] params;
        if (paramsString.contains("&")) {
            params = StringUtils.splitByWholeSeparator(paramsString, "&");
        } else {
            params = new String[]{paramsString};
        }

        Map<String, String> map = new HashMap<>(); // 防止key重复
        for (String s : params) {
            if (StringUtils.contains(s, "=")) {
                String[] temp = StringUtils.splitByWholeSeparator(s, "=", 2);
                if (temp != null && temp.length == 2) {
                    if (StringUtils.isBlank(temp[1])) {
                        continue;
                    }
                    map.put(temp[0], temp[1]);

                }
            }
        }
        return map;
    }


    /**
     * 包装 {@link org.apache.commons.lang3.StringUtils#splitByWholeSeparatorPreserveAllTokens(String, String)}
     *
     * @param string    要分割的字符串
     * @param separator 分割符
     * @return 返回分割后的字符串数组
     */
    public static String[] splitByWholeSeparator(String string, String separator) {
        return org.apache.commons.lang3.StringUtils.splitByWholeSeparator(string, separator);
    }

    /**
     * 包装{@link org.apache.commons.lang3.StringUtils#contains(CharSequence, CharSequence)}
     *
     * @param str        要检查的字符串
     * @param searchChar 要寻找的字符串
     * @return 如果找到则返回true 反之返回false
     */
    public static boolean contains(String str, String searchChar) {
        return org.apache.commons.lang3.StringUtils.contains(str, searchChar);
    }

    /**
     * 包装{@link org.apache.commons.lang3.StringUtils#splitByWholeSeparator(String, String, int)}
     *
     * @param str       要分割的字符串
     * @param separator 分割符
     * @param max       返回数组中成员的最大数量
     * @return 返回分割后的字符串数组
     */
    public static String[] splitByWholeSeparator(String str, String separator, int max) {
        return org.apache.commons.lang3.StringUtils.splitByWholeSeparator(str, separator, max);
    }
}
