package com.shsoftvina.community.modules.user;

import com.shsoftvina.community.domain.User;
import com.shsoftvina.community.modules.root.user.UserRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserDevRepository extends UserRepository {

    @Query("select u from User u join Post p on p.user.id = u.id where p.id = :postId")
    Optional<User> findByPost(Long postId);

    @Query("select u from User u join Component c on c.user.id = u.id where c.id = :componentId")
    Optional<User> findByComponent(Long componentId);
}
