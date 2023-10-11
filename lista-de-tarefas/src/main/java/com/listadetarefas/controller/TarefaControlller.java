package com.listadetarefas.controller;

import com.listadetarefas.dto.TarefaDto;
import com.listadetarefas.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarefa")
public class TarefaControlller {
    @Autowired
    private TarefaService tarefaService;

    @GetMapping
    public ResponseEntity<Page<TarefaDto>> findAll(@PageableDefault(size = 10, page = 0, sort = "ordemDeApresentação") Pageable pageable) {
        Page<TarefaDto> tarefas = this.tarefaService.findAll(pageable);
        return ResponseEntity.ok(tarefas);
    }

    @PostMapping
    public ResponseEntity<TarefaDto> save(@RequestBody @Valid TarefaDto tarefaDto) {
        TarefaDto saved = this.tarefaService.save(tarefaDto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefaDto> update(@PathVariable Long id, @RequestBody @Valid TarefaDto tarefaDto) {
        TarefaDto updated = this.tarefaService.update(id, tarefaDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping
    public void delete(Long id) {
        this.tarefaService.delete(id);
    }
}
