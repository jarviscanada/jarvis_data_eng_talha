package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService .class);

    private AccountDao accountDao;
    private SecurityOrderDao securityOrderDao;
    private QuoteDao quoteDao;
    private PositionDao positionDao;

    @Autowired
    public OrderService(AccountDao accountDao, SecurityOrderDao securityOrderDao, QuoteDao quoteDao,
                        PositionDao positionDao) {
        this.accountDao = accountDao;
        this.securityOrderDao = securityOrderDao;
        this.quoteDao = quoteDao;
        this.positionDao = positionDao;
    }

    //Execute a market order
    public SecurityOrder executeMarketOrder(MarketOrderDto marketOrderDto) {
        //First we need to check if size and ticker are null or if size is 0
        if (marketOrderDto.getSize() == null || marketOrderDto.getSize() == 0
            || marketOrderDto.getTicker() == null) {
                throw new IllegalArgumentException("All fields must not be null and size must be > 0");
        }

        //Need to create a security order so lets get the quote and account first
        Quote quote = quoteDao.findById(marketOrderDto.getTicker()).orElseThrow(() ->
                new IllegalArgumentException("The trader account specified could not be found"));

        Account account = accountDao.findById(marketOrderDto.getAccountId()).orElseThrow(() ->
                new IllegalArgumentException("No account found with specified accountId"));

        SecurityOrder securityOrder = new SecurityOrder();
        securityOrder.setAccountId(marketOrderDto.getAccountId());
        securityOrder.setSize(marketOrderDto.getSize());
        securityOrder.setTicker(marketOrderDto.getTicker());
        securityOrder.setStatus("STARTED");
        securityOrderDao.save(securityOrder);

        //If size is > 0 then execute buy, if its negative then execute sell
        if (securityOrder.getSize() > 0) {
            securityOrder.setPrice(quote.getAskPrice());
            handleBuyMarketOrder(marketOrderDto, securityOrder, account);
        }
        else {
            securityOrder.setPrice(quote.getBidPrice());
            handleSellMarketOrder(marketOrderDto, securityOrder, account);
        }

        //Finally save and return the securityOrder
        return securityOrderDao.save(securityOrder);
    }

    //Helper method that executes a buy order
    protected void handleBuyMarketOrder(MarketOrderDto marketOrderDto, SecurityOrder securityOrder,
                                        Account account) {
        Double cost = securityOrder.getPrice() * securityOrder.getSize();

        if (account.getAmount() >= cost) {
            account.setAmount(account.getAmount() - cost);

            accountDao.save(account);
            securityOrder.setStatus("FILLED");
        }
        else {
            securityOrder.setStatus("REJECTED");
            securityOrder.setNotes("Not enough funds in account for this Order");
        }
    }

    //Helper method that executes a sell order
    protected void handleSellMarketOrder(MarketOrderDto marketOrderDto, SecurityOrder securityOrder,
                                         Account account) {
        Position position = positionDao.findById(account.getId()).orElseThrow(() ->
                new IllegalArgumentException("Couldn't find position with the given accountId"));

        Double sellPrice = securityOrder.getPrice() * securityOrder.getSize();

        //First need to check if size is greater than position because size should be negative
        if (position.getPosition() + securityOrder.getSize() >= 0) {
            account.setAmount(account.getAmount() - sellPrice);
            accountDao.save(account);
            securityOrder.setStatus("FILLED");
        }
        else {
            securityOrder.setStatus("REJECTED");
            securityOrder.setNotes("Order size is greater than position");
        }
    }
}
