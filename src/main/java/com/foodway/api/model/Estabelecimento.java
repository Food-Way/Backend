package com.foodway.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity(name = "estabelecimento")
@NoArgsConstructor
@AllArgsConstructor
//@NotBlank
public class Estabelecimento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID idEstabelecimento;
    @Column(length = 75)
    String nome;
    @Column(length = 255)
    String descricao;
    @Column(length = 8)
    String cep;
    @Column(length = 45)
    String number;
    String logo;
    String rate;
    String cnpj;
    public UUID getIdEstabelecimento() {
        return idEstabelecimento;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void atualizar(Estabelecimento estabelecimento) {
        if(estabelecimento.getNome() != null) this.setNome(estabelecimento.getNome());
        if(estabelecimento.getDescricao() != null) this.setDescricao(estabelecimento.getDescricao());
        if(estabelecimento.getCep() != null) this.setCep(estabelecimento.getCep());
        if(estabelecimento.getNumber() != null) this.setNumber(estabelecimento.getNumber());
        if(estabelecimento.getLogo() != null) this.setLogo(estabelecimento.getLogo());
        if(estabelecimento.getRate() != null) this.setRate(estabelecimento.getRate());
        if(estabelecimento.getCnpj() != null) this.setCnpj(estabelecimento.getCnpj());
    }
}
