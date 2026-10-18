package com.streamworks.api_service_2.controller;

import com.streamworks.api_service_2.dto.PostListWrapper;
import com.streamworks.api_service_2.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/fetch")
    public String fetchAndSavePosts(){
        return postService.fetchAndSavePosts();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public PostListWrapper getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_XML_VALUE)
    public PostListWrapper getPostsByUserId(@PathVariable Long userId){
        return postService.getPostsByUserId(userId);
    }
}
