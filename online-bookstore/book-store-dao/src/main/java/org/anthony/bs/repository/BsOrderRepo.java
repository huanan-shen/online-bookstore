package org.anthony.bs.repository;


import org.anthony.bs.domain.BsOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BsOrderRepo extends JpaRepository<BsOrder, Long> {

    @Query(value = "select count(*) from BS_ORDER where book_id=?1 and status=?2", nativeQuery = true)
    public Integer countStatus(Long bid, Integer status);

}
