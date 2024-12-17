package com.example.demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El título es obligatorio.")
    @Size(min = 1, max = 100, message = "El título debe tener entre 1 y 100 caracteres.")
    @Column(nullable = false)
    private String title;

    @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres.")
    @Column(length = 500)
    private String description;

    @NotNull(message = "El estado es obligatorio.")
    @Pattern(regexp = "^(Pendiente|En Progreso|Completada)$", message = "El estado debe ser 'Pendiente', 'En Progreso' o 'Completada'.")
    @Column(nullable = false)
    private String status;

    private LocalDateTime createdAt;

    // Constructor vacío
    public Task() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
