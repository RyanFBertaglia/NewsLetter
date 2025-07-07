package com.newsletter.infra;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer; // Importe AbstractHttpConfigurer


@TestConfiguration // Esta anotação indica que esta configuração é específica para testes
@EnableAutoConfiguration // Mantenha se você precisa de auto-configurações específicas para o teste aqui
@Import(RabbitMQConfig.class) // Mantenha se RabbitMQConfig for uma configuração real do RabbitMQ
public class TestConfigurationSender {

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    /**
     * Configuração de segurança para testes que permite todas as requisições.
     * Isso desabilita a segurança padrão do Spring Security para que os testes
     * de integração possam acessar os endpoints sem autenticação.
     *
     * @param http O objeto HttpSecurity para configurar as regras de segurança.
     * @return Uma SecurityFilterChain configurada para permitir todas as requisições.
     * @throws Exception Se ocorrer um erro durante a configuração da segurança.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Desabilita a proteção CSRF para testes
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Permite todas as requisições sem autenticação
                );
        return http.build();
    }
}
