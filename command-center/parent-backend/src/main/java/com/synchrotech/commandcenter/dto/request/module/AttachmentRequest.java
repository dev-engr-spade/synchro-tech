package com.synchrotech.commandcenter.dto.request.module;

import java.util.Date;

public class AttachmentRequest {
    private String fileName;
    private String fileType;
    private String url;
    private String uploadedBy;
    private Date uploadedAt;
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getUploadedBy() { return uploadedBy; }
    public void setUploadedBy(String uploadedBy) { this.uploadedBy = uploadedBy; }
    public Date getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(Date uploadedAt) { this.uploadedAt = uploadedAt; }
} 