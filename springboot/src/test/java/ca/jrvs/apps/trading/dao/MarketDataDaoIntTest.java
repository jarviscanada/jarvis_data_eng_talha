package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataRetrievalFailureException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MarketDataDaoIntTest {

    private MarketDataDao dao;

    @Before
    public void init() throws Exception {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(50);
        MarketDataConfig marketDataConfig = new MarketDataConfig();
        marketDataConfig.setHost("https://cloud.iexapis.com/v1/");
        marketDataConfig.setToken(System.getenv("IEX_PUB_TOKEN"));

        dao = new MarketDataDao(cm, marketDataConfig);
    }

    @Test
    public void findIexQuotesByTicker() throws IOException {
        //Working path
        List<IexQuote> quoteList = dao.findAllById(Arrays.asList("AAPL", "FB"));
        Assert.assertEquals(2, quoteList.size());
        Assert.assertEquals("AAPL", quoteList.get(0).getSymbol());

        //Sad path
        try {
            dao.findAllById(Arrays.asList("AAPL", "FB2"));
            fail();
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        catch (DataRetrievalFailureException e) {
            fail();
        }
    }

    @Test
    public void unImplementedMethods() {
        //Test unimplemented methods
        try {
            dao.existsById("AAPL");
            fail();
        }
        catch (UnsupportedOperationException e) {
            assertTrue(true);
        }

        try {
            dao.findAll();
            fail();
        }
        catch (UnsupportedOperationException e) {
            assertTrue(true);
        }

        try {
            dao.count();
            fail();
        }
        catch (UnsupportedOperationException e) {
            assertTrue(true);
        }
    }

    @Test
    public void findByTicker() {
        String ticker = "AAPL";
        IexQuote iexQuote = dao.findById(ticker).get();
        Assert.assertEquals(ticker, iexQuote.getSymbol());
    }
}