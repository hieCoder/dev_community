package com.shsoftvina.community.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ip")
@Setter
@Getter
public class Ip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ip_client", unique = true)
    private String ipClient;

    @Column(name = "nickName")
    private String nickName;
}
