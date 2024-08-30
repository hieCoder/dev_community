package com.shsoftvina.community.domain;

import com.shsoftvina.community.domain.enumration.EHashTagType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hash_tag")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HashTag extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "event_type")
    @Enumerated(EnumType.STRING)
    private EHashTagType eventType;

    @Column(name = "count_read_in_week")
    private Integer countReadInWeek;

    @Column(name = "count_read")
    private Integer countRead;
}
