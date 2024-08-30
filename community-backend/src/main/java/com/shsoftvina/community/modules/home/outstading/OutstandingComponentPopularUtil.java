package com.shsoftvina.community.modules.home.outstading;

import org.springframework.stereotype.Component;

@Component
public class OutstandingComponentPopularUtil {

    private final float RATIO_VIEW_COMPONENT_POPULAR = 0.3f;
    private final float RATIO_LIKE_COMPONENT_POPULAR = 0.2f;
    private final float RATIO_SHARE_COMPONENT_POPULAR = 0.2f;
    private final float RATIO_COMMENT_COMPONENT_POPULAR = 0.15f;

    public float getScore(ComponentOutstandingProjection event){

        long commentCount = event.getCommentCount();
        long likeCount = event.getLikeCount();
        long shareCount = event.getShareCount();
        long viewCount = event.getViewCount();

        return getScoreOfComment(commentCount)
                + getScoreOfLike(likeCount)
                + getScoreOfShare(shareCount)
                + getScoreOfView(viewCount);
    }

    private float getScoreOfView(long viewCount) {
        return viewCount * RATIO_VIEW_COMPONENT_POPULAR;
    }

    private float getScoreOfLike(long likeCount) {
        return likeCount * RATIO_LIKE_COMPONENT_POPULAR;
    }

    private float getScoreOfShare(long shareCount) {
        return shareCount * RATIO_SHARE_COMPONENT_POPULAR;
    }

    private float getScoreOfComment(long commentCount) {
        return commentCount * RATIO_COMMENT_COMPONENT_POPULAR;
    }
}
