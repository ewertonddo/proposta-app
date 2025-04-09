package com.ewerton.proposta_app.service;

import com.ewerton.proposta_app.dto.PropostaRequestDto;
import com.ewerton.proposta_app.dto.PropostaResponseDto;
import com.ewerton.proposta_app.entity.Proposta;
import com.ewerton.proposta_app.entity.Usuario;
import com.ewerton.proposta_app.mapper.PropostaMapper;
import com.ewerton.proposta_app.repository.PropostaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PropostaServiceTest {

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
    private static final boolean INTEGRADA = true;
    private Proposta proposta;
    private PropostaResponseDto propostaResponseDto;
    private PropostaRequestDto propostaRequestDto;

    @InjectMocks
    private PropostaService service;

    @Mock
    private PropostaRepository repository;

    @Mock
    private NotificacaoService notificacaoService;

    @Mock
    private PropostaMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startProposta();
    }


    @Test
    void WhenCriarThenReturnSuccess() {
        Mockito.when(mapper.convertDtoToProposta(Mockito.any()))
                .thenReturn(proposta);

        Mockito.when(repository.save(Mockito.any())).thenReturn(proposta);

        Mockito.when(mapper.convertPropostaToDto(Mockito.any()))
                .thenReturn(propostaResponseDto);

        PropostaResponseDto response = service.criar(propostaRequestDto);

        assertNotNull(response);
        assertEquals(PropostaResponseDto.class, response.getClass());
    }

    @Test
    void whenObterPropostaThenReturnSuccess() {
        Mockito.when(repository.findAll()).thenReturn(List.of(proposta));
        List<PropostaResponseDto> response = service.obterProposta();

        assertNotNull(response);
        assertNotNull(response.get(0));
        assertEquals(PropostaResponseDto.class, response.get(0).getClass());
    }

    void startProposta() {
        proposta = new Proposta(ID,VALOR_SOLICITADO, PRAZO_PAGAMENTO, APROVADO,
                INTEGRADA, OBS, new Usuario(ID, NOME, SOBRENOME, CPF, TELEFONE
                ,RENDA, proposta));

        propostaResponseDto  = new PropostaResponseDto(ID, NOME
                , SOBRENOME, TELEFONE, CPF
                , RENDA, VALOR_SOLICITADO_FMT, PRAZO_PAGAMENTO
                , APROVADO, OBS);

        propostaRequestDto = new PropostaRequestDto(NOME, SOBRENOME, TELEFONE, CPF, RENDA
                , VALOR_SOLICITADO, PRAZO_PAGAMENTO);
    }
}
