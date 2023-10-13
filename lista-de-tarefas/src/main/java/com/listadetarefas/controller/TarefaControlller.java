package com.listadetarefas.controller;

import com.listadetarefas.dto.OrdemTarefaDto;
import com.listadetarefas.dto.TarefaDto;
import com.listadetarefas.service.TarefaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefa")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TarefaControlller {
    @Autowired
    private TarefaService tarefaService;

    @GetMapping
    public ResponseEntity<Page<TarefaDto>> findAll(@PageableDefault(size = 10000, page = 0, sort = "ordemDeApresentação") Pageable pageable) {
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

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.tarefaService.delete(id);
    }

    @PatchMapping
    public ResponseEntity<List<TarefaDto>> reorderSequence(@RequestBody @NotNull(message = "Lista não pode ser null") @NotEmpty(message = "Lista não pode ser vazia") List<@Valid OrdemTarefaDto> tarefaDto) {
        List<TarefaDto> tarefaDtos = this.tarefaService.reorderSequence(tarefaDto);
        return ResponseEntity.ok(tarefaDtos);
    }
}
