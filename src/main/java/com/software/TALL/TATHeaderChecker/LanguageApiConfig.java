package com.software.TALL.TATHeaderChecker;

import com.software.TALL.TATHeaderChecker.controllers.LanguageController;
import com.software.TALL.TATHeaderChecker.controllers.RootController;
import com.software.TALL.TATHeaderChecker.utils.SaUtils;
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
