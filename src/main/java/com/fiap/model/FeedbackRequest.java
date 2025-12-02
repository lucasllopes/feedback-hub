package com.fiap.model;

import jakarta.validation.constraints.*;

public class FeedbackRequest {

    @NotBlank(message = "idAula não pode ser vazio")
    private String idAula;

    @NotBlank(message = "nomeAluno do aluno não pode ser vazio")
    private String nomeAluno;

    @NotBlank(message = "email nao pode ser vazio")
    @Email(message = "email deve ser um e-mail válido")
    private String email;

    @NotBlank(message = "descricao é obrigatório")
    private String descricao;

    @NotNull(message = "nota é obrigatório")
    @Min(value = 0, message = "nota deve ser no mínimo 0")
    @Max(value = 10, message = "nota deve ser no máximo 10")
    private Integer nota;

    public FeedbackRequest() {}

    public FeedbackRequest(String idAula, String nomeAluno, String email, String descricao, Integer nota) {
        this.idAula = idAula;
        this.nomeAluno = nomeAluno;
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

    public String getIdAula() {
        return idAula;
    }

    public void setIdAula(String idAula) {
        this.idAula = idAula;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }
}
