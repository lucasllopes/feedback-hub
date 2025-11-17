package com.fiap.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.model.FeedbackEvent;
import com.fiap.model.FeedbackOutput;
import com.fiap.model.FeedbackRequest;
import com.fiap.service.QueueService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.UUID;

@Named("feedbackIngress")
@ApplicationScoped
public class FeedbackIngressLambda implements RequestHandler<FeedbackRequest, FeedbackOutput> {

    private final QueueService queueService;
    private final ObjectMapper objectMapper;

    @Inject
    public FeedbackIngressLambda(QueueService queueService, ObjectMapper objectMapper) {
        this.queueService = queueService;
        this.objectMapper = objectMapper;
    }

    @Override
    public FeedbackOutput handleRequest(FeedbackRequest input, Context context) {

        LambdaLogger log = context.getLogger();

        if (input == null) {
            log.log("WARN: payload nulo recebido\n");
            return new FeedbackOutput("", "Payload nulo recebido", "");
        }

        String correlationId = UUID.randomUUID().toString();
        String sagaStep = "FEEDBACK_RECEBIDO";

        FeedbackEvent event = new FeedbackEvent(input, correlationId, sagaStep);

        log.log(String.format(
                "event=feedback_ingress correlationId=%s sagaStep=%s email=%s nota=%s descricao=%s%n",
                event.getCorrelationId(),
                event.getStep(),
                event.getEmail(),
                event.getNota(),
                event.getDescricao()
        ));

        String bodyJson;

        try {
            bodyJson = objectMapper.writeValueAsString(event);
        } catch (Exception e) {
            throw new RuntimeException("Erro serializando evento para JSON", e);
        }

        var response = queueService.sendMessage(bodyJson);

        log.log("Mensagem enviada para a fila SQS com ID: " + response.messageId() + "\n");

        return new FeedbackOutput(
                correlationId,
                sagaStep + " - " + input.getDescricao() + " - " + input.getNota(),
                response.messageId()
        );
    }
}
