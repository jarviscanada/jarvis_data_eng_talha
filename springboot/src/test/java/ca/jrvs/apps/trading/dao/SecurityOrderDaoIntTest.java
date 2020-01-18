package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class SecurityOrderDaoIntTest {

    //SecurityOrder references Account and Quote so need all those Daos
    @Autowired
    private SecurityOrderDao securityOrderDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TraderDao traderDao;

    @Autowired
    private QuoteDao quoteDao;

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
        savedSecurityOrder.setStatus("No Status");
        savedSecurityOrder.setTicker(savedQuote.getTicker());
        savedSecurityOrder.setSize(100);
        savedSecurityOrder.setPrice(550d);
        savedSecurityOrder.setNotes("Test Security Order");
        securityOrderDao.save(savedSecurityOrder);
    }

    @Test
    public void findAllById() {
        List<SecurityOrder> securityOrders = Lists
                .newArrayList(securityOrderDao.findAllById(Arrays.asList(savedSecurityOrder.getAccountId())));
        assertEquals(1, securityOrders.size());

        assertEquals(savedSecurityOrder.getStatus(), securityOrders.get(0).getStatus());
        assertEquals(savedSecurityOrder.getTicker(), securityOrders.get(0).getTicker());
        assertEquals(savedSecurityOrder.getSize(), securityOrders.get(0).getSize());
        assertEquals(savedSecurityOrder.getPrice(), securityOrders.get(0).getPrice());
        assertEquals(savedSecurityOrder.getNotes(), securityOrders.get(0).getNotes());
        assertEquals(savedSecurityOrder.getId(), securityOrders.get(0).getId());
    }

    @Test
    public void unImplementedMethods() {
        //Test unimplemented methods
        try {
            securityOrderDao.updateOne(savedSecurityOrder);
            fail();
        }
        catch (UnsupportedOperationException e) {
            assertTrue(true);
        }

        try {
            securityOrderDao.delete(savedSecurityOrder);
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