package com.shsoftvina.community.modules.root.user;

import com.shsoftvina.community.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("select u from User u where u.username = :username and u.status = 'ACTIVATED' ")
    Optional<User> findByUsername(String username);

    @Query("select u from User u where u.email = :email and u.status = 'ACTIVATED' ")
    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.id = :id and u.status = 'ACTIVATED'")
    Optional<User> findById(Long id);

    @Query("select (count(u) > 0) from User u where " +
            "u.username = :username and u.status = 'ACTIVATED'")
    boolean existsByUsername(String username);

    @Query("select (count(u) > 0) from User u where " +
            "u.email = :email and u.status = 'ACTIVATED'")
    boolean existsByEmail(String email);

    @Query("select u from User u where u.refreshToken = :refreshToken")
    Optional<User> findByRefreshToken(String refreshToken);

    @Query("select u from User u join Post p on u.id = p.user.id where p.id = :postId")
    Optional<User> findByPostId(Long postId);
}
