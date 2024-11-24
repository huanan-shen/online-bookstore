package org.anthony.bs.repository;


import org.anthony.bs.domain.BsBookRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BsBookRepoRepo extends JpaRepository<BsBookRepo, Long> {

    @Query(value = "select book_count from BS_BOOK_REPO where id=?1", nativeQuery = true)
    public Integer count(Long bid);

}
