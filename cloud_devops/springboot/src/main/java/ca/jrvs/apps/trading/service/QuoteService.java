package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class QuoteService {

    private static final Logger logger = LoggerFactory.getLogger(QuoteService .class);

    private QuoteDao quoteDao;
    private MarketDataDao marketDataDao;

    @Autowired
    public QuoteService(QuoteDao quoteDao, MarketDataDao marketDataDao) {
        this.quoteDao = quoteDao;
        this.marketDataDao = marketDataDao;
    }

    /**
     * Find an IexQuote
     *
     * @param ticker
     * @return IexQuote object
     * @throws IllegalArgumentException if ticker is invalid
     */
    public IexQuote findIexQuoteByTicker(String ticker) {
        Optional<IexQuote> result = Optional.ofNullable(marketDataDao.findById(ticker)
                .orElseThrow(() -> new IllegalArgumentException(ticker + "is invalid")));

        //Now lets check if any values are null, if so then set them to 0
        if (result.get().getIexAskPrice() == null) {
            result.get().setIexAskPrice(0.0);
        }
        if (result.get().getIexAskSize() == null) {
            result.get().setIexAskSize(0);
        }
        if (result.get().getIexBidPrice() == null) {
            result.get().setIexBidPrice(0.0);
        }
        if (result.get().getIexBidSize() == null) {
            result.get().setIexBidSize(0);
        }
        if (result.get().getLatestTime() == null) {
            result.get().setLatestPrice(0.0);
        }

        return result.get();
    }

    /**
     * Update quote table against IEX source
     *  Steps:
     * - get all quotes from the db
     * - for each ticker get iexQuote
     * - convert iexQuote to quote entity
     * - persistt quote to db
     *
     * @throws org.springframework.dao.DataAccessException if unable to retrieve data
     * @throws IllegalArgumentException for invalid input
     */
    public void updateMarketData() {
        //First we need to get quotes using findAll method
        List<Quote> quotes = quoteDao.findAll();

        List<Quote> quotesToSave = new ArrayList<>();
        IexQuote iexQuote;
        Quote quoteToSave;

        for (Quote quote : quotes){
            String ticker = quote.getId();
            iexQuote = findIexQuoteByTicker(ticker);
            quoteToSave = buildQuoteFromIexQuote(iexQuote);
            quotesToSave.add(quoteToSave);
        }

        quoteDao.saveAll(quotesToSave);
    }

    /**
     * Helper method. Map a IexQuote to a Quote entity.
     * Note: `iexQuote.getLatestPrice() == null` if the stock market is closed.
     * Make sure set a default value for number field(s).
     */
    protected static Quote buildQuoteFromIexQuote(IexQuote iexQuote) {
        Quote quote = new Quote();

        quote.setTicker(iexQuote.getSymbol());
        quote.setLastPrice(iexQuote.getLatestPrice());
        quote.setAskPrice(iexQuote.getIexAskPrice());
        quote.setAskSize(iexQuote.getIexAskSize());
        quote.setBidPrice(iexQuote.getIexBidPrice());
        quote.setBidSize(iexQuote.getIexBidSize());

        //Now lets check if any values are null, if so then set them to 0
        if (quote.getAskPrice() == null) {
            quote.setAskPrice(0.0);
        }
        if (quote.getAskSize() == null) {
            quote.setAskSize(0);
        }
        if (quote.getBidPrice() == null) {
            quote.setBidPrice(0.0);
        }
        if (quote.getBidSize() == null) {
            quote.setBidSize(0);
        }

        return quote;

    }
    /**
     * Validate (against IEX) and save given tickers to quote table.
     *
     * - Get iexQuote(s)
     * - convert each iexQuote to Quote entity
     * - persist the quote to db
     *
     * @param tickers a list of tickers/symbols
     * @throws IllegalArgumentException if ticker is not found
     */
    public List<Quote> saveQuotes(List<String> tickers){

        List<Quote> quotes = new ArrayList<>();
        for (String ticker : tickers){
            quotes.add(saveQuote(ticker));
        }
        return quotes;
    }

    /**
     * helper method for saveQuotes
     * @param ticker
     * @return the found Quote
     */
    public Quote saveQuote(String ticker){
        IexQuote iexQuote;
        iexQuote = marketDataDao.findById(ticker).get();
        Quote quote = buildQuoteFromIexQuote(iexQuote);
        return saveQuote(quote);
    }

    /**
     * Update a given quote to quote table without validation
     * @param quote entity
     */
    public Quote saveQuote(Quote quote){
        return quoteDao.save(quote);
    }

    /**
     * Find all quotes from the quote table
     * @return a list of quotes
     */
    public List<Quote> findAllQuotes(){
        return quoteDao.findAll();
    }
}
