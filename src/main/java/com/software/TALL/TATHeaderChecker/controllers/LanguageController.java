package com.software.TALL.TATHeaderChecker.controllers;

import com.software.TALL.TATHeaderChecker.model.Language;
import com.software.TALL.TATHeaderChecker.service.LanguageService;
import com.software.TALL.TATHeaderChecker.utils.DriveUtils;
import com.software.TALL.TATHeaderChecker.utils.SaUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
@Path("/language")
public class LanguageController {

    @Autowired
    LanguageService languageService;


    @PermitAll
    @GET
    @Path("/")
    public Response getCourses(@Context HttpServletRequest req) {
        ArrayList<Language> languages = languageService.getLanguages();

        return DriveUtils.run(SaUtils.SaKey, languages);
    }


}

