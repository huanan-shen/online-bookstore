package org.anthony.bs.impl;

import org.anthony.bs.BookService;
import org.anthony.bs.domain.BsBook;
import org.anthony.bs.domain.BsBookRepo;
import org.anthony.bs.enums.BooleanEnum;
import org.anthony.bs.enums.OrderStatus;
import org.anthony.bs.exception.BsException;
import org.anthony.bs.repository.BookRepo;
import org.anthony.bs.repository.BsBookRepoRepo;
import org.anthony.bs.repository.BsOrderRepo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Resource
    private BookRepo bookRepo;

    @Resource
    private BsOrderRepo bsOrderRepo;

    @Resource
    private BsBookRepoRepo bsBookRepoRepo;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Long save(BsBook bsBook) throws BsException {
        bsBook.setIsActive(BooleanEnum.TRUE.getCode());
        // insert or update to DB
        bsBook = bookRepo.save(bsBook);
        return bsBook.getId();
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Long delete(Long bid) throws BsException {
        bookRepo.findById(bid).ifPresent(
                e -> {
                    e.setIsActive(BooleanEnum.FALSE.getCode());
                    bookRepo.save(e);
                }
        );
        return bid;
    }

    @Override
    public BsBook find(Long bid) throws BsException {
        Optional<BsBook> optional = bookRepo.findById(bid);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public List<BsBook> search(String keyword) throws BsException {
        return bookRepo.searchByName(keyword);
    }

    @Override
    public int submitCount(Long bid, int count) throws BsException {
        find(bid);
        //
        Optional<BsBookRepo> bsBookRepoOptional = bsBookRepoRepo.findById(bid);
        BsBookRepo bsBookRepo;
        if (bsBookRepoOptional.isPresent()) {
            bsBookRepo = bsBookRepoOptional.get();
        } else {
            bsBookRepo = new BsBookRepo();
        }
        bsBookRepo.setId(bid);
        bsBookRepo.setBookCount(count);
        bsBookRepoRepo.save(bsBookRepo);
        return count;
    }

    @Override
    public int count(Long bid) throws BsException {
        Integer count = bsBookRepoRepo.count(bid);
        if (count == null) {
            count = 0;
        }
        return count;
    }

    @Override
    public int canOrderCount(Long bid) throws BsException {
        int count = count(bid);
        if (count <= 0) {
            return count;
        }
        // wait processing count
        int waitingCount = bsOrderRepo.countStatus(bid, OrderStatus.NEW.getStatus());
        return count - waitingCount;
    }

}
