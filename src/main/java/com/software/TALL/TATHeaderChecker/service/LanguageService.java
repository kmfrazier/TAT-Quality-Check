package com.software.TALL.TATHeaderChecker.service;

import com.software.TALL.TATHeaderChecker.dao.LanguageDao;
import com.software.TALL.TATHeaderChecker.model.Language;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import software.tall.api.course.dao.LanguageDao;
//import software.tall.api.course.model.language.Language;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Service
public class LanguageService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LanguageDao languageDao;

    public ArrayList<Language> getLanguages() {
        return languageDao.getLanguages();
    }


}