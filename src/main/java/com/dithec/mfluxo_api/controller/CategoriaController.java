package com.dithec.mfluxo_api.controller;

import com.dithec.mfluxo_api.event.RecursoCriadoEvent;
import com.dithec.mfluxo_api.exceptions.customExceptionMessages.DataBaseException;
import com.dithec.mfluxo_api.model.Categoria;
import com.dithec.mfluxo_api.model.Pessoa;
import com.dithec.mfluxo_api.repository.CategoriaRepository;

import com.dithec.mfluxo_api.exceptions.customExceptionMessages.ResourceNotFoundException;
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
@RequestMapping("/categorias")
public class CategoriaController {

    //Injeção de dependência atraves de construtor, sem utilizar @AutoWired
    private final ApplicationEventPublisher applicationEventPublisher;
    private final CategoriaRepository categoriaRepository;

    public CategoriaController(CategoriaRepository categoriaRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.categoriaRepository = categoriaRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

//    return categoria.isPresent() ? ResponseEntity.ok().body(categoria.get()) : ResponseEntity.notFound().build();
//    return ResponseEntity.ok().body(categoria.get());
    @GetMapping("/{id}")
    public Categoria buscaCategoriaPorId(@PathVariable Long id, HttpServletResponse response) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);

        return categoria.orElseThrow(() -> new ResourceNotFoundException(Categoria.class, id));
    }

    @GetMapping
    public List<Categoria> listasCategoria() {
        return categoriaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Categoria> criaCategoria(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
        Categoria novaCategoria = categoriaRepository.save(categoria);
        applicationEventPublisher.publishEvent(new RecursoCriadoEvent(this, response, novaCategoria.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        try {
            categoriaRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFoundException(Pessoa.class, id);
        } catch (DataBaseException ex) {
            ex.getMessage();
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@RequestBody Categoria obj,@Valid @PathVariable Long id) {
        if (obj == null) {
            throw new EmptyResultDataAccessException(1);
        }
        obj.setId(id);
        categoriaRepository.save(obj);
        return ResponseEntity.noContent().build();
    }


}
