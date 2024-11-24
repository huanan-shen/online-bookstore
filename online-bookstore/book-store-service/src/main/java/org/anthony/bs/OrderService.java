package org.anthony.bs;


import org.anthony.bs.enums.OrderStatus;
import org.anthony.bs.exception.BsException;

/**
 * @author Anthony
 */
public interface OrderService {


    /**
     * order a book
     *
     * @param bid
     * @param uid
     * @return order id
     * @throws BsException
     */
    Long orderNew(Long bid, Long uid) throws BsException;

    /**
     * completed the order
     *
     * @param oid
     * @return
     * @throws BsException
     */
    Long completeOrder(Long oid) throws BsException;

    /**
     * canceled the order
     *
     * @param oid
     * @return
     * @throws BsException
     */
    Long cancelOrder(Long oid) throws BsException;

    OrderStatus status(Long oid) throws BsException;

}
