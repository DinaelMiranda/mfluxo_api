package com.dithec.mfluxo_api.services;

import com.dithec.mfluxo_api.controller.PessoaController;
import com.dithec.mfluxo_api.model.Pessoa;
import com.dithec.mfluxo_api.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaService {

    public final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public Pessoa updatePessoa(Pessoa pessoa, Long id) {

        Optional<Pessoa> pessoaSalva = pessoaRepository.findById(id);
        if (pessoaSalva.isEmpty() || !pessoaSalva.isPresent()) {
            throw new EmptyResultDataAccessException(1);
        }
        BeanUtils.copyProperties(pessoa, pessoaSalva, "id");
        return pessoaRepository.save(pessoaSalva.get());
    }
}
