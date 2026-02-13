package com.micro.client.messaging;


import com.micro.client.config.RabbitMQConfig;
import com.micro.client.event.ClientEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientMessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendClientCreatedEvemt(ClientEvent clientEvent) {
        log.info("Sending ClientCreatedEvemt {}", clientEvent);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.CLIENTE_CREATED_ROUTING_KEY,
                clientEvent
        );

        log.debug("Sent ClientCreatedEvemt {}", clientEvent);
    }

    public void sendClientUpdatedEvemt(ClientEvent clientEvent) {
        log.info("Sending ClientUpdatedEvemt {}", clientEvent);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.CLIENTE_CREATED_ROUTING_KEY,
                clientEvent
        );
        log.debug("Sent ClientUpdatedEvemt {}", clientEvent);
    }
    public void sendClientDeletedEvemt(ClientEvent clientEvent) {
        log.info("Sending ClientDeletedEvemt {}", clientEvent);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.CLIENTE_CREATED_ROUTING_KEY,
                clientEvent
        );
        log.debug("Sent ClientDeletedEvemt {}", clientEvent);
    }

}
