package com.ewerton.proposta_app.service;

import com.ewerton.proposta_app.entity.Proposta;
import com.ewerton.proposta_app.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotificacaoServiceTest {

    private static final String NOME = "Jose";
    private static final String SOBRENOME = "Silva";
    private static final String TELEFONE = "11999999999";
    private static final String CPF = "12345678909";
    private static final double RENDA = 1000.0;
    private static final int PRAZO_PAGAMENTO = 12;
    private static final boolean APROVADO = true;
    private static final String OBS = "obs";
    private static final double VALOR_SOLICITADO = 200.0;
    private static final Long ID = Long.valueOf(0);
    private static final boolean INTEGRADA = true;
    private Proposta proposta;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private NotificacaoService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startProposta();
    }

    @Test
    void whenNotificarThenReturnSuccess() {
        Mockito.doNothing().when(rabbitTemplate).convertAndSend(Mockito.any());
        service.notificar(proposta,"");
    }

    void startProposta() {
        proposta = new Proposta(ID, VALOR_SOLICITADO, PRAZO_PAGAMENTO, APROVADO,
                INTEGRADA, OBS, new Usuario(ID, NOME, SOBRENOME, CPF, TELEFONE
                , RENDA, proposta));
    }
}