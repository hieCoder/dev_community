package com.shsoftvina.community.modules.admin.post.service.impl;

import com.shsoftvina.community.domain.Post;
import com.shsoftvina.community.domain.User;
import com.shsoftvina.community.domain.enumration.EActionNotification;
import com.shsoftvina.community.domain.enumration.EHashTagType;
import com.shsoftvina.community.domain.enumration.ERole;
import com.shsoftvina.community.domain.enumration.EStatus;
import com.shsoftvina.community.exception.BadRequestAlertException;
import com.shsoftvina.community.exception.ErrorEnum;
import com.shsoftvina.community.management.SchedulingManagement;
import com.shsoftvina.community.modules.admin.example.service.ExampleAdminService;
import com.shsoftvina.community.modules.admin.group_noti.service.GroupNotiAdminService;
import com.shsoftvina.community.modules.admin.hashtag.service.HashTagAdminService;
import com.shsoftvina.community.modules.admin.notification.service.NotificationAdminService;
import com.shsoftvina.community.modules.admin.post.PostAdminRepository;
import com.shsoftvina.community.modules.admin.post.mapper.*;
import com.shsoftvina.community.modules.admin.post.model.req.UpdateDrafPostAdminReq;
import com.shsoftvina.community.modules.admin.post.model.req.CreatePostAdminReq;
import com.shsoftvina.community.modules.admin.post.model.req.EditPostAdminReq;
import com.shsoftvina.community.modules.admin.post.model.res.PostAdminRes;
import com.shsoftvina.community.modules.admin.post.model.res.PostDetailAdminRes;
import com.shsoftvina.community.modules.admin.post.service.PostAdminService;
import com.shsoftvina.community.modules.admin.user.service.UserAdminService;
import com.shsoftvina.community.modules.hashtag.model.HashTagRes;
import com.shsoftvina.community.modules.root.notification.model.req.CreateNotificationReq;
import com.shsoftvina.community.modules.root.post.service.impl.PostServiceImpl;
import com.shsoftvina.community.security.SecurityUtil;
import com.shsoftvina.community.service.MailService;
import com.shsoftvina.community.config.SecurityUtils;
import com.shsoftvina.community.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static com.shsoftvina.community.domain.enumration.EStatus.*;

@Service
@Transactional
public class PostAdminServiceImpl extends PostServiceImpl implements PostAdminService {

    @Setter
    @Getter
    @AllArgsConstructor
    public static class UserAccessDetail{
        private String username;
        private Long postId;
    }

    private List<UserAccessDetail> userAccessDetailList = new ArrayList<>();

    @Autowired
    private PostAdminRepository postAdminRepository;

    @Autowired
    private PostResAdminMapper postResAdminMapper;

    @Autowired
    private CreatePostAdminReqMapper createPostAdminReqMapper;

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private HashTagAdminService hashTagAdminService;

    @Autowired
    private ExampleAdminService exampleAdminService;

    @Autowired
    private SchedulingManagement schedulingManagement;

    @Autowired
    private UpdatePostDrafAdminReqMapper updatePostDrafAdminReqMapper;

    @Autowired
    private EditPostAdminReqMapper editPostAdminReqMapper;

    @Autowired
    private PostDetailAdminResMapper postDetailAdminResMapper;

    @Autowired
    private MailService mailService;

    @Autowired
    private NotificationAdminService notificationAdminService;

    @Autowired
    private GroupNotiAdminService groupNotiAdminService;

    @Override
    public List<PostAdminRes> findAll() {
        List<Post> posts = postAdminRepository.findAllByUsername(SecurityUtil.getUsernameQuery());
        List<Long> postIds = posts.stream().map(Post::getId).toList();

        Map<Long, List<HashTagRes>> hashTagMap = hashTagAdminService.getMapHashTagOfPost(postIds);

        return posts.stream().map(p -> {
            PostAdminRes postRes = postResAdminMapper.toDto(p);
            postRes.setHashTagList(hashTagMap.getOrDefault(postRes.getId(), Collections.emptyList()));
            return postRes;
        }).toList();
    }

    @Override
    public void createPost(CreatePostAdminReq req) {

        Long drafId = req.getDraftId();
        List<String> hashTagListReq = req.getHashTagList();
        List<Long> relatedExampleIdstReq = req.getExampleIds();

        this.handleDeleteDraf(drafId);

        Post post = createPostAdminReqMapper.toEntity(req);
        this.handleSetCreatePosting(post, req);
        post.setUser(userAdminService.findByUsername(SecurityUtils.getCurrentUserLogin()));
        post.setExamples(exampleAdminService.findByIdIn(relatedExampleIdstReq));
        post = super.save(post);

        Long postId = post.getId();
        this.updateHashTagListForCreatePost(hashTagListReq, postId);

        if(post.getStatus().equals(ACTIVATED)){

            mailService.handSendMailTop5PostLatest(post, LocaleContextHolder.getLocale());

            if(this.isUserCurrentTurnOnGroup()){
                notificationAdminService.createNotification(CreateNotificationReq.buildForPost(post, EActionNotification.CREATE_POST, false));
            }
        } else if (post.getStatus().equals(DEACTIVATED)){
            schedulingManagement.createPostScheduling(post, post.getPostingTime());
        }
    }

    @Async
    public void handleDeleteDraf(Long drafId) {
        if(drafId != null ){
            this.deletePosts(List.of(drafId));
        }
    }

    private void handleSetCreatePosting(Post post, CreatePostAdminReq req) {
        Boolean isSchedulingReq = req.getIsSchedulingTemp();
        Instant postingTimeReq = req.getPostingTimeTemp();

        if(!isSchedulingReq){
            post.setPostingTime(Instant.now());
            post.setStatus(ACTIVATED);
            post.setIsScheduling(false);
        } else if(postingTimeReq != null && postingTimeReq.isAfter(Instant.now())){
            post.setPostingTime(postingTimeReq);
            post.setStatus(EStatus.DEACTIVATED);
            post.setIsScheduling(true);
        } else {
            throw new BadRequestAlertException(ErrorEnum.DATA_TIME_ERROR);
        }
    }

    private void handleSetCreatePostingDraf(Post post, UpdateDrafPostAdminReq req) {
        Boolean isSchedulingReq = req.getIsSchedulingTemp();
        Instant postingTimeReq = req.getPostingTimeTemp();

        if(!isSchedulingReq){
            post.setPostingTime(Instant.now());
            post.setStatus(ACTIVATED);
            post.setIsScheduling(false);
        } else if(postingTimeReq != null && postingTimeReq.isAfter(Instant.now())){
            post.setPostingTime(postingTimeReq);
            post.setStatus(EStatus.DEACTIVATED);
            post.setIsScheduling(true);
        } else {
            throw new BadRequestAlertException(ErrorEnum.DATA_TIME_ERROR);
        }
    }

    @Override
    public void deletePosts(List<Long> ids) {
        super.deletePosts(ids);
    }

    @Override
    public PostAdminRes updateDrafPost(UpdateDrafPostAdminReq req) {
        Long idReq = req.getId();
        List<String> hashTagListReq = req.getHashTagList();
        List<Long> exampleIdsReq = req.getExampleIds();
        LocalDate dateReq = req.getDatePost();
        LocalTime timeReq = req.getTimePost();
        Instant postingTimeReq = DateUtils.mergeToInstant(dateReq, timeReq);

        Post post = null;
        if(idReq == null){
            post = updatePostDrafAdminReqMapper.toEntity(req);
            this.handleSetCreatePostingDraf(post, req);
            post.setStatus(EStatus.DRAF);
            post.setUser(userAdminService.findByUsername(SecurityUtils.getCurrentUserLogin()));
            post.setExamples(exampleAdminService.findByIdIn(exampleIdsReq));
            post = super.save(post);

            this.updateHashTagListForCreatePost(hashTagListReq, post.getId());
        } else {
            post = this.getDetail(idReq);

            this.updateHashTagListForEditPost(hashTagListReq, idReq);

            updatePostDrafAdminReqMapper.partialUpdate(post, req);
            post.setExamples(exampleAdminService.findByIdIn(exampleIdsReq));
            post.setPostingTime(postingTimeReq);
            post = super.save(post);
        }

        return postResAdminMapper.toDto(post);
    }

    @Override
    public void editPost(EditPostAdminReq req) {
        Long postIdReq = req.getId();
        List<String> hashTagListReq = req.getHashTagList();
        List<Long> exampleIdsReq = req.getExampleIds();

        Post post = this.getDetail(postIdReq);

        this.updateHashTagListForEditPost(hashTagListReq, postIdReq);

        editPostAdminReqMapper.partialUpdate(post, req);
        post.setExamples(exampleAdminService.findByIdIn(exampleIdsReq));
        this.handleSetEditPostingAndScheduling(post, req);

        post = super.save(post);

        if(post.getStatus().equals(ACTIVATED) && this.isUserCurrentTurnOnGroup()){
            notificationAdminService.createNotification(CreateNotificationReq.buildForPost(post, EActionNotification.UPDATE_POST, false));
        }
    }

    private void handleSetEditPostingAndScheduling(Post post, EditPostAdminReq req) {
        Boolean isSchedulingReq = req.getIsSchedulingTemp();
        Instant postingTimeReq = req.getPostingTimeTemp();
        if(post.getPostingTime() != null && post.getPostingTime().equals(postingTimeReq)){
            isSchedulingReq = false;
        }

        if(!post.getIsScheduling()){ // database no scheduling
            if(isSchedulingReq){ // req scheduling
                post.setPostingTime(postingTimeReq);
                post.setIsScheduling(true);
                post.setStatus(DEACTIVATED);

                // scheduling
                schedulingManagement.editPostScheduling(post, postingTimeReq);
            } else { // req no scheduling
                post.setStatus(ACTIVATED);
            }
        } else {// database scheduling
            if(!isSchedulingReq){ // req no scheduling
                post.setPostingTime(Instant.now());
                post.setIsScheduling(false);
                post.setStatus(ACTIVATED);

                // cancel scheduling
                schedulingManagement.stopPostScheduling(post);
            } else { // req scheduling
                post.setPostingTime(postingTimeReq);
                post.setStatus(DEACTIVATED);
                // restart scheduling with new posting time
                schedulingManagement.replaceNewPostScheduling(post, postingTimeReq);
            }
        }
    }

    @Async
    public void updateHashTagListForEditPost(List<String> hashTagList, Long postId) {
        hashTagAdminService.deleteHashTagList(postId, EHashTagType.POST);
        this.updateHashTagListForCreatePost(hashTagList, postId);
    }

    @Override
    public PostDetailAdminRes findDetail(Long id) {

        Post post = this.getDetail(id);
        User userCurrent = userAdminService.findByUsername(SecurityUtil.getUsernameCurrent());
        if(!userCurrent.getRole().equals(ERole.SUPER_ADMIN)
                && !userAdminService.findByUsername(SecurityUtil.getUsernameCurrent()).getUsername().equals(post.getUser().getUsername())){
            throw new BadRequestAlertException(ErrorEnum.NO_PERMISSION);
        }

        PostDetailAdminRes res = postDetailAdminResMapper.toDto(this.getDetail(id));
        res.setHashTagList(hashTagAdminService.getListHashTagResOfPost(id));
        return res;
    }

    @Override
    public void clearActivateAccessDetail(Long id) {
        if(!this.userAccessDetailList.isEmpty()){
            String currentUsername = SecurityUtil.getUsernameCurrent();
            this.userAccessDetailList.removeIf(uad ->
                    uad.getUsername().equals(currentUsername) && uad.postId.equals(id)
            );
        }
    }

    @Async
    public void updateHashTagListForCreatePost(List<String> hashTagList, Long postId) {
        hashTagAdminService.updateHashTagListOfPost(hashTagList, postId);
    }

    private Post getDetail(Long postId){
        return super.findById(postId, List.of(ACTIVATED, DEACTIVATED, DRAF));
    }

    private boolean isUserCurrentTurnOnGroup(){
        String username = SecurityUtil.getUsernameCurrent();
        return groupNotiAdminService.isUserHasGroupAccountActivity(username);
    }
}
