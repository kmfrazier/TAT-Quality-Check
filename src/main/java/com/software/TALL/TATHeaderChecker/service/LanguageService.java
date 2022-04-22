package com.software.TALL.TATHeaderChecker.service;

import com.software.TALL.TATHeaderChecker.dao.LanguageDao;
import com.software.TALL.TATHeaderChecker.model.Language;
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