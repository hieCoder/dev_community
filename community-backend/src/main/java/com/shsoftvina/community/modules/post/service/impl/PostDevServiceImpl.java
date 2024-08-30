package com.shsoftvina.community.modules.post.service.impl;

import com.shsoftvina.community.domain.HashTag;
import com.shsoftvina.community.domain.Post;
import com.shsoftvina.community.domain.enumration.ECommentType;
import com.shsoftvina.community.domain.enumration.ELikeType;
import com.shsoftvina.community.domain.enumration.EStatus;
import com.shsoftvina.community.modules.comment.CountCommentProjection;
import com.shsoftvina.community.modules.comment.service.CommentDevService;
import com.shsoftvina.community.modules.hashtag.model.HashTagRes;
import com.shsoftvina.community.modules.hashtag.service.HashtagDevService;
import com.shsoftvina.community.modules.home.outstading.PostOutstandingProjection;
import com.shsoftvina.community.modules.home.outstading.OutstandingPostHotTopicUtil;
import com.shsoftvina.community.modules.home.outstading.OutstandingPostLatestGreatestUtil;
import com.shsoftvina.community.modules.ip.service.IpDevService;
import com.shsoftvina.community.modules.like.service.LikeDevService;
import com.shsoftvina.community.modules.post.PostDevRepository;
import com.shsoftvina.community.modules.post.mapper.OutstandingPostResMapper;
import com.shsoftvina.community.modules.post.mapper.PostDetailResMapper;
import com.shsoftvina.community.modules.post.mapper.PostResMapper;
import com.shsoftvina.community.modules.post.model.FilterPostByHashTagRes;
import com.shsoftvina.community.modules.post.model.OutstandingPostRes;
import com.shsoftvina.community.modules.post.model.PostDetailRes;
import com.shsoftvina.community.modules.post.model.PostRes;
import com.shsoftvina.community.modules.post.service.PostDevService;
import com.shsoftvina.community.modules.root.post.service.impl.PostServiceImpl;
import com.shsoftvina.community.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostDevServiceImpl extends PostServiceImpl implements PostDevService {

    private final int SIZE_OF_LIST_RALATED_GREATEST = 4;

    @Autowired
    private PostDevRepository postDevRepository;

    @Autowired
    private OutstandingPostResMapper outstandingPostResMapper;

    @Autowired
    private PostResMapper postResMapper;

    @Autowired
    private OutstandingPostHotTopicUtil outstandingPostHotTopicUtil;

    @Autowired
    private OutstandingPostLatestGreatestUtil outstandingPostLatestGreatestUtil;

    @Autowired
    private HashtagDevService hashtagDevService;

    @Autowired
    private CommentDevService commentDevService;

    @Autowired
    private PostDetailResMapper postDetailResMapper;

    @Autowired
    private LikeDevService likeDevService;

    @Autowired
    private IpDevService ipDevService;

    @Value("${spring.security.oauth2.client.registration.facebook.appId}")
    private String fbAppId;

    @Override
    public List<OutstandingPostRes> getListOutstandingHotTopic(Pageable pageable) {
        return this.getListOutstanding(
                pageable,
                outstandingPostHotTopicUtil::getScore
        );
    }

    @Override
    public List<OutstandingPostRes> getListOutstandingLatestAndGreatest(Pageable pageable) {
        return this.getListOutstanding(
                pageable,
                outstandingPostLatestGreatestUtil::getScore
        );
    }

    private List<OutstandingPostRes> getListOutstanding(
            Pageable pageable,
            Function<PostOutstandingProjection, Float> scoreFunction) {

        List<PostOutstandingProjection> outstandingPostList = this.getListOutstandingPostInfo();

        Map<Long, Float> topMap = outstandingPostList.stream()
                .collect(Collectors.toMap(
                        PostOutstandingProjection::getId,
                        scoreFunction,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<Long, Float>comparingByValue(Comparator.reverseOrder()))
                .limit(pageable.getPageSize())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        List<Long> postIds = new ArrayList<>(topMap.keySet());

        List<Post> sortedPosts = postDevRepository.findByIdIn(postIds).stream().toList();

        Map<Long, List<HashTagRes>> hashTagMap = hashtagDevService.getMapHashTagOfPost(postIds);

        return outstandingPostResMapper.toDto(sortedPosts).stream()
                .peek(c -> c.setHashTagList(hashTagMap.getOrDefault(c.getId(), Collections.emptyList())))
                .toList();
    }

    @Override
    public List<OutstandingPostRes> getListOutstandingNewAndNoteworthy(Pageable pageable) {
        List<Post> posts = postDevRepository.findAllPost(pageable);
        return posts.stream()
                .map(outstandingPostResMapper::toDto)
                .peek(p -> p.setHashTagList(this.hashtagDevService.getListHashTagResOfPost(p.getId())))
                .toList();
    }

    @Override
    public List<PostOutstandingProjection> getListOutstandingPostInfo() {
        return postDevRepository.getPostOutstanding();
    }

    @Override
    public List<PostRes> findAll() {

        List<Post> posts = postDevRepository.findAllPost(Pageable.unpaged());
        List<Long> postIds = posts.stream().map(Post::getId).toList();

        List<CountCommentProjection> countCommentProjections = commentDevService.getListTotalCommentOfPost(postIds);

        Map<Long, List<HashTagRes>> hashTagMap = hashtagDevService.getMapHashTagOfPost(postIds);

        return posts.stream().map(p -> {
            PostRes postRes = postResMapper.toDto(p);
            countCommentProjections.stream()
                    .filter(c -> c.getEventId() == postRes.getId())
                    .findFirst()
                    .ifPresent(c -> postRes.setTotalComment(c.getTotalComment()));

            List<HashTagRes> hashtags = hashTagMap.getOrDefault(postRes.getId(), Collections.emptyList());
            postRes.setHashTagList(hashtags);
            return postRes;
        }).toList();
    }

    @Override
    public PostDetailRes findDetail(String ipClient, Long postId) {

        Post post = this.findDetail(postId);

        this.updateCountReadHashTag(postId);
        this.updateView(post);

        PostDetailRes res = postDetailResMapper.toDto(post);
        res.setHashTagList(hashtagDevService.getListHashTagResOfPost(postId));
        res.setTotalComment(commentDevService.getTotalCommentOfEvent(postId, ECommentType.POST));
        res.setListIpClientLiked(likeDevService.getListIpClientLiked(postId, ELikeType.POST));
        this.setListRelatedGreatest(res);
        res.setNickNameDefault(ipDevService.updateIp(ipClient).getNickName());
        res.setFbAppId(fbAppId);
        return res;
    }

    @Async
    public void updateView(Post post) {
        post.setTotalView((post.getTotalView() == null ? 0 : post.getTotalView()) + 1);
        super.save(post);
    }

    private void setListRelatedGreatest(PostDetailRes res) {
        List<String> hashTagList = res.getHashTagList().stream().map(HashTagRes::getName).toList();
        Long postIdCurrent = res.getId();

        Pageable pageable = PageRequest.ofSize(SIZE_OF_LIST_RALATED_GREATEST);
        List<Post> postRelatedGreatest = postDevRepository.findAllOtherPostByHashTagIn(pageable, postIdCurrent, hashTagList);

        if (postRelatedGreatest.isEmpty()) {
            Pageable fallbackPageable = PageRequest.ofSize(SIZE_OF_LIST_RALATED_GREATEST);
            postRelatedGreatest = postDevRepository.findAllPost(fallbackPageable)
                    .stream()
                    .filter(p -> !p.getId().equals(postIdCurrent))
                    .toList();
        }

        List<Long> postIds = postRelatedGreatest.stream().map(Post::getId).toList();
        Map<Long, List<HashTagRes>> hashTagMap = hashtagDevService.getMapHashTagOfPost(postIds);

        List<OutstandingPostRes> result = postRelatedGreatest.stream().map(p -> {
            OutstandingPostRes o = outstandingPostResMapper.toDto(p);
            List<HashTagRes> hashtags = hashTagMap.getOrDefault(o.getId(), Collections.emptyList());
            o.setHashTagList(hashtags);
            return o;
        }).toList();

        res.setListRelatedGreatest(result);
    }

    @Override
    public void updateReadingTime(Long id, Integer second) {
        Post post = this.findDetail(id);
        post.setTotalReadSecond(Optional.ofNullable(post.getTotalReadSecond()).orElse(0) + second);
        super.save(post);
    }

    @Async
    public CompletableFuture<List<PostRes>> findPostsByHashTagIdAsync(Long hashTagId) {
        List<PostRes> posts = postDevRepository.findAllByHashTagId(hashTagId).stream()
                .map(postResMapper::toDto)
                .peek(p -> p.setHashTagList(this.hashtagDevService.getListHashTagResOfPost(p.getId())))
                .toList();
        return CompletableFuture.completedFuture(posts);
    }

    @Async
    public CompletableFuture<List<HashTagRes>> getRelatedPostHashTagsByFilterHashTagAsync(Long hashTagId) {
        List<HashTagRes> hashTagRelatedList = hashtagDevService.getLisRelatedPostByFilterHashTag(hashTagId);
        return CompletableFuture.completedFuture(hashTagRelatedList);
    }

    public FilterPostByHashTagRes findAllByHashTagId(Long hashTagId) {
        CompletableFuture<List<PostRes>> postsFuture = findPostsByHashTagIdAsync(hashTagId);
        CompletableFuture<List<HashTagRes>> hashTagRelatedListFuture = getRelatedPostHashTagsByFilterHashTagAsync(hashTagId);

        CompletableFuture.allOf(postsFuture, hashTagRelatedListFuture).join();

        List<PostRes> posts = postsFuture.join();
        List<HashTagRes> hashTagRelatedList = hashTagRelatedListFuture.join();

        return new FilterPostByHashTagRes(posts, hashTagRelatedList);
    }

    @Override
    public void updateSharing(Long id) {
        Post post = this.findDetail(id);
        post.setTotalShare(Optional.ofNullable(post.getTotalShare()).orElse(0) + 1);
        super.save(post);
    }

    @Async
    public void updateCountReadHashTag(Long postId) {
        List<HashTag> hashTagList = hashtagDevService.getListHashTagOfPost(postId).stream()
                .peek(h -> {
                    int countReadInWeek = (h.getLastModifiedDate() != null && DateUtils.isDateInCurrentWeek(h.getLastModifiedDate()))
                            ? (h.getCountReadInWeek() == null ? 0 : h.getCountReadInWeek()) + 1
                            : 1;
                    h.setCountReadInWeek(countReadInWeek);
                    h.setCountRead((h.getCountReadInWeek() == null ? 0 : h.getCountReadInWeek()) + 1);
                })
                .toList();
        hashtagDevService.saveAll(hashTagList);
    }

    private Post findDetail(Long postId){
        return super.findById(postId, List.of(EStatus.ACTIVATED));
    }
}