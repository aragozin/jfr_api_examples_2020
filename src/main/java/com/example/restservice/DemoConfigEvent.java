package com.example.restservice;

import java.lang.ref.WeakReference;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.FlightRecorder;
import jdk.jfr.Label;
import jdk.jfr.Period;
import jdk.jfr.StackTrace;

@Category("MyEvents")
@Label("Demo Config")
@Description("Demo Controller Configuration")
@Period()
@StackTrace(false)
public class DemoConfigEvent extends Event {

    @Label("Set is enabled")
    public boolean isSetEnabled;

    @Label("Get is enabled")
    public boolean isGetEnabled;

    public static void enableConfigRecording(DemoController controller) {

        final WeakReference<DemoController> ref = new WeakReference<DemoController>(controller);
        final DemoConfigEvent event = new DemoConfigEvent();

        FlightRecorder.addPeriodicEvent(DemoConfigEvent.class, new Runnable() {

            @Override
            public void run() {
                DemoController controller = ref.get();
                if (controller == null) {

                    FlightRecorder.removePeriodicEvent(this);

                } else {

                    event.begin();
                    event.isGetEnabled = controller.isGetEnabled();
                    event.isSetEnabled = controller.isSetEnabled();
                    event.commit();
                }
            }
        });
    }

}
