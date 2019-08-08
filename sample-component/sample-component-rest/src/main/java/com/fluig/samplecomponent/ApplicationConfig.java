package com.fluig.samplecomponent;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api/v1")
public class ApplicationConfig extends Application {
    public ApplicationConfig() {

    }
}
