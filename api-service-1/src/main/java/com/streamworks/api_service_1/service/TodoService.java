package com.streamworks.api_service_1.service;

import com.streamworks.api_service_1.dto.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class TodoService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String EXTERNAL_TODOS_URL = "https://jsonplaceholder.typicode.com/todos";
    private static final String REDIS_KEY = "TODOS_CACHE";

    public String fetchAndSaveTodos(){
        Todo[] response = restTemplate.getForObject(EXTERNAL_TODOS_URL, Todo[].class);

        if(response != null){
            List<Todo> todoList = Arrays.asList(response);
            redisTemplate.opsForValue().set(REDIS_KEY, todoList);
            return "Berhasil menyimpan "+ todoList.size() +" todos di Redis!";
        }

        return "Gagal mengambil data dari external API.";
    }

    @SuppressWarnings("unchecked")
    public List<Todo> getAllTodos(){
        List<Todo> cachedTodos = (List<Todo>) redisTemplate.opsForValue().get(REDIS_KEY);

        if(cachedTodos == null || cachedTodos.isEmpty()){
            Todo[] response = restTemplate.getForObject(EXTERNAL_TODOS_URL, Todo[].class);
            if(response != null){
                cachedTodos = Arrays.asList(response);
                redisTemplate.opsForValue().set(REDIS_KEY, cachedTodos);
            }
        }

        return cachedTodos;
    }
}
