package com.listadetarefas.controller.exception;

public class ControllerNotFoundException extends RuntimeException {
    public ControllerNotFoundException(String message) {
        super(message);
    }
}
