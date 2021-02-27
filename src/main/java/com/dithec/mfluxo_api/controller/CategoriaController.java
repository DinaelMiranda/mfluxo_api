package com.dithec.mfluxo_api.controller;

import com.dithec.mfluxo_api.model.Categoria;
import com.dithec.mfluxo_api.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public List<Categoria> listasCategoria() {
        return categoriaRepository.findAll();

    }
}
