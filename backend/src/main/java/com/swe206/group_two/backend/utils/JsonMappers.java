package com.swe206.group_two.backend.utils;

import java.util.Map;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonMappers<V> {
    private Map<String, Object> json;

    public JsonMappers(V object) {
        JsonMapper jsonMapper = new JsonMapper();
        jsonMapper.registerModule(new JavaTimeModule());
        jsonMapper.configure(
                SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.json = jsonMapper.convertValue(object, Map.class);
    }

    public void put(String key, V value) {
        json.put(key, value);
    }

    public Map<String, Object> getJson() {
        return json;
    }
}
