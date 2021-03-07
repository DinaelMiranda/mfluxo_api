package com.dithec.mfluxo_api.controller;

import com.dithec.mfluxo_api.event.RecursoCriadoEvent;
import com.dithec.mfluxo_api.exceptions.customExceptionMessages.DataBaseException;
import com.dithec.mfluxo_api.exceptions.customExceptionMessages.ResourceNotFoundException;
import com.dithec.mfluxo_api.model.Pessoa;
import com.dithec.mfluxo_api.repository.PessoaRepository;
import com.dithec.mfluxo_api.services.PessoaService;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final PessoaRepository pessoaRepository;
    private final PessoaService pessoaService;

    public PessoaController(ApplicationEventPublisher applicationEventPublisher, PessoaRepository pessoaRepository, PessoaService pessoaService) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.pessoaRepository = pessoaRepository;
        this.pessoaService = pessoaService;
    }

    @GetMapping("/{id}")
    public Pessoa buscaPessoaPorId(@PathVariable Long id, HttpServletResponse response) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);

        return pessoa.orElseThrow(() -> new ResourceNotFoundException(Pessoa.class, id));
    }

    @GetMapping
    public List<Pessoa> listaPessoa() {
        return pessoaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Pessoa> criaPessoa(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
        Pessoa novaPessoa = pessoaRepository.save(pessoa);
        applicationEventPublisher.publishEvent(new RecursoCriadoEvent(this, response, novaPessoa.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPessoa);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        try {
            pessoaRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFoundException(Pessoa.class, id);
        } catch (DataBaseException ex) {
            ex.getMessage();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> update(@Valid @RequestBody Pessoa obj, @PathVariable Long id) {

        Pessoa novaPessoa = pessoaService.updatePessoa(obj, id);
        return novaPessoa != null ? ResponseEntity.ok(novaPessoa) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @PutMapping("/{id}/status")
    public void updateStatus(@PathVariable Long id,@RequestBody Boolean status) {
         pessoaService.atualizaStatus(id, status);
    }
}
