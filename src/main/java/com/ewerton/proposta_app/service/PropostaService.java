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

    @Autowired
    private PropostaRepository repository;

    public PropostaResponseDto criar(PropostaRequestDto request) {
        Proposta proposta = PropostaMapper.INSTANCE.convertDtoToProposta(request);
        repository.save(proposta);
        return PropostaMapper.INSTANCE.convertPropostaToDto(proposta);
    }

    public List<PropostaResponseDto> obterProposta() {
        return PropostaMapper.INSTANCE.convertListPropostaToDto(repository.findAll());
    }
}
