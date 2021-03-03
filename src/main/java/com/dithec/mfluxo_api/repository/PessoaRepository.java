package com.dithec.mfluxo_api.repository;


import com.dithec.mfluxo_api.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
