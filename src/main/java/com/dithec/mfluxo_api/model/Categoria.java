package com.dithec.mfluxo_api.model;

import lombok.*;

import javax.persistence.*;



@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categoria")
@Data
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

}
