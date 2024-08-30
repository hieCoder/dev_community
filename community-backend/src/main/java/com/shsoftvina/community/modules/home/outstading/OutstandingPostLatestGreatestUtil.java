package com.shsoftvina.community.modules.home.outstading;

import org.springframework.stereotype.Component;

@Component
public class OutstandingPostLatestGreatestUtil {

    private final float RATIO_VIEW_POST_LATEST_GREATEST = 0.2f;
    private final float RATIO_LIKE_POST_LATEST_GREATEST = 0.1f;
    private final float RATIO_SHARE_POST_LATEST_GREATEST = 0.1f;
    private final float RATIO_COMMENT_POST_LATEST_GREATEST = 0.2f;
    private final float RATIO_USER_READING_TIME_POST = 0.1f;
    private final float RATIO_USER_READ_FULL_POST = 0.3f;

    public float getScore(PostOutstandingProjection event){

        long commentCount = event.getCommentCount();
        long likeCount = event.getLikeCount();
        long shareCount = event.getShareCount();
        long viewCount = event.getViewCount();
        long readSecondTotal = event.getTotalReadSecond();
        long totalReadFullCount = event.getTotalReadFullCount();

        long totalIp = 1;

        return getScoreOfComment(commentCount)
                + getScoreOfLike(likeCount)
                + getScoreOfShare(shareCount)
                + getScoreOfView(viewCount)
                + getScoreOfReadingTime(readSecondTotal, totalIp)
                + getScoreOfCompletionRate(totalReadFullCount, totalIp);
    }

    private float getScoreOfView(long viewCount) {
        return viewCount * RATIO_VIEW_POST_LATEST_GREATEST;
    }

    private float getScoreOfLike(long likeCount) {
        return likeCount * RATIO_LIKE_POST_LATEST_GREATEST;
    }

    private float getScoreOfShare(long shareCount) {
        return shareCount * RATIO_SHARE_POST_LATEST_GREATEST;
    }

    private float getScoreOfComment(long commentCount) {
        return commentCount * RATIO_COMMENT_POST_LATEST_GREATEST;
    }

    private float getScoreOfReadingTime(long readSecondTotal, long totalIp) {
        if(totalIp == 0) return totalIp;
        return (readSecondTotal/totalIp) * RATIO_USER_READING_TIME_POST;
    }

    private float getScoreOfCompletionRate(long totalReadFullCount, long totalIp) {
        if(totalIp == 0) return totalIp;
        return (totalReadFullCount/ totalIp) * RATIO_USER_READ_FULL_POST;
    }
}
