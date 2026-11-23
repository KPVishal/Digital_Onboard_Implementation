package com.bijlipay.service;

import com.bijlipay.model.UserAuthentication;
import org.springframework.stereotype.Service;

@Service
public interface UserAuthenticationService {

    UserAuthentication getToken(Long id);


}
