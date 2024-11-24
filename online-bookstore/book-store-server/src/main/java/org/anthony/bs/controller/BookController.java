package org.anthony.bs.controller;

import org.anthony.bs.BookService;
import org.anthony.bs.domain.BsBook;
import org.anthony.bs.exception.BsException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Anthony
 */
@RestController
@RequestMapping(value = "/book")
public class BookController {

    @Resource
    private BookService bookService;


    @GetMapping(value = "/find")
    public BsBook find(@RequestParam Long bid) throws BsException {
        return bookService.find(bid);
    }


    @GetMapping(value = "/search")
    public List<BsBook> search(@RequestParam String keyword) throws BsException {
        return bookService.search(keyword);
    }


    @GetMapping(value = "/count")
    public int count(@RequestParam Long bid) throws BsException {
        return bookService.count(bid);
    }

    @PostMapping(value = "/submitCount")
    public int submitCount(@RequestParam Long bid, @RequestParam int count) throws BsException {
        return bookService.submitCount(bid, count);
    }

    @GetMapping(value = "/canOrderCount")
    public int canOrderCount(@RequestParam Long bid) throws BsException {
        return bookService.canOrderCount(bid);
    }

    @PostMapping(value = "/save")
    public Long save(@RequestBody BsBook bsBook) throws BsException {
        return bookService.save(bsBook);
    }

}
