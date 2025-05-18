package com.synchrotech.commandcenter.dto.mapper;

import com.synchrotech.commandcenter.model.module.Attachment;
import com.synchrotech.commandcenter.dto.request.module.AttachmentRequest;

public class AttachmentMapper {
    public static Attachment toEntity(AttachmentRequest request) {
        if (request == null) return null;
        return Attachment.builder()
                .fileName(request.getFileName())
                .fileType(request.getFileType())
                .url(request.getUrl())
                .uploadedBy(request.getUploadedBy())
                .uploadedAt(request.getUploadedAt())
                .build();
    }
} 