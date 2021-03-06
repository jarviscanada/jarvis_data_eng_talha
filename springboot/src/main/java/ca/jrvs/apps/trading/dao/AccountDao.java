package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class AccountDao extends JdbcCrudDao<Account> {

    private static final String TABLE_NAME = "account";
    private static final String ID_COLUMN = "id";

    private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleInsert;

    @Autowired
    public AccountDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(ID_COLUMN);
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() {
        return simpleInsert;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getIdColumnName() {
        return ID_COLUMN;
    }

    @Override
    Class<Account> getEntityClass() {
        return Account.class;
    }

    @Override
    public int updateOne(Account account) {
        String update_sql = "UPDATE ACCOUNT SET amount=?, trader_id=? WHERE id=?";
        return jdbcTemplate.update(update_sql,
                new Object[]{account.getAmount(), account.getTraderId(), account.getId()});
    }

    @Override
    public <S extends Account> Iterable<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public void delete(Account account) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends Account> account) {
        throw new UnsupportedOperationException("Not Implemented");
    }
}
