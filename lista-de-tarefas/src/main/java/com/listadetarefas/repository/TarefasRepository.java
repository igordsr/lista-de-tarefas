package com.listadetarefas.repository;

import com.listadetarefas.entities.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefasRepository extends JpaRepository<Tarefa, Long> {
}
