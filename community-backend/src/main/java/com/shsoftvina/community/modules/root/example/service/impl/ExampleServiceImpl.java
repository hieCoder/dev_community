package com.shsoftvina.community.modules.root.example.service.impl;

import com.shsoftvina.community.domain.Example;
import com.shsoftvina.community.domain.Post;
import com.shsoftvina.community.domain.enumration.EStatus;
import com.shsoftvina.community.modules.post.service.PostDevService;
import com.shsoftvina.community.modules.root.example.ExampleRepository;
import com.shsoftvina.community.modules.root.example.service.ExampleService;
import com.shsoftvina.community.modules.root.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Primary
@Slf4j
public class ExampleServiceImpl extends ExampleEntityServiceImpl implements ExampleService {

    @Autowired
    private ExampleRepository exampleRepository;

    @Autowired
    private PostService postService;

    @Override
    public void deleteAll(List<Example> examples) {
        exampleRepository.saveAll(examples.stream().peek(e -> e.setStatus(EStatus.DELETED)).toList());

        this.deleteRelatedExamples(examples);
    }

    @Async
    public void deleteRelatedExamples(List<Example> examples){
        log.debug("Delete related example");
        examples.forEach(e -> {
            List<Post> posts = e.getPosts();
            if(posts!= null){
                postService.saveAll(posts.stream().peek(p->p.setExamples(null)).toList());
            }
        });
    }
}
