package com.shsoftvina.community.modules.home.outstading;

public interface PostOutstandingProjection {

    long getId();
    String getTitle();
    long getCommentCount();
    long getLikeCount();
    long getShareCount();
    long getViewCount();
    long getTotalReadSecond();
    long getTotalReadFullCount();
}
