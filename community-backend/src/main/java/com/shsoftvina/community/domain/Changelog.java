package com.shsoftvina.community.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "changelog")
@Setter
@Getter
public class Changelog extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="code", unique = true)
    private String code;

    @Column(name="component_id")
    private Long componentId;

    @OneToMany(mappedBy = "changelog", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<ExampleChangelog> examples;
}