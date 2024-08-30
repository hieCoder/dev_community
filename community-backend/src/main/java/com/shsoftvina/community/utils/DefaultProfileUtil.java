package com.shsoftvina.community.utils;

import com.shsoftvina.community.constant.ProfileConstant;
import org.springframework.boot.SpringApplication;

import java.util.HashMap;
import java.util.Map;

public class DefaultProfileUtil {
	
	private static final String SPRING_PROFILE_DEFAULT = "spring.profiles.default";
	
	private DefaultProfileUtil() {
	}

	public static void addDefaultProfile(SpringApplication app) {
		Map<String, Object> defProperties = new HashMap<>();
		defProperties.put(SPRING_PROFILE_DEFAULT, ProfileConstant.SPRING_PROFILE_DEVELOPMENT);
		app.setDefaultProperties(defProperties);
	}
}
