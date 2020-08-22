package com.example.restservice;

import jdk.jfr.Category;
import jdk.jfr.DataAmount;
import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Label;

@Category("MyEvents")
@Label("REST Event")
@Description("REST Request Processing Event")
public class RestCallEvent extends Event {

    @Label("Request Path")
    public String path;

    @Label("Request Key")
    public String key;

    @Label("Result Size")
    @DataAmount(DataAmount.BYTES)
    public long dataSize;

}
