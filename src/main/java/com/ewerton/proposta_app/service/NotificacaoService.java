package com.ewerton.proposta_app.service;

import com.ewerton.proposta_app.dto.PropostaResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NotificacaoService {

    private RabbitTemplate rabbitTemplate;

    public void notificar(PropostaResponseDto response, String exchange){
        rabbitTemplate.convertAndSend(exchange,"", response);
    }
}
