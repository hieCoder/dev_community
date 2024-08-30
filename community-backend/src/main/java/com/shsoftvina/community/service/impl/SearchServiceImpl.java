package com.shsoftvina.community.service.impl;

import com.shsoftvina.community.mapper.search.ComponentSearchResMapper;
import com.shsoftvina.community.mapper.search.PostSearchResMapper;
import com.shsoftvina.community.model.search.res.ComponentSearchRes;
import com.shsoftvina.community.model.search.res.PostSearchRes;
import com.shsoftvina.community.modules.root.component.service.ComponentService;
import com.shsoftvina.community.modules.root.post.service.PostService;
import com.shsoftvina.community.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private PostService postService;

    @Autowired
    private ComponentService componentService;

    @Autowired
    private PostSearchResMapper postSearchResMapper;

    @Autowired
    private ComponentSearchResMapper componentSearchResMapper;

    private final String POST_KEY = "post";

    private final String COMPONENT_KEY = "component";

    @Override
    public Map<String, Object> searchGlobal(String keyword) {

        CompletableFuture<List<PostSearchRes>> postFuture = this.searchAllPost(keyword);
        CompletableFuture<List<ComponentSearchRes>> componentFuture = this.searchAllCompoment(keyword);

        CompletableFuture.allOf(postFuture, componentFuture).join();

        List<PostSearchRes> posts = postFuture.join();

        List<ComponentSearchRes> components = componentFuture.join();

        Map<String, Object> map = Map.of(
                POST_KEY, posts,
                COMPONENT_KEY, components);
        return map;
    }

    @Async
    public CompletableFuture<List<PostSearchRes>> searchAllPost(String keyword){
        return CompletableFuture.completedFuture(postSearchResMapper.toDto(postService.findAll(keyword)));
    }

    @Async
    public CompletableFuture<List<ComponentSearchRes>> searchAllCompoment(String keyword){
        return CompletableFuture.completedFuture(componentSearchResMapper.toDto(componentService.findAll(keyword)));
    }
}
