package com.fiap.model;

import java.time.LocalDateTime;

public class FeedbackEvent {

    private String email;
    private String descricao;
    private Integer nota;
    private String correlationId;
    private String step;
    private final LocalDateTime timestamp;

    public FeedbackEvent(FeedbackRequest req, String correlationId, String sagaStep) {
        this.email = req.getEmail();
        this.descricao = req.getDescricao();
        this.nota = req.getNota();
        this.correlationId = correlationId;
        this.step = sagaStep;
        this.timestamp = LocalDateTime.now();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
