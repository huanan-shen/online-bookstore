package org.anthony.bs;

import org.anthony.bs.domain.BsOrder;
import org.anthony.bs.enums.OrderStatus;
import org.anthony.bs.exception.BsException;
import org.anthony.bs.impl.OrderServiceImpl;
import org.anthony.bs.repository.BsBookRepoRepo;
import org.anthony.bs.repository.BsOrderRepo;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.annotation.Bean;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @Mock
    private BsOrderRepo bsOrderRepo;

    @Mock
    private BsBookRepoRepo bsBookRepoRepo;

    @Mock
    private BookService bookService;

    @InjectMocks
    private OrderServiceImpl orderServiceImpl;

    @Bean
    public void setUp() {

    }

    @After
    public void close() {

    }

    @Test
    public void testOrderNew() throws BsException {
        long bid = 1L;
        long uid = 1L;
        //
        Mockito.when(bookService.count(Mockito.any())).thenReturn(0);
        try {
            orderServiceImpl.orderNew(bid, uid);
        } catch (BsException e) {
            assert e.getErrCode().equals("02-002");
        }
        //
        Mockito.when(bookService.count(Mockito.any())).thenReturn(10);
        Mockito.when(bsOrderRepo.countStatus(Mockito.any(), Mockito.any())).thenReturn(10);
        try {
            orderServiceImpl.orderNew(bid, uid);
        } catch (BsException e) {
            assert e.getErrCode().equals("02-002");
        }
        Mockito.when(bsOrderRepo.countStatus(Mockito.any(), Mockito.any())).thenReturn(5);
        BsOrder bsOrder = new BsOrder();
        bsOrder.setId(100L);
        bsOrder.setBookId(bid);
        bsOrder.setUserId(uid);
        bsOrder.setStatus(OrderStatus.NEW.getStatus());
        Mockito.when(bsOrderRepo.save(Mockito.any())).thenReturn(bsOrder);
        long orderId = orderServiceImpl.orderNew(bid, uid);
        assert orderId == 100L;
    }

}
