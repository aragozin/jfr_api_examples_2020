package com.example.restservice;

import java.lang.ref.WeakReference;
import java.util.Map;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.FlightRecorder;
import jdk.jfr.Label;
import jdk.jfr.Period;
import jdk.jfr.StackTrace;

@Category("MyEvents")
@Label("Cache Stats")
@Description("Simple cache statistics")
@Period("10s")
@StackTrace(false)
public class CacheStatsEvent extends Event {

    @Label("Cache Name")
    public String cacheName;

    @Label("Cache Size")
    public int cacheSize;

    public static void enableStatsRecording(String cacheName, Map<?, ?> cache) {

        final WeakReference<Map<?, ?>> ref = new WeakReference<Map<?,?>>(cache);
        final CacheStatsEvent event = new CacheStatsEvent();
        event.cacheName = cacheName;

        FlightRecorder.addPeriodicEvent(CacheStatsEvent.class, new Runnable() {

            @Override
            public void run() {
                Map<?, ?> cache = ref.get();
                if (cache == null) {

                    FlightRecorder.removePeriodicEvent(this);

                } else {

                    event.begin();
                    event.cacheSize = cache.size();
                    event.commit();
                }
            }
        });
    }
}
