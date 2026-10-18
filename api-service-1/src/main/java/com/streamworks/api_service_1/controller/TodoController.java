package com.streamworks.api_service_1.controller;

import com.streamworks.api_service_1.dto.Todo;
import com.streamworks.api_service_1.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @PostMapping("/fetch")
    public String fetchAndSaveTodos(){
        return todoService.fetchAndSaveTodos();
    }

    @GetMapping
    public List<Todo> getAllTodos(){
        return todoService.getAllTodos();
    }
}
