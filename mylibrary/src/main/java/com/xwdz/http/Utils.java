package com.xwdz.http;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * @author 黄兴伟 (xwd9989@gamil.com)
 * @since 2018/7/20
 */
public class Utils {

    public static String appendHttpParams(LinkedHashMap<String, String> sLinkedHashMap) {
        Iterator<String> keys = sLinkedHashMap.keySet().iterator();
        Iterator<String> values = sLinkedHashMap.values().iterator();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("?");

        for (int i = 0; i < sLinkedHashMap.size(); i++) {
            String value = null;
            try {
                value = URLEncoder.encode(values.next(), "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }

            stringBuffer.append(keys.next() + "=" + value);
            if (i != sLinkedHashMap.size() - 1) {
                stringBuffer.append("&");
            }
        }

        return stringBuffer.toString();
    }
}
