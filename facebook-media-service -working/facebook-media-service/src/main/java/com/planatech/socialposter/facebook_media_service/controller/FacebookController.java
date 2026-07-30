package com.planatech.socialposter.facebook_media_service.controller;

import java.time.Instant;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.planatech.socialposter.facebook_media_service.dto.MediaPostDTO;
import com.planatech.socialposter.facebook_media_service.service.FacebookService;

@RestController
@RequestMapping("/api/facebook")
public class FacebookController {

	@Autowired
	private FacebookService facebookService;

	@PostMapping("/post")
	public ResponseEntity<Map<String, Object>> postToFacebook(@RequestBody MediaPostDTO dto,
			@AuthenticationPrincipal Jwt jwt) {
		if (jwt == null || jwt.getExpiresAt().isBefore(Instant.now())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid JWT"));
		}
		try {
			Map<String, Object> response = facebookService.postMedia(dto);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
		}
	}

	@GetMapping("/post/{postId}")
	public ResponseEntity<Map<String, Object>> getFacebookPost(@PathVariable String postId,
			@RequestParam String accountId, @AuthenticationPrincipal Jwt jwt) {
		if (jwt == null || jwt.getExpiresAt().isBefore(Instant.now())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid JWT"));
		}
		try {
			Map<String, Object> response = facebookService.getPost(postId, accountId);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
		}
	}
}