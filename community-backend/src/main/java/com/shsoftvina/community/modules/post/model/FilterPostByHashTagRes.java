package com.shsoftvina.community.modules.post.model;

import com.shsoftvina.community.modules.hashtag.model.HashTagRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class FilterPostByHashTagRes {

    private List<PostRes> posts;
    private List<HashTagRes> hashTagRelatedList;
}
