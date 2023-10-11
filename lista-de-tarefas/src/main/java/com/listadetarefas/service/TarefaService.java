package com.listadetarefas.service;

import com.listadetarefas.controller.exception.ControllerNotFoundException;
import com.listadetarefas.dto.TarefaDto;
import com.listadetarefas.entities.Tarefa;
import com.listadetarefas.repository.TarefasRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TarefaService {
    @Autowired
    private TarefasRepository tarefasRepository;

    public Page<TarefaDto> findAll(Pageable pageable) {
        Page<Tarefa> terefas = this.tarefasRepository.findAll(pageable);
        return terefas.map(Tarefa::toDto);
    }

    public TarefaDto save(TarefaDto tarefaDto) {
        Tarefa tarefa = tarefaDto.toTarefa();
        tarefa = this.tarefasRepository.save(tarefa);
        return tarefa.toDto();
    }

    public TarefaDto update(Long id, TarefaDto tarefaDto) {
        try {
            Tarefa tarefa = this.tarefasRepository.getReferenceById(id);
            tarefa.setNomeDaTarefa(tarefaDto.nomeDaTarefa());
            tarefa.setCusto(tarefaDto.custo());
            tarefa.setDataLimite(tarefaDto.dataLimite());
            tarefa = this.tarefasRepository.saveAndFlush(tarefa);
            tarefaDto = tarefa.toDto();
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Tarefa não Encontrada");
        }
        return tarefaDto;
    }

    public void delete(Long id) {
        this.tarefasRepository.deleteById(id);
    }

}
