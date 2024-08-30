package com.shsoftvina.community.domain;

import com.shsoftvina.community.domain.enumration.ECommentPermission;
import com.shsoftvina.community.domain.enumration.EStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "post")
@Setter
@Getter
public class Post extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "cover")
    private String cover;

    @Column(name = "posting_time")
    private Instant postingTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "comment_permission")
    private ECommentPermission commentPermission;

    @Column(name = "total_view")
    private Integer totalView;

    @Column(name = "total_share")
    private Integer totalShare;

    @Column(name = "total_read_second")
    private Integer totalReadSecond;

    @Column(name = "table_content", length = 10000)
    private String tableContent;

    @Column(name = "is_scheduling")
    private Boolean isScheduling;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "post_related_example",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "example_id"))
    private List<Example> examples;

    public Boolean getIsScheduling(){
        if(this.isScheduling != null && this.isScheduling) return true;
        return false;
    }
}