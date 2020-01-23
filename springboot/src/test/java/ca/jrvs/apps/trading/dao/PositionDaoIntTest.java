package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.*;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class PositionDaoIntTest {

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private SecurityOrderDao securityOrderDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TraderDao traderDao;

    @Autowired
    private QuoteDao quoteDao;

    private Position savedPosition;
    private SecurityOrder savedSecurityOrder;
    private Trader savedTrader;
    private Account savedAccount;
    private Quote savedQuote;

    @Before
    public void insertOne() throws Exception {
        savedTrader = new Trader();
        //ID will be set automatically but need to manually set other fields
        savedTrader.setFirstName("John");
        savedTrader.setLastName("Doe");
        savedTrader.setDob(LocalDate.parse("1975-01-02"));
        savedTrader.setCountry("USA");
        savedTrader.setEmail("Johndoe@gmail.com");
        traderDao.save(savedTrader);

        savedQuote = new Quote();
        savedQuote.setID("AMZN");
        savedQuote.setAskPrice(10d);
        savedQuote.setAskSize(10);
        savedQuote.setBidPrice(10.2d);
        savedQuote.setBidSize(10);
        savedQuote.setLastPrice(10.1d);
        quoteDao.save(savedQuote);

        savedAccount = new Account();
        savedAccount.setAmount(500d);
        savedAccount.setTraderId(savedTrader.getId());
        accountDao.save(savedAccount);

        savedSecurityOrder = new SecurityOrder();
        savedSecurityOrder.setAccountId(savedAccount.getId());
        savedSecurityOrder.setID(savedAccount.getId());
        savedSecurityOrder.setStatus("FILLED");
        savedSecurityOrder.setTicker(savedQuote.getTicker());
        savedSecurityOrder.setSize(100);
        savedSecurityOrder.setPrice(550d);
        savedSecurityOrder.setNotes("Test Security Order");
        securityOrderDao.save(savedSecurityOrder);

        savedPosition = new Position();
        savedPosition.setAccountId(savedAccount.getId());
        savedPosition.setID(savedAccount.getId());
        savedPosition.setTicker(savedQuote.getTicker());
        savedPosition.setPosition(savedSecurityOrder.getSize());
    }

    @Test
    public void findAllById() {
        List<Position> positions = Lists
                .newArrayList(positionDao.findAllById(Arrays.asList(1)));
        assertEquals(1, positions.size());

        assertEquals(savedPosition.getTicker(), positions.get(0).getTicker());
        assertEquals(savedPosition.getPosition(), positions.get(0).getPosition());
        assertEquals(savedPosition.getId(), positions.get(0).getId());
    }

    @Test
    public void unImplementedMethods() {
        //Test unimplemented methods
        try {
            positionDao.updateOne(savedPosition);
            fail();
        }
        catch (UnsupportedOperationException e) {
            assertTrue(true);
        }

        try {
            positionDao.delete(savedPosition);
            fail();
        }
        catch (UnsupportedOperationException e) {
            assertTrue(true);
        }
    }

    @After
    public void delete() {
        securityOrderDao.deleteById(savedSecurityOrder.getAccountId());
        accountDao.deleteById(savedAccount.getId());
        quoteDao.deleteById(savedQuote.getId());
        traderDao.deleteById(savedTrader.getId());
    }
}