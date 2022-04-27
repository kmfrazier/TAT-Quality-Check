package com.software.TALL.TATChecker;

import com.software.TALL.TATChecker.controllers.LanguageController;
import com.software.TALL.TATChecker.controllers.RootController;
import com.software.TALL.TATChecker.utils.SaUtils;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;
import javax.annotation.PostConstruct;

@Component
@ApplicationPath("/api/v2")
public class LanguageApiConfig extends ResourceConfig {
    @PostConstruct
    private void init() {
        //SaUtils.runSaUtils(); This retrieves the public key associated with the private key stored in /resources.
        registerClasses(LanguageController.class);
        registerClasses(RootController.class);


    }
}
