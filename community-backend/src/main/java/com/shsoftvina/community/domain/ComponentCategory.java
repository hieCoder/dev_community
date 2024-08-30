package com.shsoftvina.community.domain;

import com.shsoftvina.community.domain.enumration.EStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Table(name = "component_category")
@Setter
@Getter
public class ComponentCategory extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "category")
    @Where(clause = "status = 'ACTIVATED'")
    private List<Component> components;
}