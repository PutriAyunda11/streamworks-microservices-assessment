package com.streamworks.api_service_1.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.streamworks.api_service_1.dto.Post;
import com.streamworks.api_service_1.dto.PostListWrapper;
import com.streamworks.api_service_1.dto.Todo;
import com.streamworks.api_service_1.dto.User;
import com.streamworks.api_service_1.dto.UserCombinedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final RestTemplate restTemplate = new RestTemplate();
    private final XmlMapper xmlMapper = new XmlMapper();

    private static final String EXTERNAL_USERS_URL = "https://jsonplaceholder.typicode.com/users";
    private static final String EXTERNAL_TODOS_URL = "https://jsonplaceholder.typicode.com/todos";

    private static final String API_SERVICE_2_XML_URL = "http://api-service-2:8082/posts";

    private static final String REDIS_USERS_KEY = "USERS_CACHE";

    @SuppressWarnings("unchecked")
    public List<User> getUsersFromCacheOrApi() {
        List<User> cachedUsers = (List<User>) redisTemplate.opsForValue().get(REDIS_USERS_KEY);

        if (cachedUsers == null || cachedUsers.isEmpty()) {
            User[] response = restTemplate.getForObject(EXTERNAL_USERS_URL, User[].class);
            if (response != null) {
                cachedUsers = Arrays.asList(response);
                redisTemplate.opsForValue().set(REDIS_USERS_KEY, cachedUsers);
            }
        }
        return cachedUsers != null ? cachedUsers : new ArrayList<>();
    }

    public List<UserCombinedResponse> getCombinedUserData() {
        List<User> users = getUsersFromCacheOrApi();

        Todo[] todosArray = restTemplate.getForObject(EXTERNAL_TODOS_URL, Todo[].class);
        List<Todo> todos = todosArray != null ? Arrays.asList(todosArray) : new ArrayList<>();

        List<Post> posts = new ArrayList<>();
        try {
            String xmlResponse = restTemplate.getForObject(API_SERVICE_2_XML_URL, String.class);
            if (xmlResponse != null) {
                PostListWrapper wrapper = xmlMapper.readValue(xmlResponse, PostListWrapper.class);
                if (wrapper != null && wrapper.getPosts() != null) {
                    posts = wrapper.getPosts();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            posts = new ArrayList<>();
        }

        List<Post> finalPosts = posts;
        return users.stream().map(user -> {
            List<Todo> userTodos = todos.stream()
                    .filter(t -> t.getUserId() != null && t.getUserId().equals(user.getId()))
                    .collect(Collectors.toList());

            List<Post> userPosts = finalPosts.stream()
                    .filter(p -> p.getUserId() != null && p.getUserId().equals(user.getId()))
                    .collect(Collectors.toList());

            return new UserCombinedResponse(
                    user.getId(),
                    user.getName(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getAddress(),
                    user.getPhone(),
                    user.getWebsite(),
                    user.getCompany(),
                    userTodos,
                    userPosts
            );
        }).collect(Collectors.toList());
    }
}