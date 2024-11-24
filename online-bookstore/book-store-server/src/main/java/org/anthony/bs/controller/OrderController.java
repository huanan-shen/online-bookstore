package org.anthony.bs.controller;

import org.anthony.bs.OrderService;
import org.anthony.bs.UserContext;
import org.anthony.bs.enums.OrderStatus;
import org.anthony.bs.exception.BsException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Anthony
 */
@RestController
@RequestMapping(value = "order")
public class OrderController {

    @Resource
    private OrderService orderService;


    @PostMapping(value = "/new")
    public Long newOrder(@RequestParam Long bid) throws BsException {
        return orderService.orderNew(bid, UserContext.currentUser().getBsUser().getId());
    }


    @GetMapping(value = "/status")
    public OrderStatus status(@RequestParam Long oid) throws BsException {
        return orderService.status(oid);
    }


    @PostMapping(value = "/completed")
    public Long completed(@RequestParam Long oid) throws BsException {
        return orderService.completeOrder(oid);
    }

    @PostMapping(value = "/cancel")
    public Long cancel(@RequestParam Long oid) throws BsException {
        return orderService.cancelOrder(oid);
    }

}
