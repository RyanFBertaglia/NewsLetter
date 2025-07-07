package com.newsletter.service;

import com.newsletter.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<String> getUser() {
        return userRepository.getValidEmails();
    }
}
