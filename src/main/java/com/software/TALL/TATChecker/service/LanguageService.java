package com.software.TALL.TATChecker.service;

import com.software.TALL.TATChecker.dao.LanguageDao;
import com.software.TALL.TATChecker.model.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LanguageService {

    @Autowired
    private LanguageDao languageDao;

    public ArrayList<Language> getLanguages() {
        return languageDao.getLanguages();
    }


}