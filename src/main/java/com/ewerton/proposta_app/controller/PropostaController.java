package com.ewerton.proposta_app.controller;

import com.ewerton.proposta_app.dto.PropostaRequestDto;
import com.ewerton.proposta_app.dto.PropostaResponseDto;
import com.ewerton.proposta_app.service.PropostaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/proposta")
public class PropostaController {

    @Autowired
    private PropostaService service;

    @PostMapping
    public ResponseEntity<PropostaResponseDto> criar(@RequestBody PropostaRequestDto requestDto){
        PropostaResponseDto response =  service.criar(requestDto);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.getId())
                .toUri()).body(response);
    }

}
