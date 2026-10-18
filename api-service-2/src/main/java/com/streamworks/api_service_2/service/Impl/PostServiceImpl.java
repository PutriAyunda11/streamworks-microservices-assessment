package com.streamworks.api_service_2.service.Impl;

import com.streamworks.api_service_2.dto.PostListWrapper;
import com.streamworks.api_service_2.entity.Post;
import com.streamworks.api_service_2.repository.PostRepository;
import com.streamworks.api_service_2.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String EXTERNAL_POSTS_URL = "https://jsonplaceholder.typicode.com/posts";

    @Override
    public String fetchAndSavePosts(){
        Post[] response = restTemplate.getForObject(EXTERNAL_POSTS_URL, Post[].class);
        if(response != null){
            postRepository.saveAll(Arrays.asList(response));
            return "Berhasil menyimpan "+ response.length +" post ke PostgreSQL!";
        }
        return "Gagal mengambil data dari external API!";
    }

    @Override
    public PostListWrapper getAllPosts(){
        List<Post> posts = postRepository.findAll();

        if(posts.isEmpty()){
            Post[] response = restTemplate.getForObject(EXTERNAL_POSTS_URL, Post[].class);
            if(response != null){
                posts = postRepository.saveAll(Arrays.asList(response));
            }
        }

        return new PostListWrapper(posts);
    }

    @Override
    public PostListWrapper getPostsByUserId(Long userId){
        List<Post> posts = postRepository.findByUserId(userId);
        return new PostListWrapper(posts);
    }

}
