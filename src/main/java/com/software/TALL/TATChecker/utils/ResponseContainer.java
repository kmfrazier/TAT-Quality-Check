package com.software.TALL.TATChecker.utils;

import java.util.HashMap;

public class ResponseContainer {
    private HashMap<String, Object> responseBody = new HashMap<>();

    public void put(String key, Object value) {
        responseBody.put(key, value);
    }

    public Object get(String key) {
        return responseBody.get(key);
    }

    public HashMap<String, Object> getResponseBody() {
        return responseBody;
    }
}
