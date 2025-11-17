package com.fiap.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

@ApplicationScoped
public class QueueService {

    private final SqsClient sqsClient;

    @ConfigProperty(name = "sqs.queue.url")
    String queueUrl;

    public QueueService() {
        this.sqsClient = SqsClient.builder()
                .httpClient(UrlConnectionHttpClient.builder().build())
                .region(Region.SA_EAST_1)
                .build();
    }

    public SendMessageResponse sendMessage(String messageBody) {
        SendMessageResponse response = sqsClient.sendMessage(m -> m.queueUrl(queueUrl).messageBody(messageBody));
        return response;
    }
}
