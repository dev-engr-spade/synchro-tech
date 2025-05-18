package com.synchrotech.commandcenter.dto.request.module;

import java.util.Date;

public class CommentRequest {
    private String userId;
    private String content;
    private Date createdAt;
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
} 