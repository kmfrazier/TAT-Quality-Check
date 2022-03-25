package com.software.TALL.TATHeaderChecker.dao;

import com.software.TALL.TATHeaderChecker.dao.sql.LanguageSQL;
import com.software.TALL.TATHeaderChecker.model.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
//import software.tall.api.course.dao.sql.GrammarSQL;
//import software.tall.api.course.dao.sql.LanguageSQL;
//import software.tall.api.course.model.db.grammar.DGrammar;
//import software.tall.api.course.model.language.Language;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LanguageDao extends JdbcDaoSupport {

    @Qualifier("dataSource")
    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }


    public ArrayList<Language> getLanguages() {
        List<Language> rows = getJdbcTemplate().query(
                LanguageSQL.SELECT_LANGUAGES,
                new Object[]{},
                BeanPropertyRowMapper.newInstance(Language.class));
        return new ArrayList<>(rows);
    }

    public ArrayList<Language> getLanguagesForApp(Long appId) {
        List<Language> rows = getJdbcTemplate().query(
                LanguageSQL.SELECT_LANGUAGES_BY_APP_ID,
                new Object[]{appId},
                BeanPropertyRowMapper.newInstance(Language.class));
        return new ArrayList<>(rows);
    }

}
