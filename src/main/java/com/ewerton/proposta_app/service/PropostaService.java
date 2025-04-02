package com.ewerton.proposta_app.service;

import com.ewerton.proposta_app.dto.PropostaRequestDto;
import com.ewerton.proposta_app.dto.PropostaResponseDto;
import com.ewerton.proposta_app.entity.Proposta;
import com.ewerton.proposta_app.mapper.PropostaMapper;
import com.ewerton.proposta_app.repository.PropostaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropostaService {

    public PropostaService(@Value("${rabbitmq.propostapendente.exchange}") String exchangePropostaPendente,
                           PropostaRepository repository,
                           NotificacaoService notificacaoService) {
        this.exchangePropostaPendente = exchangePropostaPendente;
        this.repository = repository;
        this.notificacaoService = notificacaoService;
    }

    private String exchangePropostaPendente;
    private PropostaRepository repository;
    private NotificacaoService notificacaoService;

    public PropostaResponseDto criar(PropostaRequestDto request) {
        Proposta proposta = PropostaMapper.INSTANCE.convertDtoToProposta(request);
        repository.save(proposta);

        notificarRabbitMq(proposta);

        return PropostaMapper.INSTANCE.convertPropostaToDto(proposta);
    }

    private void notificarRabbitMq(Proposta proposta) {
        try {
            notificacaoService.notificar(proposta, exchangePropostaPendente);
        } catch (RuntimeException ex) {
            proposta.setIntegrada(false);
            repository.save(proposta);
        }
    }

    public List<PropostaResponseDto> obterProposta() {
        return PropostaMapper.INSTANCE.convertListPropostaToDto(repository.findAll());
    }
}
