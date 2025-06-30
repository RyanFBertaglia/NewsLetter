package com.newsletter.service;

import com.newsletter.exception.UserAlreadyExists;
import com.newsletter.exception.UserNotFound;
import com.newsletter.model.Role;
import com.newsletter.model.User;
import com.newsletter.model.UserDTO;
import com.newsletter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO getUserDatails(String email) {
        try {
            User user = userRepository.getUserByEmail(email);
            return new UserDTO(
                    user.getNome(),
                    user.getEmail(),
                    user.getRole(),
                    user.getValidade()
            );
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFound("Usuário não encontrado: " + email);
        }
    }

    @Transactional
    public void createUser(String email, String nome) {
        try {
            if (userRepository.findByEmail(email).isPresent()) {
                throw new UserAlreadyExists("Usuário com e-mail " + email + " já existe.");
            }

            User user = new User();
            user.setEmail(email);
            user.setNome(nome);
            user.setCompartilhados(0);
            user.setValidade(LocalDate.now());
            user.setRole(Role.USER);

            userRepository.save(user);

        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao salvar usuário: " + e.getMessage(), e);
        }
    }
}