package com.listadetarefas.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrdemTarefaDto(
        @NotNull(message = "Id Da Tarefa não pode estar em null.")
        Long id,
        @NotNull(message = "Ordem Da Tarefa não pode estar em Branco.")
        @Positive(message = "Ordem da Tarefa não pode ser um valor Negativo")
        Long ordemDeApresentação

) {

}
