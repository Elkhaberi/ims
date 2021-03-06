package com.ita.if103java.ims.dao.impl;

import com.ita.if103java.ims.dao.AccountTypeDao;
import com.ita.if103java.ims.entity.AccountType;
import com.ita.if103java.ims.exception.dao.AccountTypeNotFoundException;
import com.ita.if103java.ims.exception.dao.CRUDException;
import com.ita.if103java.ims.mapper.jdbc.AccountTypeRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class AccountTypeDaoImpl implements AccountTypeDao {

    private JdbcTemplate jdbcTemplate;
    private AccountTypeRowMapper accountTypeRowMapper;

    @Autowired
    public AccountTypeDaoImpl(DataSource dataSource, AccountTypeRowMapper accountTypeRowMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.accountTypeRowMapper = accountTypeRowMapper;
    }

    @Override
    public AccountType findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(Queries.SQL_SELECT_ACCOUNT_TYPE_BY_ID, accountTypeRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new AccountTypeNotFoundException("Account type not found. id = " + id, e);
        } catch (DataAccessException e) {
            throw new CRUDException("Searching current type exception, id = " + id, e);
        }
    }

    @Override
    public List<AccountType> selectAllPossibleToUpgrade(Integer accountLvl) {
        try {
            return jdbcTemplate.query(Queries.SQL_FIND_ALL_POSSIBLE_TO_UPGRADE, accountTypeRowMapper, accountLvl);
        } catch (DataAccessException e) {
            throw new CRUDException("Searching all possible types exception, *", e);
        }
    }

    @Override
    public Long minLvlType() {
        try {
            return jdbcTemplate.queryForObject(Queries.SQL_FIND_MIN_LVL_TYPE, Long.class);
        } catch (EmptyResultDataAccessException e) {
            throw new AccountTypeNotFoundException("Account not found while min lvl type searching", e);
        } catch (DataAccessException e) {
            throw new CRUDException("Find min lvl type exception", e);
        }
    }

    class Queries {

        static final String SQL_SELECT_ACCOUNT_TYPE_BY_ID = """
                SELECT *
                FROM account_types
                WHERE id = ?
            """;

        static final String SQL_FIND_MIN_LVL_TYPE = """
                SELECT id
                FROM account_types
                WHERE level =
                (SELECT MIN(level)
                FROM account_types
                WHERE active = true)
            """;

        static final String SQL_FIND_ALL_POSSIBLE_TO_UPGRADE = """
                SELECT *
                FROM account_types
                WHERE level > ?
                AND active = true
            """;
    }
}
