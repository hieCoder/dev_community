package com.shsoftvina.community.modules.component.service.impl;

import com.shsoftvina.community.domain.Component;
import com.shsoftvina.community.domain.ComponentCategory;
import com.shsoftvina.community.domain.enumration.ECommentType;
import com.shsoftvina.community.domain.enumration.ELikeType;
import com.shsoftvina.community.modules.comment.service.CommentDevService;
import com.shsoftvina.community.modules.component.ComponentDevRepository;
import com.shsoftvina.community.modules.component.mapper.ComponentDetailDevResMapper;
import com.shsoftvina.community.modules.component.mapper.ListComponentDevResMapper;
import com.shsoftvina.community.modules.component.mapper.OutstandingComponentResMapper;
import com.shsoftvina.community.modules.component.model.res.ComponentDetailDevRes;
import com.shsoftvina.community.modules.component.model.res.ListComponentDevRes;
import com.shsoftvina.community.modules.component.model.res.OutstandingComponentRes;
import com.shsoftvina.community.modules.component.service.ComponentDevService;
import com.shsoftvina.community.modules.component_category.service.ComponentCategoryDevService;
import com.shsoftvina.community.modules.hashtag.model.HashTagRes;
import com.shsoftvina.community.modules.hashtag.service.HashtagDevService;
import com.shsoftvina.community.modules.home.outstading.ComponentOutstandingProjection;
import com.shsoftvina.community.modules.home.outstading.OutstandingComponentPopularUtil;
import com.shsoftvina.community.modules.ip.service.IpDevService;
import com.shsoftvina.community.modules.like.service.LikeDevService;
import com.shsoftvina.community.modules.root.component.service.impl.ComponentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ComponentDevServiceImpl extends ComponentServiceImpl implements ComponentDevService {

    @Autowired
    private ComponentDevRepository componentDevRepository;

    @Autowired
    private OutstandingComponentResMapper outstandingComponentResMapper;

    @Autowired
    private OutstandingComponentPopularUtil outstandingComponentPopularUtil;

    @Autowired
    private HashtagDevService hashtagDevService;

    @Autowired
    private ListComponentDevResMapper listComponentDevResMapper;

    @Autowired
    private ComponentCategoryDevService componentCategoryDevService;

    @Autowired
    private ComponentDetailDevResMapper componentDetailDevResMapper;

    @Autowired
    private IpDevService ipDevService;

    @Autowired
    private LikeDevService likeDevService;

    @Autowired
    private CommentDevService commentDevService;

    private final String ANONIMUS_USERNAME = null;

    @Value("${spring.security.oauth2.client.registration.facebook.appId}")
    private String fbAppId;

    @Override
    public List<OutstandingComponentRes> getListOutstanding(Pageable pageable) {
        List<ComponentOutstandingProjection> outstandingPostList = this.getListOutstandingComponentInfo();

        Map<Long, Float> topMap = outstandingPostList.stream()
                .collect(Collectors.toMap(
                        ComponentOutstandingProjection::getId,
                        outstandingComponentPopularUtil::getScore,
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

        List<Long> componentIds = new ArrayList<>(topMap.keySet());

        List<Component> sortedComponents = componentDevRepository.findByIdIn(componentIds).stream().toList();

        Map<Long, List<HashTagRes>> hashTagMap = hashtagDevService.getMapHashTagOfComponent(componentIds);

        return outstandingComponentResMapper.toDto(sortedComponents).stream()
                .peek(c -> c.setHashTagList(hashTagMap.getOrDefault(c.getId(), Collections.emptyList())))
                .toList();
    }

    @Override
    public List<ComponentOutstandingProjection> getListOutstandingComponentInfo() {
        return componentDevRepository.getComponentOutstanding();
    }

    @Override
    public ListComponentDevRes findAll() {

        List<Component> components = componentDevRepository.findAllByRole(ANONIMUS_USERNAME);
        List<ComponentCategory> categories = componentCategoryDevService.findAllByRole(ANONIMUS_USERNAME);
        return listComponentDevResMapper.toDto(categories, components);
    }

    @Override
    public ComponentDetailDevRes getDetail(String ipClient, Long id) {

        Component component = super.findById(id);
        this.updateView(component);

        ComponentDetailDevRes res = componentDetailDevResMapper.toDto(component);
        res.setHashTagList(hashtagDevService.getListHashTagResOfComponent(id));
        res.setNickNameDefault(ipDevService.updateIp(ipClient).getNickName());
        res.setListIpClientLiked(likeDevService.getListIpClientLiked(id, ELikeType.COMPONENT));
        res.setTotalComment(commentDevService.getTotalCommentOfEvent(id, ECommentType.COMPONENT));
        res.setFbAppId(fbAppId);
        return res;
    }

    @Override
    public void updateSharing(Long id) {
        Component component = super.findById(id);
        component.setTotalShare(Optional.ofNullable(component.getTotalShare()).orElse(0) + 1);
        super.save(component);
    }

    @Async
    public void updateView(Component component) {
        component.setTotalView((component.getTotalView() == null ? 0 : component.getTotalView()) + 1);
        super.save(component);
    }
}
