package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteServiceIntTest {

    @Autowired
    private QuoteDao quoteDao;

    @Autowired
    private QuoteService quoteService;

    @Before
    public void setUp() {
        //Lets create three quotes and save them with save method in dao
        Quote quote = new Quote();
        Quote quote2 = new Quote();
        Quote quote3 = new Quote();

        quote.setID("FB");
        quote.setLastPrice(10.1d);
        quote.setBidPrice(10.2d);
        quote.setBidSize(10);
        quote.setAskPrice(10d);
        quote.setAskSize(10);
        quoteDao.save(quote);

        quote2.setID("AAPL");
        quote2.setLastPrice(10.1d);
        quote2.setBidPrice(10.2d);
        quote2.setBidSize(10);
        quote2.setAskPrice(10d);
        quote2.setAskSize(10);
        quoteDao.save(quote2);

        quote3.setID("AMZN");
        quote3.setLastPrice(10.1d);
        quote3.setBidPrice(10.2d);
        quote3.setBidSize(10);
        quote3.setAskPrice(10d);
        quote3.setAskSize(10);
        quoteDao.save(quote3);
    }

    @Test
    public void updateMarketData() {
        quoteService.updateMarketData();

        assertTrue(quoteDao.existsById("FB"));
        assertFalse(quoteDao.existsById("This shouldnt work"));

        //There should be 3 quotes now so count should return 3
        assertEquals(3, quoteDao.count());
    }

    @Test
    public void findIexQuoteByTicker() {
        IexQuote iexQuote = quoteService.findIexQuoteByTicker("FB");

        assertEquals("FB", iexQuote.getSymbol());
    }

    @Test
    public void saveQuotes() {
        List<String> tickersList = new ArrayList();
        tickersList.add("FB");
        tickersList.add("AAPL");
        tickersList.add("AMZN");

        //First lets test saveQuotes method
        List<Quote> savedQuotes = quoteService.saveQuotes(tickersList);
        assertTrue(tickersList.get(0).matches(savedQuotes.get(0).getId()));
        assertTrue(tickersList.get(1).matches(savedQuotes.get(1).getId()));
        assertTrue(tickersList.get(2).matches(savedQuotes.get(2).getId()));

        //Test saveQuote method
        String tickerName = "MSFT";
        tickersList.add(tickerName);
        Quote quote = quoteService.saveQuote(tickerName);
        assertTrue(quote.getId().matches(tickerName));

        //Test findAllQuotes method
        List<Quote> quotes = quoteService.findAllQuotes();
        for (Quote testQuote : quotes) {
            assertTrue(tickersList.contains(testQuote.getTicker()));
        }
        //TickerList and found quotes should both have length of 4 now
        assertTrue(tickersList.size() == quotes.size());
    }

    @After
    public void tearDown() throws Exception {
        quoteDao.deleteAll();
    }
}