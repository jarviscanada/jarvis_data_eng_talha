package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
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
public class TraderDaoIntTest {

    @Autowired
    private TraderDao traderDao;

    private Trader savedTrader;

    @Before
    public void insertOne() {
        savedTrader = new Trader();
        //ID will be set automatically but need to manually set other fields
        savedTrader.setFirstName("John");
        savedTrader.setLastName("Doe");
        savedTrader.setDob(LocalDate.parse("1975-01-02"));
        savedTrader.setCountry("USA");
        savedTrader.setEmail("Johndoe@gmail.com");
        traderDao.save(savedTrader);
    }

    @Test
    public void findAllById() {
        List<Trader> traders = Lists
                .newArrayList(traderDao.findAllById(Arrays.asList(savedTrader.getId())));
        assertEquals(1, traders.size());
        assertEquals(savedTrader.getFirstName(), traders.get(0).getFirstName());
        assertEquals(savedTrader.getLastName(), traders.get(0).getLastName());
        assertEquals(savedTrader.getDob(), traders.get(0).getDob());
        assertEquals(savedTrader.getCountry(), traders.get(0).getCountry());
        assertEquals(savedTrader.getEmail(), traders.get(0).getEmail());
    }

    @Test
    public void unImplementedMethods() {
        //Test unimplemented methods
        try {
            traderDao.updateOne(savedTrader);
            fail();
        }
        catch (UnsupportedOperationException e) {
            assertTrue(true);
        }

        try {
            traderDao.delete(savedTrader);
            fail();
        }
        catch (UnsupportedOperationException e) {
            assertTrue(true);
        }
    }

    @After
    public void delete() {
        traderDao.deleteById(savedTrader.getId());
    }
}