package com.example.restservice;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController{

    private final Map<String, String> store = new ConcurrentHashMap<>();

    @PostConstruct
    public void initFlightRecorder() {
        DemoConfigEvent.enableConfigRecording(this);
        CacheStatsEvent.enableStatsRecording("store", store);
    }

    @GetMapping("/get")
    public String get(
            @RequestParam(value = "key") String key) {
        RestCallEvent event = new RestCallEvent();
        event.begin();
        event.path = "get";
        event.key = key;
        try {
            String value = store.getOrDefault(key, "Not Found");
            event.dataSize = value.length();
            return value;
        } finally {
            event.end();
            event.commit();
        }
    }

    @GetMapping("/set")
    public String set(
            @RequestParam(value = "key") String key,
            @RequestParam(value = "val", defaultValue="") String val) {
        RestCallEvent event = new RestCallEvent();
        event.begin();
        event.path = "set";
        event.key = key;
        try {
            store.put(key, val);
            event.dataSize = val.length();
            return val;
        } finally {
            event.end();
            event.commit();
        }
    }

    public boolean isGetEnabled() {
        return true;
    }

    public boolean isSetEnabled() {
        return true;
    }
}
