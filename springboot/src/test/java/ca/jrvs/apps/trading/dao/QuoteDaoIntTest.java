package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
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
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteDaoIntTest {

    @Autowired
    private QuoteDao quoteDao;

    private List<Quote> savedQuotes = new ArrayList<>();

    @Before
    public void insertFourQuotes() {
        List<Quote> newQuotes = new ArrayList<>();
        String[] tickerIds = {"AAPL", "FB", "AMZN", "TD"};

        //Lets loop through and add each as a quote into savedQuotes
        for (String ticker : tickerIds) {
            Quote quote = new Quote();
            quote.setID(ticker);
            quote.setAskPrice(10d);
            quote.setAskSize(10);
            quote.setBidPrice(10.2d);
            quote.setBidSize(10);
            quote.setLastPrice(10.1d);
            newQuotes.add(quote);
        }

        //Now we can call saveAll method in quoteDao with our list of quotes
        List<Quote> savedQuotes = quoteDao.saveAll(newQuotes);

        //Test to see if savedQuotes is same as our newQuotes list
        int count = 0;
        for (String ticker : tickerIds) {
            assertEquals(newQuotes.get(count).getId(), savedQuotes.get(count).getId());
            assertEquals(newQuotes.get(count).getAskPrice(), savedQuotes.get(count).getAskPrice());
            assertEquals(savedQuotes.get(count).getBidPrice(), savedQuotes.get(count).getBidPrice());
            count++;
        }

        //Now lets call findAll method in quoteDao
        List<Quote> foundQuotes = quoteDao.findAll();

        //Test to see if foundQuotes is same as our savedQuotes list
        count = 0;
        for (String ticker : tickerIds) {
            assertEquals(savedQuotes.get(count).getId(), foundQuotes.get(count).getId());
            assertEquals(savedQuotes.get(count).getAskPrice(), foundQuotes.get(count).getAskPrice());
            assertEquals(savedQuotes.get(count).getBidPrice(), foundQuotes.get(count).getBidPrice());
        }

        //Now lets test findById method with only one ticker
        String AAPLTicker = savedQuotes.get(0).getId();
        Optional<Quote> quote = quoteDao.findById(AAPLTicker);

        assertEquals(quote.get().getId(), savedQuotes.get(0).getId());
        assertEquals(quote.get().getAskPrice(), savedQuotes.get(0).getAskPrice());
        assertEquals(quote.get().getAskSize(), savedQuotes.get(0).getAskSize());
        assertEquals(quote.get().getBidPrice(), savedQuotes.get(0).getBidPrice());

        //Test existsById method
        assertTrue(quoteDao.existsById(quote.get().getId()));
        assertFalse(quoteDao.existsById("This Shouldnt work"));
    }

    @Test
    public void QuoteDaoMethods() {
        //Lets add one quote
        Quote quote = new Quote();
        quote.setID("TESTTICKER");
        quote.setAskPrice(10d);
        quote.setAskSize(10);
        quote.setBidPrice(10.2d);
        quote.setBidSize(10);
        quote.setLastPrice(10.1d);

        Quote saveQuote = quoteDao.save(quote);
        assertEquals(quote.getId(), saveQuote.getId());
        assertEquals(quote.getAskPrice(), saveQuote.getAskPrice());
        assertEquals(quote.getAskSize(), saveQuote.getAskSize());
        assertEquals(quote.getBidPrice(), saveQuote.getBidPrice());

        //Now lets try to update quote
        quote = new Quote();
        quote.setID("TESTTICKER");
        quote.setAskPrice(20d);
        quote.setAskSize(20);
        quote.setBidPrice(20.2d);
        quote.setBidSize(20);
        quote.setLastPrice(20.1d);
        Quote updateQuote = quoteDao.save(quote);

        assertEquals(quote.getId(), updateQuote.getId());
        assertEquals(quote.getAskPrice(), updateQuote.getAskPrice());
        assertEquals(quote.getAskSize(), updateQuote.getAskSize());
        assertEquals(quote.getBidPrice(), updateQuote.getBidPrice());

        //Now that we added one more, our quoteDao should have 5 quotes
        assertTrue(quoteDao.count() == 5);

        //Now lets delete the quote we just saved
        quoteDao.deleteById(saveQuote.getId());

        //Should be back to 4 quotes now
        assertTrue(quoteDao.count() == 4);
    }

    @After
    public void deleteQuotes() {
        quoteDao.deleteAll();
    }
}