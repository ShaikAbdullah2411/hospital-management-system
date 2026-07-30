package com.planatech.socialposter.facebook_media_service.dto;

public class MediaPostDTO {
	
	private String title;
    private String description;
    private String privacyStatus;  // e.g., "EVERYONE", "FRIENDS"
    private String filePath;  // Local path to the media file
    private String caption;
    private String type;  // "photo" or "video"
    private String accountId;  // Facebook Page/User ID
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPrivacyStatus() {
		return privacyStatus;
	}
	public void setPrivacyStatus(String privacyStatus) {
		this.privacyStatus = privacyStatus;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

}
