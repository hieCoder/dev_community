package com.shsoftvina.community.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Implementation of {@link AuditorAware} based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
	    String username = SecurityUtils.getCurrentUserLogin();
		return Optional.of(StringUtils.isBlank(username)? "anonymous": username);
	}
}