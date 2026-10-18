package com.streamworks.api_service_2.service;

import com.streamworks.api_service_2.dto.PostListWrapper;

public interface PostService {
    String fetchAndSavePosts();
    PostListWrapper getAllPosts();
    PostListWrapper getPostsByUserId(Long userId);
}
