package com.shsoftvina.community.modules.root.subscribemail;

import com.shsoftvina.community.domain.SubscribeMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubscribeMailRepository extends JpaRepository<SubscribeMail, Long> {

    @Query("select s from SubscribeMail s")
    List<SubscribeMail> findAll();

    @Query("select s from SubscribeMail s where s.email = :email")
    Optional<SubscribeMail> findByEmail(String email);
}