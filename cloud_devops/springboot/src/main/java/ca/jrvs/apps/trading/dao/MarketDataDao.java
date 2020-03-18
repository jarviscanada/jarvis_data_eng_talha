package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class MarketDataDao implements CrudRepository<IexQuote, String> {

    private static final String IEX_BATCH_PATH = "stock/market/batch?symbols=%s&types=quote&token=";
    private final String IEX_BATCH_URL;

    private Logger logger = LoggerFactory.getLogger(MarketDataDao.class);
    private HttpClientConnectionManager httpClientConnectionManager;

    @Autowired
    public MarketDataDao(HttpClientConnectionManager httpClientConnectionManager, MarketDataConfig marketDataConfig) {
        this.httpClientConnectionManager = httpClientConnectionManager;
        IEX_BATCH_URL = marketDataConfig.getHost() + IEX_BATCH_PATH + marketDataConfig.getToken();
    }

    /**
     * Get an IexQuote (helper method which calls findAllById)
     *
     * @param ticker
     * @throws IllegalArgumentException if the given ticker is invalid
     * @throws DataRetrievalFailureException if the HTTP request failed
     */
    @Override
    public Optional<IexQuote> findById(String ticker) {
        Optional<IexQuote> iexQuote;
        List<IexQuote> quotes = findAllById(Collections.singletonList(ticker));

        if (quotes.size() == 0) {
            return Optional.empty();
        }
        else if (quotes.size() == 1) {
            iexQuote = Optional.of(quotes.get(0));
        }
        else {
            throw new DataRetrievalFailureException("Unexpected number of quotes");
        }

        return iexQuote;
    }

    /**
     * Gets quotes from IEX
     *
     * @param tickers is a list of tickers
     * @return a list of IexQuote objects
     * @throws IllegalArgumentException if any given ticker is invalid or if tickers is empty
     * @throws DataRetrievalFailureException if the HTTP request failed
     */
    @Override
    public List<IexQuote> findAllById(Iterable<String> tickers) {
        int tickerCount = 0;

        //Go through tickers and make sure they are proper format
        for (String ticker : tickers) {
            if (!ticker.matches("[a-zA-Z]*")) {
                throw new IllegalArgumentException("Incorrect ticker format");
            }
            tickerCount++;
        }

        //Check if tickers is empty
        if (tickerCount == 0) {
            throw new IllegalArgumentException("No tickers specified");
        }

        //Construct url with all tickers
        String tickerString = String.join(",", tickers);
        String url = String.format(IEX_BATCH_URL, tickerString);

        //Execute httpGet then convert the response to JSON
        Optional<String> response = executeHttpGet(url);
        JSONObject jsonQbject = new JSONObject(response.get());

        //return a list of quotes after mapping JSON to IexQuote objects
        List<IexQuote> listOfQuotes = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        for (String ticker : tickers) {
            try {
                String quoteString = jsonQbject.getJSONObject(ticker).get("quote").toString();
                IexQuote quote = mapper.readValue(quoteString, IexQuote.class);
                listOfQuotes.add(quote);
            }
            catch (IOException e) {
                throw new DataRetrievalFailureException("Error converting JSON to quote object.");
            }
        }
        return listOfQuotes;
    }

    /**
     * Execute a get and return http entity/body as a string
     *
     * @param url resource URL
     * @return HTTP response body or Optional.empty for 404 response
     * @throws DataRetrievalFailureException if HTTP failed or status code is unexpected
     */
    private Optional<String> executeHttpGet(String url) {
        HttpClient httpClient = getHttpClient();
        HttpGet httpGet = new HttpGet(url);

        try {
            HttpResponse response = httpClient.execute(httpGet);
            int status = response.getStatusLine().getStatusCode();

            if (status != 200) {
                throw new DataRetrievalFailureException("Unexpected status code: " + status);
            }

            HttpEntity responseBody = response.getEntity();
            return Optional.of(EntityUtils.toString(responseBody));
        }
        catch (IOException e) {
            throw new DataRetrievalFailureException("HTTP Get Request failed");
        }
    }

    /**
     * Borrow a HTTP Client from the httpClientConnectionManager
     * @return a httpClient
     */
    private CloseableHttpClient getHttpClient() {
        return HttpClients.custom()
                .setConnectionManager(httpClientConnectionManager)
                .setConnectionManagerShared(true)
                .build();
    }

    @Override
    public boolean existsById(String s) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Iterable<IexQuote> findAll() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteById(String s) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void delete(IexQuote iexQuote) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends IexQuote> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public <S extends IexQuote> S save(S s) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public <S extends IexQuote> Iterable<S> saveAll(Iterable<S> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
