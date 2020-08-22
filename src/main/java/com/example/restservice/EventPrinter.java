package com.example.restservice;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;

public class EventPrinter {

    private static final String REST_CALL_EVENT = RestCallEvent.class.getName();

    public static void main(String[] args) throws IOException {

        String recFile = args[0];

        RecordingFile rec = new RecordingFile(Paths.get(recFile));

        while (rec.hasMoreEvents()) {
            RecordedEvent event = rec.readEvent();
            if (REST_CALL_EVENT.equals(event.getEventType().getName())) {
                Instant startTime = event.getStartTime();
                String path = event.getString("path");
                String key = event.getString("key");
                long size = event.getLong("dataSize");
                long duration = event.getDuration().get(ChronoUnit.NANOS);

                System.out.println(String.format(
                        "%s start=%s path=%s key=%s dataSize=%d duration=%dns",
                        REST_CALL_EVENT, startTime, path, key, size, duration));
            }
        }

        rec.close();
    }
}
