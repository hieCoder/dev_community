package com.shsoftvina.community.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shsoftvina.community.exception.BadRequestAlertException;
import com.shsoftvina.community.exception.ErrorEnum;
import org.apache.commons.lang3.StringUtils;

public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T jsonToObject(String json, Class<T> valueType) {

        if(StringUtils.isBlank(json)){
            return null;
        }

        try {
            return objectMapper.readValue(json, valueType);
        } catch (Exception e) {
            throw new BadRequestAlertException(ErrorEnum.JSON_CONVERT_TO_OBJECT_ERROR);
        }
    }

    public static String objectToJson(Object object) {
        try {
            if(object == null) return null;
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
