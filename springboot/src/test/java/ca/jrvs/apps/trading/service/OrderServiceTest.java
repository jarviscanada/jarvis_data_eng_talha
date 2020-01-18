package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @Captor
    ArgumentCaptor<SecurityOrder> captorSecurityOrder;

    @Mock
    AccountDao accountDao;
    @Mock
    SecurityOrderDao securityOrderDao;
    @Mock
    QuoteDao quoteDao;
    @Mock
    PositionDao positionDao;

    @InjectMocks
    public OrderService orderService;

    private MarketOrderDto marketOrderDto;
    private Account account;
    private Quote quote;
    private Position position;

    @Before
    public void setUp() {
        //Lets setup all objects to test
        marketOrderDto = new MarketOrderDto();
        marketOrderDto.setAccountId(5);
        marketOrderDto.setTicker("TEST");

        quote = new Quote();
        quote.setTicker("TEST");
        quote.setLastPrice(100d);
        quote.setAskPrice(50d);
        quote.setAskSize(50);
        quote.setBidPrice(50d);
        quote.setBidSize(50);
        when(quoteDao.findById(any())).thenReturn(Optional.of(quote));

        account = new Account();
        account.setID(5);
        account.setTraderId(5);
        //Initial account balance set to 0, will change this later
        account.setAmount(0d);
        when(accountDao.findById(any())).thenReturn(Optional.of(account));

        position = new Position();
        position.setAccountId(5);
        position.setTicker("TEST");
        when(positionDao.findById(any())).thenReturn(Optional.of(position));
    }

    @Test
    public void workingBuyOrder() {
        //For a size of 50, we need an account balance of 2500 to get the order filled
        marketOrderDto.setSize(50);
        account.setAmount(2500d);
        orderService.executeMarketOrder(marketOrderDto);

        //Need two invocations so set times to 2
        verify(securityOrderDao, times(2)).save(captorSecurityOrder.capture());
        assertEquals(captorSecurityOrder.getAllValues().get(1).getStatus(), "FILLED");
    }

    @Test
    public void rejectedBuyOrder() {
        //Lets test if we dont have enough balance
        marketOrderDto.setSize(50);
        account.setAmount(250d);
        orderService.executeMarketOrder(marketOrderDto);

        //Need two invocations so set times to 2
        verify(securityOrderDao, times(2)).save(captorSecurityOrder.capture());
        assertEquals(captorSecurityOrder.getAllValues().get(1).getStatus(), "REJECTED");
    }

    @Test
    public void workingSellOrder() {
        //Size + position should be 0 or greater
        marketOrderDto.setSize(-50);
        position.setPosition(50);
        orderService.executeMarketOrder(marketOrderDto);

        //Need two invocations so set times to 2
        verify(securityOrderDao, times(2)).save(captorSecurityOrder.capture());
        assertEquals(captorSecurityOrder.getAllValues().get(1).getStatus(), "FILLED");
    }

    @Test
    public void rejectedSellOrder() {
        marketOrderDto.setSize(-50);
        position.setPosition(10);
        orderService.executeMarketOrder(marketOrderDto);

        //Need two invocations so set times to 2
        verify(securityOrderDao, times(2)).save(captorSecurityOrder.capture());
        assertEquals(captorSecurityOrder.getAllValues().get(1).getStatus(), "REJECTED");
    }
}