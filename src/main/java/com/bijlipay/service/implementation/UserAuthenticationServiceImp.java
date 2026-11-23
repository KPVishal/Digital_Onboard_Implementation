package com.bijlipay.service.implementation;

import com.bijlipay.model.UserAuthentication;
import com.bijlipay.repository.UserAuthenticationRepo;
import com.bijlipay.service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationServiceImp  implements UserAuthenticationService {

    @Autowired
    private UserAuthenticationRepo userAuthenticateRepo;

    @Override
    public UserAuthentication getToken(Long id) {
        return userAuthenticateRepo.getToken(id);
    }


}
