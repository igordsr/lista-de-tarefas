package com.listadetarefas.service;

import com.listadetarefas.controller.exception.ControllerNotFoundException;
import com.listadetarefas.controller.exception.TarefaDuplicadaException;
import com.listadetarefas.dto.OrdemTarefaDto;
import com.listadetarefas.dto.TarefaDto;
import com.listadetarefas.entities.Tarefa;
import com.listadetarefas.repository.TarefasRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        if(!this.tarefasRepository.findByNomeDaTarefa(tarefaDto.nomeDaTarefa()).isEmpty()){
            throw new TarefaDuplicadaException();
        }
        long nextSequence = this.tarefasRepository.count()+1;
        tarefa.setOrdemDeApresentação(nextSequence);
        tarefa = this.tarefasRepository.save(tarefa);
        return tarefa.toDto();
    }

    public TarefaDto update(Long id, TarefaDto tarefaDto) {
        List<Tarefa> tarefas = this.tarefasRepository.findByNomeDaTarefa(tarefaDto.nomeDaTarefa());
        tarefas = tarefas.stream().filter(t1 -> !t1.getId().equals(id)).toList();
        if(!tarefas.isEmpty()){
            throw new TarefaDuplicadaException();
        }
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

    public List<TarefaDto> reorderSequence(List<OrdemTarefaDto> tarefaDtos){
        try {
            List<Long> ids = tarefaDtos.stream().map(OrdemTarefaDto::id).toList();
            List<Tarefa> tarefas = this.tarefasRepository.findAllById(ids);
            tarefas.forEach(tarefa -> {
                OrdemTarefaDto ordemTarefaDto = tarefaDtos.stream().filter(dto -> tarefa.getId().equals(dto.id())).findFirst().get();
                tarefa.setOrdemDeApresentação(ordemTarefaDto.ordemDeApresentação());
            });
            tarefas= this.tarefasRepository.saveAllAndFlush(tarefas);
            return tarefas.stream().map(Tarefa::toDto).toList();
        }
        catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Tarefa não Encontrada");
        }
    }

}
