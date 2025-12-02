package com.fiap.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.exception.ErrorEvent;
import com.fiap.model.FeedbackEvent;
import com.fiap.model.FeedbackOutput;
import com.fiap.model.FeedbackRequest;
import com.fiap.service.QueueService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.UUID;
import java.util.stream.Collectors;

@Named("feedbackIngress")
@ApplicationScoped
public class FeedbackIngressLambda implements RequestHandler<FeedbackRequest, FeedbackOutput> {

    private static final Validator VALIDATOR =
            Validation.buildDefaultValidatorFactory().getValidator();

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

        var violations = VALIDATOR.validate(input);

        if (!violations.isEmpty()) {
            String msg = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("; "));
            return new FeedbackOutput("400", "", "", null, "",
                    "", "", "", msg);
        }

        String correlationId = UUID.randomUUID().toString();
        String sagaStep = "FEEDBACK_RECEBIDO";

        FeedbackEvent event = new FeedbackEvent(input, correlationId, sagaStep);

        log.log(String.format(
                "event = feedback_ingress:: \n" +
                        "correlationId = %s \n" +
                        "sagaStep = %s",
                event.getCorrelationId(),
                event.getStep()
        ));

        String bodyJson;

        try {
            bodyJson = objectMapper.writeValueAsString(event);
        } catch (Exception e) {
            throw new ErrorEvent("Erro serializando evento para JSON");
        }

        var response = queueService.sendMessage(bodyJson);

        log.log("Mensagem enviada para a fila SQS com ID: " + response.messageId() + "\n");

        return new FeedbackOutput("200",
                event.getNomeAluno(),
                event.getEmail(),
                event.getNota(),
                event.getDescricao(),
                event.getIdAula(),
                event.getCorrelationId(),
                response.messageId(),
                null);
    }
}
