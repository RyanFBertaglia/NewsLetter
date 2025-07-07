package com.newsletter.validation;

import com.newsletter.infra.TestConfigurationSender;
import com.newsletter.infra.RabbitMQConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = TestConfigurationSender.class)
public class TestMQConfig {

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private Queue delayQueue;
    @Autowired
    private Queue finalQueue;
    @Autowired
    private DirectExchange dlxExchange;

    @BeforeEach
    void setup() {
        try {
            rabbitAdmin.purgeQueue(RabbitMQConfig.DELAY_QUEUE_NAME);
            rabbitAdmin.purgeQueue(RabbitMQConfig.FINAL_QUEUE_NAME);
            System.out.println("Queues purged before test.");
        } catch (Exception e) {
            System.out.println("Could not purge queues: " + e.getMessage());
        }
    }

    @Test
    void testDelayQueueConfiguration() {
        assertThat(delayQueue).isNotNull();
        assertThat(delayQueue.getName()).isEqualTo(RabbitMQConfig.DELAY_QUEUE_NAME);

        if (delayQueue.getArguments() != null) {
            assertThat(delayQueue.getArguments().get("x-dead-letter-exchange"))
                    .isEqualTo(RabbitMQConfig.DLX_EXCHANGE_NAME);
            assertThat(delayQueue.getArguments().get("x-dead-letter-routing-key"))
                    .isEqualTo(RabbitMQConfig.DLX_ROUTING_KEY);
        }

        System.out.println("Delay Queue configuration verified.");
    }

    @Test
    void testFinalQueueConfiguration() {
        assertThat(finalQueue).isNotNull();
        assertThat(finalQueue.getName()).isEqualTo(RabbitMQConfig.FINAL_QUEUE_NAME);
        System.out.println("Final Queue configuration verified.");
    }

    @Test
    void testDlxExchangeConfiguration() {
        assertThat(dlxExchange).isNotNull();
        assertThat(dlxExchange.getName()).isEqualTo(RabbitMQConfig.DLX_EXCHANGE_NAME);
        assertThat(dlxExchange.getType()).isEqualTo("direct");
        System.out.println("DLX Exchange configuration verified.");
    }

    @Test
    void testRabbitAdminConfiguration() {
        assertThat(rabbitAdmin).isNotNull();
        System.out.println("RabbitAdmin configuration verified.");
    }
}