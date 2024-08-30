package com.shsoftvina.community.domain;

import com.shsoftvina.community.domain.enumration.EStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Table(name = "component")
@Setter
@Getter
public class Component extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "when_to_use")
    private String whenToUse;

    @Column(name = "total_view")
    private Integer totalView;

    @Column(name = "total_share")
    private Integer totalShare;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private ComponentCategory category;

    @OneToMany(mappedBy = "component", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Where(clause = "status = 'ACTIVATED'")
    private List<Example> examples;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}