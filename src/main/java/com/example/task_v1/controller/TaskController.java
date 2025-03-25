/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.task_v1.controller;
import com.example.task_v1.entity.Task;
import com.example.task_v1.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;


    @GetMapping
    public Flux<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Mono<Task> getTaskById(@PathVariable String id) {
        return taskService.getTaskById(id);
    }

    @PostMapping
    public Mono<Task> createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    public Mono<Task> updateTask(@PathVariable String id, @RequestBody Task task) {
        task.setId(id);
        return taskService.updateTask(task);
    }

    @PatchMapping("/{taskId}/observers/{observerId}")
    public Mono<Void> addObserverToTask(@PathVariable String taskId, @PathVariable String observerId) {
        return taskService.addObserverToTask(taskId, observerId);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteTaskById(@PathVariable String id) {
        return taskService.deleteTaskById(id);
    }
}
