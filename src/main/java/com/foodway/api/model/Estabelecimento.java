package com.foodway.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity(name = "estabelecimento")
@NoArgsConstructor
@AllArgsConstructor
@Data
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
}
