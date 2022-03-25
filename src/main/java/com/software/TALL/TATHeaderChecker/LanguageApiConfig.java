package com.software.TALL.TATHeaderChecker;

import com.software.TALL.TATHeaderChecker.controllers.LanguageController;
import com.software.TALL.TATHeaderChecker.controllers.RootController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;
import javax.annotation.PostConstruct;

@Component
@ApplicationPath("api/v2")
public class LanguageApiConfig extends ResourceConfig {
    @PostConstruct
    private void init() {
        System.out.println("running languageApiConfig");
        registerClasses(LanguageController.class);
        registerClasses(RootController.class);
        //register(AuthenticationFilter.class);


    }
}
