package com.goomesoft.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonUtil {

    private static ObjectMapper mapper = null;

    static {
        if(mapper == null) {
            mapper = new ObjectMapper();
        }
    }
    
    public static <T> T parseObject(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> T parseObject(InputStream stream, Class<T> clazz) {
        try {
            return mapper.readValue(stream, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String toJsonString(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonNode toJsonNode(String json) {
        JsonNode node = null;
        try {
            node = mapper.readTree(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return node;
    }
    
    public static String createJson(String msgno, int code, Map<String, Object> map) {
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("msgno", msgno);
        rootNode.put("code", code);

        ArrayNode dataNode = rootNode.putArray("data");

        for(Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = String.valueOf(entry.getValue());

            ObjectNode item = mapper.createObjectNode();
            item.put(key, value);
            dataNode.add(item);
        }
        String json = null;
        try {
            json = mapper.writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }
    
}
