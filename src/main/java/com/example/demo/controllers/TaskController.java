package com.example.demo.controllers;

import com.example.demo.entities.Task;
import com.example.demo.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Obtener todas las tareas
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    // Obtener tarea por ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskService.getTaskById(id);
        return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva tarea
    @PostMapping
    public ResponseEntity<String> createTask(@Valid @RequestBody Task task, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            result.getAllErrors().forEach(error -> errorMessages.append(error.getDefaultMessage()).append("\n"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages.toString());
        }

        // Guardar la tarea en la base de datos
        taskService.createTask(task);

        return ResponseEntity.status(HttpStatus.CREATED).body("Tarea creada con éxito.");
    }

    // Actualizar una tarea existente
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable Long id, @Valid @RequestBody Task updatedTask, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            result.getAllErrors().forEach(error -> errorMessages.append(error.getDefaultMessage()).append("\n"));
            // Retornamos los mensajes de error como String
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages.toString());
        }

        // Intentar actualizar la tarea
        Optional<Task> task = taskService.updateTask(id, updatedTask);
        if (task.isPresent()) {
            return ResponseEntity.ok(task.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }





    // Eliminar una tarea por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
