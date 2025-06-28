package com.newsletter.service;

import com.newsletter.model.User;
import com.newsletter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

import java.util.Calendar;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void createUser(String email, String nome) {
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setNome(nome);
        newUser.setValidade(calculateOneMonthValidity());

        userRepository.save(newUser);
    }

    private Date calculateOneMonthValidity() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }
}