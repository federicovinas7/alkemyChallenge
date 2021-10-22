package com.alkemy.challenge.repository;

import com.alkemy.challenge.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(value = "SELECT * from users where username = ?1",nativeQuery = true)
    User getByUsername(String username);

    @Query(value = "SELECT * from users where username = ?1 and password = ?2",nativeQuery = true)
    User getByUserAndPass(String username, String password);
}
