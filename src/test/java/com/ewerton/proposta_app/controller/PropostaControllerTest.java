package com.ewerton.proposta_app.controller;

import com.ewerton.proposta_app.dto.PropostaRequestDto;
import com.ewerton.proposta_app.dto.PropostaResponseDto;
import com.ewerton.proposta_app.service.PropostaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PropostaControllerTest {

    private static final String NOME = "Jose";
    private static final String SOBRENOME = "Silva";
    private static final String TELEFONE = "11999999999";
    private static final String CPF = "12345678909";
    private static final double RENDA = 1000.0;
    private static final String VALOR_SOLICITADO_FMT = "500";
    private static final int PRAZO_PAGAMENTO = 12;
    private static final boolean APROVADO = true;
    private static final String OBS = "obs";
    private static final double VALOR_SOLICITADO = 200.0;
    private static final Long ID = Long.valueOf(0);

    private PropostaResponseDto propostaResponseDto;
    private PropostaRequestDto propostaRequestDto;

    @InjectMocks
    private PropostaController controller;

    @Mock
    private PropostaService service;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        start();
    }

    @Test
    void whenCriarReturnSuccess() {
        Mockito.when(service.criar(Mockito.any())).thenReturn(propostaResponseDto);

        ResponseEntity<PropostaResponseDto> response = controller.criar(propostaRequestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(response.getBody().getId(), ID);
    }

    @Test
    void whenObterPropostaReturnSuccess() {
        Mockito.when(service.obterProposta()).thenReturn(List.of(propostaResponseDto));

        ResponseEntity<List<PropostaResponseDto>> response = controller.obterProposta();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(PropostaResponseDto.class, response.getBody().get(0).getClass());
    }

    void start(){
        propostaResponseDto  = new PropostaResponseDto(ID, NOME
                , SOBRENOME, TELEFONE, CPF
                , RENDA, VALOR_SOLICITADO_FMT, PRAZO_PAGAMENTO
                , APROVADO, OBS);

        propostaRequestDto = new PropostaRequestDto(NOME, SOBRENOME, TELEFONE, CPF, RENDA
                , VALOR_SOLICITADO, PRAZO_PAGAMENTO);
    }
}

