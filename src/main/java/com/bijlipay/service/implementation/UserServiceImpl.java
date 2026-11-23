package com.bijlipay.service.implementation;

import com.bijlipay.model.User;
import com.bijlipay.repository.UserRepository;
import com.bijlipay.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service(value="userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("UserName  {}",username);
        User user = findByUsername(username);
        logger.info("User  {}",user);
        if(user==null)
            throw new UsernameNotFoundException("User not exist");
        //read user(from db)
        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                user.getRole().stream()
                        .map(role->new SimpleGrantedAuthority(role))
                        .collect(Collectors.toList()));

    }

    public User findByUsername(String username) {
        return userRepository.findUsername(username);
    }
}
