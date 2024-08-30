package com.shsoftvina.community.service;

import com.shsoftvina.community.domain.Post;
import com.shsoftvina.community.model.authenticate.forgotpass.ForgotPassRes;
import com.shsoftvina.community.modules.admin.post.mapper.PostLatestMailResAdminMapper;
import com.shsoftvina.community.modules.admin.post.model.res.PostLatestMailAdminRes;
import com.shsoftvina.community.modules.media.model.MediaRes;
import com.shsoftvina.community.modules.root.post.PostRepository;
import com.shsoftvina.community.modules.root.subscribemail.service.SubscribeMailService;
import com.shsoftvina.community.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.shsoftvina.community.constant.ApplicationConstant.CLIENT_SERVER;

@Service
@Slf4j
public class MailService {

	private final String URI_CONFIRM_REGISTER_USER = "/api/v1/authenticate/confirm/user-register/";
	private final String URI_FORGOT_PASS_USER = "/confirm/user-forgot-pass/";

	private final int SIZE_POST_LATEST_BY_MAIL = 5;
	private final int LIMIT_CONTENT_WORD = 100;

	@Autowired
	private MailSenderService mailSender;

	@Autowired
	private SubscribeMailService subscribeMailService;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private PostLatestMailResAdminMapper postLatestMailResAdminMapper;

	@Async
	public void sendUriConfirmRegisterUser(String email, String token, Locale locale) {
		log.debug("[Confirm Register] Sending otp email to '{}'", email);
		String uri = CLIENT_SERVER + URI_CONFIRM_REGISTER_USER + token;
		mailSender.sendEmailFromTemplate(email, Map.of("uri", uri), "mail/confirmRegisterUser", "email.confirm-register-user.title", locale);
	}

	@Async
	public void sendUriConfirmForgotPassUser(String email, ForgotPassRes forgotPassRes, Locale locale) {
		log.debug("[Confirm Forgot Password] Sending otp email to '{}'", email);
		String uri = CLIENT_SERVER + URI_FORGOT_PASS_USER + forgotPassRes.getToken();

		String newPassword = forgotPassRes.getNewPassword();

		mailSender.sendEmailFromTemplate(email, Map.of("currentPass", newPassword, "uri", uri),
				"mail/confirmForgotPassUser", "email.confirm-forgot-pass-user.title", locale);
	}

	@Async
	public void handSendMailTop5PostLatest(Post post, Locale locale) {

		Page<Post> postPage = postRepository.findAll(PageRequest.of(0, SIZE_POST_LATEST_BY_MAIL - 1,
				Sort.by(Sort.Direction.DESC, "postingTime")));

		long totalPosts = postPage.getTotalElements() + 1;

		if (totalPosts % SIZE_POST_LATEST_BY_MAIL == 0) {

			List<Post> posts = postPage.getContent();
			posts = new ArrayList<>(posts);
			posts.add(0, post);

			List<PostLatestMailAdminRes> postLatestMailAdminRes = postLatestMailResAdminMapper.toDto(posts);

			postLatestMailAdminRes.forEach(p -> {

				String coverPath = null;
				MediaRes coverObj = JsonUtils.jsonToObject(p.getCover(), MediaRes.class);
				coverPath = coverObj != null ? coverObj.getPath() : null;

				if (coverPath != null) {
					coverPath = CLIENT_SERVER + "/api/v1/media/" + coverPath.replaceAll("\\\\", "/"); //"http://103.69.87.36:8080/api/v1/system/media/2024-07-19/image/2b241e45-9f59-4417-b5be-2fe6a905f601.png";//
				}

				p.setCover(coverPath);

				String content = p.getContent();
				if (content == null || content.isEmpty()) {
					return;
				}

				String[] words = content.split("\\s+");
				if (words.length <= LIMIT_CONTENT_WORD) {
					return;
				}

				StringBuilder limitedContent = new StringBuilder();
				for (int i = 0; i < LIMIT_CONTENT_WORD; i++) {
					limitedContent.append(words[i]).append(" ");
				}

				p.setContent(limitedContent.toString().trim() + "...");
			});

			subscribeMailService.findAll().stream().forEach(s-> {
				String email = s.getEmail();
				log.debug("Sending top 5 latest post to email '{}'", email);
				mailSender.sendEmailFromTemplate(email, Map.of("posts", postLatestMailAdminRes), "mail/top5LatestPost", "email.top5LatestPost.title", locale);
			});
		}
	}
}