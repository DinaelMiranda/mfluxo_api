package com.dithec.mfluxo_api.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;




@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categoria")
public class Categoria {
    @Id
   @Getter @Setter private Long id;

   @Getter @Setter private String nome;

}
