package com.ewerton.proposta_app.agendador;

import com.ewerton.proposta_app.repository.PropostaRepository;
import com.ewerton.proposta_app.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropostaSemIntegracao {

    private PropostaRepository repository;

    public PropostaSemIntegracao(PropostaRepository repository,
                                 NotificacaoService notificacaoService,
                                 @Value("${rabbitmq.propostapendente.exchange}") String exchangePropostaPendente) {
        this.repository = repository;
        this.notificacaoService = notificacaoService;
        this.exchangePropostaPendente = exchangePropostaPendente;
    }

    private NotificacaoService notificacaoService;
    private String exchangePropostaPendente;

    public void buscarPropostasSemIntegracao(){
        repository.findAllByIntegradaIsFalse().forEach(proposta -> {
            try {
                notificacaoService.notificar(proposta, exchangePropostaPendente);
                proposta.setIntegrada(true);
            }catch(RuntimeException ex){
                System.out.println(ex);
            }
        });
    }
}
