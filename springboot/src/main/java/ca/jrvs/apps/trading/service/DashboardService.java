package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.PortfolioView;
import ca.jrvs.apps.trading.model.view.SecurityRow;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class DashboardService {

    private TraderDao traderDao;
    private PositionDao positionDao;
    private AccountDao accountDao;
    private QuoteDao quoteDao;

    @Autowired
    public DashboardService(TraderDao traderDao, PositionDao positionDao, AccountDao accountDao,
                            QuoteDao quoteDao) {
        this.traderDao = traderDao;
        this.positionDao = positionDao;
        this.accountDao = accountDao;
        this.quoteDao = quoteDao;
    }

    public TraderAccountView getTraderAccount(Integer traderId) {
        //First check if tradeId is null
        if (traderId == null) {
            throw new IllegalArgumentException("traderId must not be null");
        }
        
        //Check if trader and account can be found
        Trader trader = traderDao.findById(traderId)
                .orElseThrow(() -> new IllegalArgumentException("Trader not found with specified id"));
        Account account = accountDao.findById(traderId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with specified id"));
        
        TraderAccountView traderAccountView = new TraderAccountView();

        //Create and return traderAccountView
        traderAccountView.setTrader(trader);
        traderAccountView.setAccount(account);

        return traderAccountView;
    }

    public PortfolioView getProfileViewByTraderId(Integer traderId) {
        //First check if tradeId is null
        if (traderId == null) {
            throw new IllegalArgumentException("traderId must be nonempty");
        }
        
        //Check if account can be found
        Account account = accountDao.findById(traderId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with specified id"));

        PortfolioView portfolioView = new PortfolioView();

        List<Position> positions = Lists
                .newArrayList(positionDao.findAllById(Arrays.asList(account.getId())));

        List<SecurityRow> securityRowsList = new ArrayList<>();
        SecurityRow securityRow;
        String ticker;
        
        for (Position position : positions) {
            //Setup new securityRow and add to list
            securityRow = new SecurityRow();
            securityRow.setPosition(position);
            ticker = position.getTicker();
            securityRow.setTicker(ticker);
            securityRow.setQuote(quoteDao.findById(ticker).orElseThrow(() ->
                    new IllegalArgumentException("Quote not found with specified ticker")));
            
            securityRowsList.add(securityRow);
        }

        portfolioView.setSecurityRows(securityRowsList);
        return portfolioView;
    }
}
