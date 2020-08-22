package com.example.restservice;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;

import jdk.jfr.Configuration;
import jdk.jfr.EventType;
import jdk.jfr.FlightRecorder;
import jdk.jfr.Recording;

public class CrashDumpHelper {

    private static Recording rec;

    public static synchronized void activate() throws IOException, ParseException {

        if (rec != null) {

            Configuration conf = Configuration.getConfiguration("default");

            rec = new Recording(conf);

            configureEvents(rec);

            // disable disk writes
            rec.setToDisk(false);
            rec.start();
        }
    }

    private static void configureEvents(Recording rec) {
        for (EventType et: FlightRecorder.getFlightRecorder().getEventTypes()) {
            if (isEnabledForCrachDump(et)) {
                rec.enable(et.getName());
            }
        }
    }

    private static boolean isEnabledForCrachDump(EventType et) {
        // enabled application specific custom events
        return false;
    }

    public void dump(Path filename) throws IOException {
        rec.dump(filename);
    }
}
