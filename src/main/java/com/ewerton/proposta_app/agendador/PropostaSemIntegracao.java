package com.ewerton.proposta_app.agendador;

import com.ewerton.proposta_app.entity.Proposta;
import com.ewerton.proposta_app.repository.PropostaRepository;
import com.ewerton.proposta_app.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class PropostaSemIntegracao {

    private final PropostaRepository repository;
    private final NotificacaoService notificacaoService;
    private final String exchangePropostaPendente;

    public PropostaSemIntegracao(PropostaRepository repository,
                                 NotificacaoService notificacaoService,
                                 @Value("${rabbitmq.propostapendente.exchange}") String exchangePropostaPendente) {
        this.repository = repository;
        this.notificacaoService = notificacaoService;
        this.exchangePropostaPendente = exchangePropostaPendente;
    }

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void buscarPropostasSemIntegracao(){
        repository.findAllByIntegradaIsFalse().forEach(proposta -> {
            try {
                notificacaoService.notificar(proposta, exchangePropostaPendente);
                atualizarProposta(proposta);
            }catch(RuntimeException ex){
                System.out.println(ex);
            }
        });
    }

    private void atualizarProposta(Proposta proposta) {
        proposta.setIntegrada(true);
        repository.save(proposta);
    }
}
