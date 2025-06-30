package com.newsletter.model;

import java.time.LocalDate;

public record UserDTO(String nome, String email, Role role, LocalDate validade) {}
