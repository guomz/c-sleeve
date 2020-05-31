package com.guomz.csleeve.service;

import com.guomz.csleeve.model.User;
import com.guomz.csleeve.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id){
        User user = userRepository.findUserById(id);
        return user;
    }
}
