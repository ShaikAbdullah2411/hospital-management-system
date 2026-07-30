package com.planatech.socialposter.facebook_media_service.service;


import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.planatech.socialposter.facebook_media_service.dto.MediaPostDTO;
import com.planatech.socialposter.facebook_media_service.entity.AccessToken;

@Service
public class FacebookService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RestTemplate restTemplate;

    private static final String FACEBOOK_API_URL = "https://graph.facebook.com/v24.0";  // Latest Graph API version as of 2025

    public Map<String, Object> postMedia(MediaPostDTO dto) {
        // Fetch access token from MongoDB
        AccessToken accessToken = mongoTemplate.findOne(
                Query.query(Criteria.where("accountId").is(dto.getAccountId())),
                AccessToken.class
        );
        if (accessToken == null) {
            throw new RuntimeException("Access token not found for accountId: " + dto.getAccountId());
        }

        // Prepare multipart request for media upload
        String endpoint = dto.getType().equals("photo") ? "/photos" : "/videos";
        String url = FACEBOOK_API_URL + "/" + dto.getAccountId() + endpoint;

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("access_token", accessToken.getToken());
        body.add("caption", dto.getCaption());
        body.add("description", dto.getDescription());
        body.add("privacy", "{\"value\":\"" + dto.getPrivacyStatus() + "\"}");
        body.add("source", new FileSystemResource(new File(dto.getFilePath())));  // Handle file upload

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, requestEntity, Map.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Facebook post failed: " + response.getStatusCode());
        }

        return response.getBody();  // Returns post ID, etc.
    }

    public Map<String, Object> getPost(String postId, String accountId) {
        // Fetch access token
        AccessToken accessToken = mongoTemplate.findOne(
                Query.query(Criteria.where("accountId").is(accountId)),
                AccessToken.class
        );
        if (accessToken == null) {
            throw new RuntimeException("Access token not found for accountId: " + accountId);
        }

        // Get post details
        String url = FACEBOOK_API_URL + "/" + postId + "?access_token=" + accessToken.getToken() + "&fields=id,message,picture,full_picture,source";  // Add fields as needed

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Facebook get failed: " + response.getStatusCode());
        }

        return response.getBody();
    }
}