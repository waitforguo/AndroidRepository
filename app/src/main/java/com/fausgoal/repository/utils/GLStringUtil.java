package com.fausgoal.repository.utils;

import android.text.TextUtils;

import com.fausgoal.repository.common.GLConst;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.Map;

/**
 * Description：String处理工具类
 * <br/><br/>Created by Fu on 2015/5/5.
 * <br/><br/>
 */
public class GLStringUtil {
    private GLStringUtil() {
    }

    /**
     * 检查指定的字符串是否为空。
     * <ul>
     * <li>SysUtils.isEmpty(null) = true</li>
     * <li>SysUtils.isEmpty("") = true</li>
     * <li>SysUtils.isEmpty("   ") = true</li>
     * <li>SysUtils.isEmpty("abc") = false</li>
     * </ul>
     *
     * @param value 待检查的字符串
     * @return true/false
     */
    public static boolean isEmpty(String value) {
        int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(value.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断对象是否为空
     *
     * @param obj Object
     * @return true 为空
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null)
            return true;
        if (obj instanceof CharSequence)
            return ((CharSequence) obj).length() == 0;
        if (obj instanceof Collection)
            return ((Collection<?>) obj).isEmpty();
        if (obj instanceof Map)
            return ((Map<?, ?>) obj).isEmpty();
        if (obj.getClass().isArray())
            return java.lang.reflect.Array.getLength(obj) == 0;
        return false;
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    public static boolean isNotEmpty(Object value) {
        return !isEmpty(value);
    }

    /**
     * 检查对象是否为数字型字符串。
     */
    public static boolean isNumeric(Object obj) {
        if (obj == null) {
            return false;
        }
        String str = obj.toString();
        int sz = str.length();
        if (sz == 0) {
            return false;
        }
        if (!Character.isDigit(str.charAt(0)) && str.charAt(0) != '-') {
            return false;
        }
        for (int i = 1; i < sz; i++) {
            if (!Character.isDigit(str.charAt(i)) && str.charAt(i) != '.') {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查指定的字符串列表是否不为空。
     */
    public static boolean areNotEmpty(String... values) {
        boolean result = true;
        if (values == null || values.length == 0) {
            result = false;
        } else {
            for (String value : values) {
                result &= !isEmpty(value);
            }
        }
        return result;
    }

    /**
     * 把通用字符编码的字符串转化为汉字编码。
     */
    public static String unicodeToChinese(String unicode) {
        StringBuilder out = new StringBuilder();
        if (!isEmpty(unicode)) {
            for (int i = 0; i < unicode.length(); i++) {
                out.append(unicode.charAt(i));
            }
        }
        return out.toString();
    }

    /**
     * 过滤不可见字符
     */
    public static String stripNonValidXMLCharacters(String input) {
        if (input == null || ("".equals(input)))
            return "";
        StringBuilder out = new StringBuilder();
        char current;
        for (int i = 0; i < input.length(); i++) {
            current = input.charAt(i);
            if ((current == 0x9) || (current == 0xA) || (current == 0xD)
                    || ((current >= 0x20) && (current <= 0xD7FF))
                    || ((current >= 0xE000) && (current <= 0xFFFD))
                    || ((current >= 0x10000) && (current <= 0x10FFFF)))
                out.append(current);
        }
        return out.toString();
    }

    public static boolean isNumberOrLetter(String value) {
        value = value.trim();
        if (isEmpty(value))
            return false;
        int len = value.length();
        for (int i = 0; i < len; i++) {
            char c = value.charAt(i);
            if (c < '0' || (c > '9' && c < 'A') || (c > 'Z' && c < 'a') || c > 'z')
                return false;
        }
        return true;
    }

    public static boolean isLetter(String value) {
        value = value.trim();
        if (isEmpty(value))
            return false;
        int len = value.length();
        for (int i = 0; i < len; i++) {
            char c = value.charAt(i);
            if (c < 'A' || (c > 'Z' && c < 'a') || c > 'z')
                return false;
        }
        return true;
    }

    public static String getLocathostUrl(String url) {
        if (isEmpty(url)) {
            return "";
        }
        int index = url.indexOf("file:///android_asset/");
        if (index == -1) {
            return "file:///android_asset/" + url;
        }
        return url;
    }

    public static String getHtmlData(String bodyHTML) {
        return getHtmlData(null, bodyHTML);
    }

    public static String getHtmlData(String cssUrl, String bodyHTML) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<html>");
        buffer.append("<head>");
        buffer.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
        buffer.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\" />");
        buffer.append("<style  type=\"text/css\"> ");
        // set margin & padding 0
        buffer.append("body {margin:0px; padding:0px; line-height: 1.5em;} ");
        buffer.append("img{max-width: 100%; width:auto; height:auto;} ");
        buffer.append("div,p,span,a {max-width: 100%;}");
        // custom font
//        buffer.append(" @font-face {font-family: 'PingFang_Medium';src: url('file:///android_asset/fonts/PingFang_Medium.ttf');} body {font-family: 'PingFang_Medium';}");
        buffer.append("</style>");
        if (!TextUtils.isEmpty(cssUrl)) {
            buffer.append(" <link rel=\"stylesheet\" type=\"text/css\" href=\"")
                    .append(cssUrl)
                    .append("\" /> ");
        }
        buffer.append("</head>");
        buffer.append("<body>");
        buffer.append(bodyHTML);
        buffer.append("</body>");
        buffer.append("</html>");
        return buffer.toString();
    }

    /**
     * 获取颜色是否加#，没有则自动加上
     */
    public static String getColorText(String color) {
        if (TextUtils.isEmpty(color)) {
            return "";
        }
        String tempColor;
        int index = color.indexOf("#");
        if (index == GLConst.NEGATIVE) {
            tempColor = "#" + color;
        } else {
            tempColor = color;
        }
        return tempColor;
    }

    /**
     * url是否为网络地址，还是本地地址
     */
    public static boolean isHttpImgUrl(String url) {
        if (TextUtils.isEmpty(url))
            return false;

        int index = url.indexOf(":");
        if (index != -1) {
            // 有冒号，可能是http开始的
            String strStart = url.substring(0, index);
            if ("HTTP".equals(strStart.toUpperCase())
                    || "HTTPS".equals(strStart.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    public static String getHttpUrl(String url) {
        if (!isHttpImgUrl(url)) {
            return "http://" + url;
        }
        return url;
    }

    /**
     * 判断字符串是否为http的网络地址
     */
    public static boolean hasHttpUrl(String url) {
        if (isEmpty(url)) {
            return false;
        }
        int index = url.indexOf(":");
        if (index == -1) {
            return false;
        } else {
            String temp = url.substring(0, index);
            if (!"http".equals(temp.toLowerCase())
                    && !"https".equals(temp.toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    public static String getURLDecoder(String value) {
        try {
            value = URLDecoder.decode(value, GLConst.CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return value;
    }
}
