package org.anthony.bs.repository;

import org.anthony.bs.domain.BsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<BsUser, Long> {

    @Query(value = "select * from bs_user where name=?1 and is_active=1", nativeQuery = true)
    public BsUser findByName(String name);

}
