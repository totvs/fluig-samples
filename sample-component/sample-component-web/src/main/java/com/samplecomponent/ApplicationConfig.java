package com.samplecomponent;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/my-component")
public class ApplicationConfig extends Application {
    public ApplicationConfig() {

    }
}
