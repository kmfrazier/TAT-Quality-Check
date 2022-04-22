package com.software.TALL.TATHeaderChecker.dao;

import com.software.TALL.TATHeaderChecker.dao.sql.LanguageSQL;
import com.software.TALL.TATHeaderChecker.model.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
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

}
