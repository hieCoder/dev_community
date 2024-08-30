package com.shsoftvina.community;

import com.shsoftvina.community.config.CRLFLogConverter;
import com.shsoftvina.community.constant.ProfileConstant;
import com.shsoftvina.community.utils.DefaultProfileUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@SpringBootApplication
@ConfigurationPropertiesScan("com.shsoftvina.community")
public class CommunityApplication {

	private final Environment env;

	public CommunityApplication(Environment env){
		this.env = env;
	}

	@PostConstruct
	public void initApplication() {
		Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
		if (
				activeProfiles.contains(ProfileConstant.SPRING_PROFILE_DEVELOPMENT) &&
						activeProfiles.contains(ProfileConstant.SPRING_PROFILE_PRODUCTION)
		) {
			log.error(
					"You have misconfigured your application! It should not run " + "with both the 'dev' and 'prod' profiles at the same time."
			);
		}
		if (
				activeProfiles.contains(ProfileConstant.SPRING_PROFILE_DEVELOPMENT) &&
						activeProfiles.contains(ProfileConstant.SPRING_PROFILE_CLOUD)
		) {
			log.error(
					"You have misconfigured your application! It should not " + "run with both the 'dev' and 'cloud' profiles at the same time."
			);
		}
	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(CommunityApplication.class);
		DefaultProfileUtil.addDefaultProfile(app);
		Environment env = app.run(args).getEnvironment();
		logApplicationStartup(env);
	}

	private static void logApplicationStartup(Environment env) {
		String applicationName = env.getProperty("spring.application.name");
		String protocol = Optional.ofNullable(env.getProperty("server.ssl.key-store")).map(key -> "https").orElse("http");
		String serverPort = env.getProperty("server.port");
		String contextPath = Optional
				.ofNullable(env.getProperty("server.servlet.context-path"))
				.filter(StringUtils::isNotBlank)
				.orElse("/");
		String hostAddress = "localhost";
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			log.warn("The host name could not be determined, using `localhost` as fallback");
		}
		log.info(
				CRLFLogConverter.CRLF_SAFE_MARKER,
				"""
	
				----------------------------------------------------------
				\tApplication '{}' is running! Access URLs:
				\tLocal: \t\t{}://localhost:{}{}
				\tExternal: \t{}://{}:{}{}
				\tProfile(s): \t{}
				---------------------------------------------------------""",
				applicationName,
				protocol,
				serverPort,
				contextPath,
				protocol,
				hostAddress,
				serverPort,
				contextPath,
				env.getActiveProfiles().length == 0 ? env.getDefaultProfiles() : env.getActiveProfiles()
		);
	}
}
