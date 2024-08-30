package com.shsoftvina.community.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "example_changelog")
@Setter
@Getter
public class ExampleChangelog extends AbstractAuditingEntity<Long> {

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

    @Column(name="commit")
    private String commit;

    @Column(name="cover")
    private String cover;

    @ManyToOne
    private Changelog changelog;
}