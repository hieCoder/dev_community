package com.shsoftvina.community.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "event_hash_tag")
@Setter
@Getter
public class EventHashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "hash_tag_id")
    private Long hashTagId;
}
