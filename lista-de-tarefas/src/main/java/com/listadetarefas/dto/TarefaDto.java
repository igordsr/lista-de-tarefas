package com.listadetarefas.dto;

import com.listadetarefas.entities.Tarefa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public record TarefaDto(
        Long id,
        @NotBlank(message = "Nome Da Tarefa não pode estar em Branco.")
        String nomeDaTarefa,
        @NotNull(message = "Custo Da Tarefa não pode estar em Branco.")
        @PositiveOrZero(message = "Custo da Tarefa não pode ser um valor Negativo")
        Double custo,
        @NotNull(message = "Data Limite da Terega não pode ser null.")
        LocalDate dataLimite,
        Long ordemDeApresentação
) {
    public Tarefa toTarefa() {
        return new Tarefa(this.nomeDaTarefa, this.custo, this.dataLimite);
    }
}
