package com.leimingtech.extend.module.payment.unionpay.pc.gwj.util;

import java.text.SimpleDateFormat;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * json操作工具类
 */
@Slf4j
public class JsonUtil {

    //private static final Logger logger = Logger.getLogger(JsonUtil.class);

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static {
        mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        mapper.getSerializationConfig().withDateFormat(new SimpleDateFormat(DATE_FORMAT));
    }

    public static <T> T fromJson(String json, Class<T> t) {

        if (json == null) {
            return null;
        }
        try {
            return mapper.readValue(json, t);
        } catch (Exception e) {
            log.info("Cannot parse json string to Object. Json: <"
                    + json + ">, Object class: <" + t.getName() + ">.", e);
        }
        return null;
    }

    public static <T> T fromJsonWithException(String json, Class<T> t) {
        try {
            return mapper.readValue(json, t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromMap(Map<?, ?> map, Class<T> t) {

        if (map == null) {
            return null;
        }
        try {
            return mapper.readValue(toJson(map), t);
        } catch (Exception e) {
            log.info("Cannot parse map to Object. Map: <"
                    + map + ">, Object class: <" + t.getName() + ">.", e);
        }
        return null;
    }

    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn(e.toString());
        }
        return "{}";
    }
}
