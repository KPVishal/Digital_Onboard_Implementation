package com.bijlipay.repository;

import com.bijlipay.model.UserAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserAuthenticationRepo extends JpaRepository<UserAuthentication, Integer> {

    @Query(value = "Select * from user_authentication where user_id=?1",nativeQuery = true)
    UserAuthentication getToken(Long id);
}
