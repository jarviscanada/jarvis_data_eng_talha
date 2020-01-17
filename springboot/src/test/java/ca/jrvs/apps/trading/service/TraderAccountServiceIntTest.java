package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.domain.TraderAccountView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class TraderAccountServiceIntTest {

    private TraderAccountView savedView;

    @Autowired
    private TraderAccountService traderAccountService;

    @Autowired
    private TraderDao traderDao;

    @Autowired
    private AccountDao accountDao;

    private Integer traderId;

    @Before
    public void insertOne() {
        Trader newTrader = new Trader();
        newTrader.setFirstName("Jane");
        newTrader.setLastName("Doe");
        newTrader.setEmail("Jane@gmail.com");
        newTrader.setDob(LocalDate.parse("1996-12-22"));
        newTrader.setCountry("Canada");

        savedView = traderAccountService.createTraderAndAccount(newTrader);

        traderId = savedView.getTrader().getId();
    }

    @Test
    public void depositAndWithdraw() {
        //First lets test a negative deposit value
        try {
            traderAccountService.deposit(traderId, -100d);
            fail();
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
        }

        //Test deposit method with appropriate amount
        double testAmount = savedView.getAccount().getAmount() + 100d;
        assertTrue(testAmount == traderAccountService.deposit(traderId, 100d).getAmount());

        //Test withdraw method with appropriate amount
        testAmount = savedView.getAccount().getAmount() + 100d - 50d;
        assertTrue(testAmount == traderAccountService.withdraw(traderId, 50d).getAmount());

        //Lets withdraw remaining balance of 50 so that we can delete this trader
        traderAccountService.withdraw(traderId, 50d).getAmount();
    }

    @After
    public void delete() {
        traderAccountService.deleteTraderById(traderId);
    }
}