package com.shsoftvina.community.domain;

import com.shsoftvina.community.domain.enumration.EGroupNoti;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "group_noti")
@Setter
@Getter
public class GroupNoti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    @Enumerated(EnumType.STRING)
    private EGroupNoti code;

    @ManyToMany(mappedBy = "groups")
    private List<User> users;
}
