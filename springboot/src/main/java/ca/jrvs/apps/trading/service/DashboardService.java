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

        if (traderId == null) {
            throw new IllegalArgumentException("traderId must be nonempty");
        }

        TraderAccountView traderAccountView = new TraderAccountView();
        Trader trader = traderDao.findById(traderId)
                .orElseThrow(() -> new IllegalArgumentException("Trader not found"));
        Account account = accountDao.findById(traderId)
                .orElseThrow(() -> new IllegalArgumentException("Trader's Account not found"));

        traderAccountView.setTrader(trader);
        traderAccountView.setAccount(account);

        return traderAccountView;
    }

    public PortfolioView getProfileViewByTraderId(Integer traderId) {
        if (traderId == null) {
            throw new IllegalArgumentException("traderId must be nonempty");
        }

        PortfolioView portfolioView = new PortfolioView();
        Account account = accountDao.findById(traderId)
                .orElseThrow(() -> new IllegalArgumentException("Trader's account not found"));

        //List<Position> positions = Arrays.asList(positionDao.findById(account.getId()).get());
        List<Position> positions = Lists
                .newArrayList(positionDao.findAllById(Arrays.asList(account.getId())));

        List<SecurityRow> securityRows = new ArrayList<>();
        SecurityRow securityRow;
        String currentTicker;
        for (Position position : positions) {
            securityRow = new SecurityRow();
            securityRow.setPosition(position);
            currentTicker = position.getTicker();
            securityRow.setQuote(quoteDao.findById(currentTicker).orElseThrow(() ->
                    new IllegalArgumentException("Quote with specified ticker not found")));
            securityRow.setTicker(currentTicker);
            securityRows.add(securityRow);
        }

        portfolioView.setSecurityRows(securityRows);
        return portfolioView;
    }
}
