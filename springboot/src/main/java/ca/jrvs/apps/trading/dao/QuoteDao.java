package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class QuoteDao implements CrudRepository<Quote, String> {

    private static final String TABLE_NAME = "quote";
    private static final String ID_COLUMN_NAME = "ticker";

    private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public QuoteDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
    }

    @Override
    public Quote save(Quote quote) {
        if (existsById(quote.getTicker())) {
            int updateRowNo = updateOne(quote);

            if (updateRowNo != 1) {
                throw new DataRetrievalFailureException("Unable to update quote");
            }
        } 
        else {
            addOne(quote);
        }
        return quote;
    }

    private void addOne(Quote quote) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
        int row = simpleJdbcInsert.execute(parameterSource);

        if (row != 1) {
            throw new IncorrectResultSizeDataAccessException("Failed to insert", 1, row);
        }
    }

    private int updateOne(Quote quote) {
        String updateSQL = "UPDATE quote SET last_price=?, bid_price=?, "
                + "bid_size=?, ask_price=?, ask_size=? WHERE ticker=?";

        return jdbcTemplate.update(updateSQL, makeUpdateValues(quote));
    }

    private Object[] makeUpdateValues(Quote quote) {
        List<Object> updateValues = new ArrayList<>();

        updateValues.add(quote.getLastPrice());
        updateValues.add(quote.getBidPrice());
        updateValues.add(quote.getBidSize());
        updateValues.add(quote.getAskPrice());
        updateValues.add(quote.getAskSize());
        updateValues.add(quote.getId());

        //Need to return an array object so lets convert our list to array
        return updateValues.toArray();
    }

    @Override
    public <S extends Quote> List<S> saveAll(Iterable<S> quotes) {
        List<S> resultQuotes = new ArrayList<>();

        for (Quote quote : quotes) {
            resultQuotes.add((S) save(quote));
        }

        return resultQuotes;
    }

    @Override
    public Optional<Quote> findById(String ticker) {
        Quote quote = null;
        String selectSql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " =?";
        try {
            quote = jdbcTemplate.queryForObject
                    (selectSql, BeanPropertyRowMapper.newInstance(Quote.class), ticker);
        }
        catch (EmptyResultDataAccessException e) {
            logger.debug("Can't find ticker: " + ticker, e);
        }

        return Optional.of(quote);
    }

    @Override
    public boolean existsById(String ticker) {
        String selectSql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " =?";

        boolean result = jdbcTemplate.queryForObject(selectSql, Integer.class, ticker) > 0;
        return result;
    }

    @Override
    public List<Quote> findAll() {
        String selectSql = "SELECT * FROM " + TABLE_NAME;

        return jdbcTemplate.query(selectSql, BeanPropertyRowMapper.newInstance(Quote.class));
    }

    @Override
    public long count() {
        String selectSql = "SELECT COUNT(*) FROM " + TABLE_NAME;

        return jdbcTemplate.queryForObject(selectSql, Long.class);
    }

    @Override
    public void deleteById(String ticker) {
        String deleteSql = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " =?";

        jdbcTemplate.update(deleteSql, ticker);
    }

    @Override
    public void deleteAll() {
        String deleteSql = "DELETE FROM " + TABLE_NAME;

        jdbcTemplate.update(deleteSql);
    }

    @Override
    public Iterable<Quote> findAllById(Iterable<String> strings) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public void delete(Quote entity) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends Quote> entities) {
        throw new UnsupportedOperationException("Not Implemented");
    }
}
