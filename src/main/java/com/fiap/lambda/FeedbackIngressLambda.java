package com.fiap.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fiap.model.FeedbackEvent;
import com.fiap.model.FeedbackRequest;

import java.util.UUID;

public class FeedbackIngressLambda implements RequestHandler<FeedbackRequest, String> {

    @Override
    public String handleRequest(FeedbackRequest input, Context context) {

        LambdaLogger log = context.getLogger();

        if (input == null) {
            log.log("WARN: payload nulo recebido\n");
            return "payload-invalido";
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

        String resposta = String.format(
                "Feedback recebido::::::: email=%s | nota=%d | descricao=\"%s\"",
                input.getEmail(), input.getNota(), input.getDescricao()
        );

        return resposta;
    }
}
