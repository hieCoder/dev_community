package com.shsoftvina.community.domain;

import com.shsoftvina.community.domain.enumration.ERole;
import com.shsoftvina.community.domain.enumration.EStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.shsoftvina.community.security.AuthoritiesConstant.ROLE_PREFIX;

@Entity
@Table(name = "user")
@Data
public class User extends AbstractAuditingEntity<Long> implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "country")
    private String country;

    @Column(name = "title")
    private String title;

    @Column(name = "school")
    private String school;

    @Column(name = "degree")
    private String degree;

    @Column(name = "start_from")
    @Temporal(TemporalType.DATE)
    private LocalDate startFrom;

    @Column(name = "ending_in")
    @Temporal(TemporalType.DATE)
    private LocalDate endingIn;

    @Column(name = "skills")
    private String skills;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private ERole role;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "refresh_token_expired")
    private Instant refreshTokenExpired;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EStatus status;

    @ManyToMany
    private List<GroupNoti> groups;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(ROLE_PREFIX + this.role.name());
        return Collections.singleton(authority);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
