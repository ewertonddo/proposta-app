package com.ewerton.proposta_app.service;

import com.ewerton.proposta_app.dto.PropostaRequestDto;
import com.ewerton.proposta_app.dto.PropostaResponseDto;
import com.ewerton.proposta_app.entity.Proposta;
import com.ewerton.proposta_app.mapper.PropostaMapper;
import com.ewerton.proposta_app.repository.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropostaService {

    public static final String PROPOSTA_PENDENTE_EX = "proposta-pendente.ex";

    @Autowired
    private PropostaRepository repository;
    
    @Autowired
    private NotificacaoService notificacaoService;

    public PropostaResponseDto criar(PropostaRequestDto request) {
        Proposta proposta = PropostaMapper.INSTANCE.convertDtoToProposta(request);
        repository.save(proposta);

        PropostaResponseDto response = PropostaMapper.INSTANCE.convertPropostaToDto(proposta);
        notificacaoService.notificar(response, PROPOSTA_PENDENTE_EX);
        
        return response;
    }

    public List<PropostaResponseDto> obterProposta() {
        return PropostaMapper.INSTANCE.convertListPropostaToDto(repository.findAll());
    }
}
