package com.shsoftvina.community.service;

import com.shsoftvina.community.modules.root.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserRepository userRepository;

    public DomainUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String login) {
        String trimmedLogin = login.trim();
        log.debug("Authenticating {}", trimmedLogin);

        return userRepository.findByUsername(trimmedLogin)
                .or(() -> userRepository.findByEmail(trimmedLogin))
                .orElseThrow(() -> new UsernameNotFoundException("User " + trimmedLogin + " not found"));
    }
}