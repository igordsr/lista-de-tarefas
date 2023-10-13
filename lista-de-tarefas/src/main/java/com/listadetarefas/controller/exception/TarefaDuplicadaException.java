package com.listadetarefas.controller.exception;

public class TarefaDuplicadaException extends RuntimeException {
    public TarefaDuplicadaException() {
        super("Descrição da Tarefa não pode ser Duplicado!");
    }
}
