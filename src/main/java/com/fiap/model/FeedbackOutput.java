package com.fiap.model;

public record FeedbackOutput (String statusCode,
                              String nomeAluno,
                              String emailAluno,
                              Integer nota,
                              String descricaoFeedback,
                              String idAulaAvaliada,
                              String correlationId,
                              String messageId,
                              String errors) {
}
