package com.fiap.model;

import jakarta.validation.constraints.NotBlank;
import org.eclipse.microprofile.graphql.NonNull;

public class FeedbackRequest {

    @NotBlank(message="Email não pode ser vazio")
    private String email;
    @NotBlank(message="Descrição não pode ser vazia")
    private String descricao;
    @NotBlank(message="Nota não pode ser vazia")
    private Integer nota;

    public FeedbackRequest() {}

    public FeedbackRequest(String email, String descricao, Integer nota) {
        this.email = email;
        this.descricao = descricao;
        this.nota = nota;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Integer getNota() { return nota; }
    public void setNota(Integer nota) { this.nota = nota; }
}
