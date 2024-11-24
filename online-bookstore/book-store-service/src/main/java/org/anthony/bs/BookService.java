package org.anthony.bs;

import org.anthony.bs.domain.BsBook;
import org.anthony.bs.exception.BsException;

import java.util.List;

/**
 * @author Anthony
 */
public interface BookService {

    Long save(BsBook bsBook) throws BsException;

    Long delete(Long bid) throws BsException;

    BsBook find(Long bid) throws BsException;

    List<BsBook> search(String keyword) throws BsException;

    int submitCount(Long bid, int count) throws BsException;

    int count(Long bid) throws BsException;

    int canOrderCount(Long bid) throws BsException;

}
