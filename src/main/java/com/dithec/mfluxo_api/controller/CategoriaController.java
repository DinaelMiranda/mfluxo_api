package com.dithec.mfluxo_api.controller;

import com.dithec.mfluxo_api.model.Categoria;
import com.dithec.mfluxo_api.repository.CategoriaRepository;
import com.dithec.mfluxo_api.services.exceptions.ResourceNotFoundException;
import org.aspectj.bridge.MessageUtil;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    //Injeção de dependência atraves de construtor, sem utilizar @AutoWired
    private final CategoriaRepository categoriaRepository;
    public CategoriaController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping("/{id}")
//    public ResponseEntity<Categoria> buscaCategoriaPorId(@PathVariable Long id, HttpServletResponse response) {
        public Categoria buscaCategoriaPorId(@PathVariable Long id, HttpServletResponse response) {
       Optional <Categoria> categoria = categoriaRepository.findById(id);
//        return categoria.isPresent() ? ResponseEntity.ok().body(categoria.get()) : ResponseEntity.notFound().build();
//        return ResponseEntity.ok().body(categoria.get());
        return categoria.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @GetMapping
    public List<Categoria> listasCategoria() {
        return categoriaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Categoria> criaCategoria(@RequestBody Categoria categoria, HttpServletResponse response) {
        Categoria novaCategoria = categoriaRepository.save(categoria);

        //Retorna o conteúdo que foi inserido
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(novaCategoria.getId()).toUri();
        response.setHeader("Locations", uri.toASCIIString());

        return ResponseEntity.created(uri).body(novaCategoria);
    }


}
