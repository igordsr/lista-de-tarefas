package com.listadetarefas.repository;

import com.listadetarefas.entities.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefasRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findByNomeDaTarefa(String lastname);
}
