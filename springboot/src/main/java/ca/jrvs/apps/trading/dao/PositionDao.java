package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PositionDao extends JdbcCrudDao<Position> {

    private static final String TABLE_NAME = "position";
    private static final String ID_COLUMN = "account_id";

    private static final Logger logger = LoggerFactory.getLogger(PositionDao.class);

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleInsert;

    @Autowired
    public PositionDao(DataSource dataSource) {
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
    Class<Position> getEntityClass() {
        return Position.class;
    }

    @Override
    public <S extends Position> S save(S entity) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public void deleteById(Integer id) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public int updateOne(Position entity) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public <S extends Position> Iterable<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public void delete(Position entity) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends Position> entities) {
        throw new UnsupportedOperationException("Not Implemented");
    }
}
