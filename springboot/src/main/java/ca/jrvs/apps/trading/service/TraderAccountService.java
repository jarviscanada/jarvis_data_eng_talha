package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraderAccountService {

    private TraderDao traderDao;
    private AccountDao accountDao;
    private PositionDao positionDao;
    private SecurityOrderDao securityOrderDao;

    @Autowired
    public TraderAccountService(TraderDao traderDao, AccountDao accountDao,
                                PositionDao positionDao, SecurityOrderDao securityOrderDao) {
        this.traderDao = traderDao;
        this.accountDao = accountDao;
        this.positionDao = positionDao;
        this.securityOrderDao = securityOrderDao;
    }

    /**
     * Create a new trader and initialize a new account with 0 amount
     * @param trader
     * @return TraderAccountView of created trader
     */
    public TraderAccountView createTraderAndAccount(Trader trader) {
        //First we need to check if any fields other than id are null
        if (trader.getFirstName() == null || trader.getLastName() == null || trader.getDob() == null
                || trader.getEmail() == null || trader.getCountry() == null) {
            throw new IllegalArgumentException("Every field other than id must not be null");
        }
        if (trader.getId() != null) {
            throw new IllegalArgumentException("Trader id must be null, it will be set by the database");
        }

        Trader createTrader = traderDao.save(trader);

        Account account = new Account();
        account.setTraderId(createTrader.getId());
        account.setAmount(0d);
        account = accountDao.save(account);

        //Need to return a traderAccountView object so lets create that now
        TraderAccountView traderAccountView = new TraderAccountView();
        traderAccountView.setTrader(createTrader);
        traderAccountView.setAccount(account);

        return traderAccountView;
    }

    /**
     * A trader can be deleted iff it has no open position and 0 cash balance
     * @param traderId
     */
    public void deleteTraderById(Integer traderId) {
        //First lets do all checks and return IllegalArgumentException if needed
        if (traderId == null) {
            throw new IllegalArgumentException("Id must not be null");
        }

        Account account = accountDao.findById(traderId).orElseThrow(() ->
                new IllegalArgumentException("The trader account specified could not be found"));

        if (account.getAmount() != 0d) {
            throw new IllegalArgumentException("Trader account balance must first be Zero");
        }

        accountDao.deleteById(traderId);
        traderDao.deleteById(traderId);
    }

    /**
     * Deposit a fund to an account by traderId
     * @param traderId
     * @param fund
     * @return Account
     */
    public Account deposit(Integer traderId, Double fund) {
        //First lets do all checks and return IllegalArgumentException if needed
        if (traderId == null) {
            throw new IllegalArgumentException("Id must not be null");
        }
        if (fund == 0d) {
            throw new IllegalArgumentException("Fund cannot be zero");
        }
        if (fund < 0d) {
            throw new IllegalArgumentException("Fund cannot be negative");
        }

        Account account = accountDao.findById(traderId).orElseThrow(() ->
                new IllegalArgumentException("The trader account specified could not be found"));

        Double newBalance = account.getAmount() + fund;
        account.setAmount(newBalance);

        return accountDao.save(account);
    }

    /**
     * Withdraw a fund to an account by traderId
     * @param traderId
     * @param fund
     * @return Account
     */
    public Account withdraw(Integer traderId, Double fund) {
        //First lets do all checks and return IllegalArgumentException if needed
        if (traderId == null) {
            throw new IllegalArgumentException("traderId cannot be null");
        } else if (fund <= 0) {
            throw new IllegalArgumentException("fund must be greater than 0");
        }

        Account account = accountDao.findById(traderId).orElseThrow(() ->
                new IllegalArgumentException("The trader account specified could not be found"));

        //Check if we have enough balance, if we do then return the updated account
        Double balance = account.getAmount();
        Double remainingBalance = balance - fund;

        if (remainingBalance >= 0) {
            account.setAmount(remainingBalance);
            return accountDao.save(account);
        }
        else {
            throw new IllegalArgumentException("Not enough balance in account");
        }
    }
}
