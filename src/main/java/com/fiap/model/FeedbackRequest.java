package com.fiap.model;

public class FeedbackRequest {

    private String email;
    private String descricao;
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
