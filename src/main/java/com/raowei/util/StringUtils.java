package com.raowei.util;

import com.raowei.log.Log;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by terryrao on 5/25/2015.
 */
public class StringUtils {
    private static Log logger = Log.getLogger(StringUtils.class);
    public static boolean isBlank (String string) {
        if (string == null || string.equals("")) {
            return Boolean.TRUE;
        }else {
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

}
