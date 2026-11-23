package com.bijlipay.repository;

import com.bijlipay.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("SELECT u FROM User u WHERE u.id=?1")
    User find(Long id);

    @Query(value="Select * from user u  where u.email = ?1 order by  u.user_id desc limit 1",nativeQuery = true)
    User findUsername(String username);

    @Query("SELECT u FROM User u WHERE u.email=?1")
    User findByEmail(String updatedBy);

    @Query(value="Select * from user u  where u.email = ?1 order by  u.user_id desc limit 1",nativeQuery = true)
    User findSessionUser(String username);
}
