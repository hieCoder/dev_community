package com.shsoftvina.community.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "read_full")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReadFull {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "ip_id")
    private Long ipId;

    @Column(name = "is_read_full")
    private Boolean isReadFull;
}
