package org.anthony.bs.repository;

import org.anthony.bs.domain.BsBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepo extends JpaRepository<BsBook, Long> {

    @Query(value = "select * from BS_BOOK where name=?1 and is_active=1", nativeQuery = true)
    public BsBook findByName(String name);

    @Query(value = "select * from BS_BOOK where name like %?1%  and is_active=1", nativeQuery = true)
    public List<BsBook> searchByName(String name);

}
