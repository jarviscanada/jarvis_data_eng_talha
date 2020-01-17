package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
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
public class AccountDaoIntTest {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TraderDao traderDao;

    private Account savedAccount;
    private Trader savedTrader;

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

        savedAccount = new Account();
        savedAccount.setAmount(500d);
        savedAccount.setTraderId(savedTrader.getId());
        accountDao.save(savedAccount);
    }

    @Test
    public void findAllById() {
        List<Account> accounts = Lists
                .newArrayList(accountDao.findAllById(Arrays.asList(savedAccount.getId())));
        assertEquals(1, accounts.size());
        assertEquals(savedAccount.getAmount(), accounts.get(0).getAmount());
        assertEquals(savedAccount.getTraderId(), accounts.get(0).getTraderId());
        assertEquals(savedAccount.getId(), accounts.get(0).getId());
    }

    @Test
    public void unImplementedMethods() {
        //Test unimplemented methods
        try {
            accountDao.updateOne(savedAccount);
            fail();
        }
        catch (UnsupportedOperationException e) {
            assertTrue(true);
        }

        try {
            accountDao.delete(savedAccount);
            fail();
        }
        catch (UnsupportedOperationException e) {
            assertTrue(true);
        }
    }

    @After
    public void delete() {
        accountDao.deleteById(savedAccount.getId());
        traderDao.deleteById(savedTrader.getId());
    }
}