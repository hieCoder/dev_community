package com.shsoftvina.community.domain;

import com.shsoftvina.community.domain.enumration.EStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "book")
@Setter
@Getter
public class Book extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "cover")
    private String cover;

    @Column(name = "href")
    private String href;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EStatus status;
}