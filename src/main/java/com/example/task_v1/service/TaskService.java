/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.task_v1.service;

import com.example.task_v1.entity.Task;
import com.example.task_v1.entity.User;
import com.example.task_v1.repo.TaskRepository;
import com.example.task_v1.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;


    public Flux<Task> getAllTasks() {
        return taskRepository.findAll()
                .flatMap(this::enrichTaskWithUsers);
    }

    public Mono<Task> getTaskById(String id) {
        return taskRepository.findById(id)
                .flatMap(this::enrichTaskWithUsers);
    }

    public Mono<Task> createTask(Task task) {
        return enrichTaskWithUsers(task)
                .flatMap(taskRepository::save);
    }

    public Mono<Task> updateTask(Task task) {
        return enrichTaskWithUsers(task)
                .flatMap(taskRepository::save);
    }

    public Mono<Void> addObserverToTask(String taskId, String observerId) {
        return taskRepository.findById(taskId)
                .doOnNext(task -> task.getObserverIds().add(observerId))
                .flatMap(taskRepository::save)
                .then();
    }

    public Mono<Void> deleteTaskById(String id) {
        return taskRepository.deleteById(id);
    }

    private Mono<Task> enrichTaskWithUsers(Task task) {
        return Mono.just(task)
                .zipWith(findUserById(task.getAuthorId())) // Task + User
                .zipWith(findUserById(task.getAssigneeId()))
                .zipWith(findUsersByIds(task.getObserverIds()))
                .flatMap(tuple -> {
                    task.setAuthor(tuple.getT1().getT1().getT2());
                    task.setAssignee(tuple.getT1().getT2());
                    task.setObservers(tuple.getT2());
                    return Mono.just(task);
                });
    }

    private Mono<User> findUserById(String userId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.empty()); // пустой
    }

    private Mono<Set<User>> findUsersByIds(Set<String> userIds) {
        return Flux.fromIterable(userIds)
                .flatMap(userRepository::findById)
                .collect(Collectors.toSet());
    }
    
}
