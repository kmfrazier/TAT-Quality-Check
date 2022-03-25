package com.software.TALL.TATHeaderChecker.service;

import com.software.TALL.TATHeaderChecker.dao.LanguageDao;
import com.software.TALL.TATHeaderChecker.model.Language;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import software.tall.api.course.dao.LanguageDao;
//import software.tall.api.course.model.language.Language;

import java.util.ArrayList;

@Service
public class LanguageService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LanguageDao languageDao;

    public ArrayList<Language> getLanguages() {
        return languageDao.getLanguages();
    }

//    public ArrayList<Language> getLanguagesForApp(Long appId) {
//        ArrayList<Language> languages = languageDao.getLanguagesForApp(appId);
//        for (Language l : languages) {
//            l.setDownloadSize(l.getDownloadSize().replaceAll("MiB", "MB").replaceAll("GiB", "GB"));
//        }
//        return languages;
//    }

}