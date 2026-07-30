package com.planatech.socialposter.facebook_media_service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "access_tokens")
public class AccessToken {
    @Id
    private String id;
    private String accountId;
    private String token;
}