package org.anthony.bs.impl;

import org.anthony.bs.BookService;
import org.anthony.bs.OrderService;
import org.anthony.bs.domain.BsBookRepo;
import org.anthony.bs.domain.BsOrder;
import org.anthony.bs.enums.OrderStatus;
import org.anthony.bs.exception.BsException;
import org.anthony.bs.repository.BsBookRepoRepo;
import org.anthony.bs.repository.BsOrderRepo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private BsOrderRepo bsOrderRepo;

    @Resource
    private BsBookRepoRepo bsBookRepoRepo;

    @Resource
    private BookService bookService;

    @Override
    public Long orderNew(Long bid, Long uid) throws BsException {
        int count = bookService.count(bid);
        if (count <= 0) {
            throw BsException.of("02-002");
        }
        // wait processing count
        int waitingCount = bsOrderRepo.countStatus(bid, OrderStatus.NEW.getStatus());
        if (count - waitingCount <= 0) {
            throw BsException.of("02-002");
        }
        BsOrder bsOrder = new BsOrder();
        bsOrder.setBookId(bid);
        bsOrder.setUserId(uid);
        bsOrder.setStatus(OrderStatus.NEW.getStatus());
        bsOrder = bsOrderRepo.save(bsOrder);
        return bsOrder.getId();
    }

    @Override
    public Long completeOrder(Long oid) throws BsException {
        BsOrder bsOrder = find(oid);
        bsOrder.setStatus(OrderStatus.COMPLETED.getStatus());
        bsOrderRepo.save(bsOrder);
        // change repository
        BsBookRepo bsBookRepoInfo = bsBookRepoRepo.findById(bsOrder.getBookId()).get();
        bsBookRepoInfo.setBookCount(bsBookRepoInfo.getBookCount() - 1);
        bsBookRepoRepo.save(bsBookRepoInfo);
        return bsOrder.getId();
    }

    @Override
    public Long cancelOrder(Long oid) throws BsException {
        BsOrder bsOrder = find(oid);
        bsOrder.setStatus(OrderStatus.CANCELED.getStatus());
        bsOrderRepo.save(bsOrder);
        return bsOrder.getId();
    }

    @Override
    public OrderStatus status(Long oid) throws BsException {
        return OrderStatus.valueOfCode(find(oid).getStatus());
    }

    private BsOrder find(Long oid) throws BsException {
        Optional<BsOrder> optional = bsOrderRepo.findById(oid);
        if (!optional.isPresent()) {
            throw BsException.of("02-001");
        }
        return optional.get();
    }

}
