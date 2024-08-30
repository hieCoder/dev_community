package com.shsoftvina.community.domain;

import com.shsoftvina.community.domain.enumration.EStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "example")
@Setter
@Getter
public class Example extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "video")
    private String video;

    @Column(name = "resource", length = 10000)
    private String resource;

    @Column(name = "source_code")
    private String sourceCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EStatus status;

    @Column(name="cover")
    private String cover;

    @ManyToOne(fetch = FetchType.LAZY)
    private Component component;

    @ManyToMany(mappedBy = "examples")
    private List<Post> posts;
}