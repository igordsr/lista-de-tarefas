package com.listadetarefas.entities;

import com.listadetarefas.dto.TarefaDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Tarefas", uniqueConstraints = {@UniqueConstraint(name = "UNIQUE_CONSTRAINT_NOME_DA_TAREFA", columnNames = {"nomeDaTarefa"})})
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nomeDaTarefa;

    @Column(nullable = false)
    private Double custo;

    @Temporal(TemporalType.DATE)
    private LocalDate dataLimite;

    @Column(nullable = false)
    private Long ordemDeApresentação;

    public Tarefa(String nome, Double custo, LocalDate dataLimite) {
        this.nomeDaTarefa = nome;
        this.custo = custo;
        this.dataLimite = dataLimite;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "id=" + id +
                ", nome='" + nomeDaTarefa + '\'' +
                ", custo=" + custo +
                ", dataLimite=" + dataLimite +
                ", ordemDeApresentação=" + ordemDeApresentação +
                '}';
    }

    public TarefaDto toDto() {
        return new TarefaDto(this.id, this.nomeDaTarefa, this.custo, this.dataLimite, this.ordemDeApresentação);
    }
}
