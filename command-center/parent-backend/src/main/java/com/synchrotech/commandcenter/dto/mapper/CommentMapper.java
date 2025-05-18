package com.synchrotech.commandcenter.dto.mapper;

import com.synchrotech.commandcenter.model.module.Comment;
import com.synchrotech.commandcenter.dto.request.module.CommentRequest;

public class CommentMapper {
    public static Comment toEntity(CommentRequest request) {
        if (request == null) return null;
        return Comment.builder()
                .userId(request.getUserId())
                .content(request.getContent())
                .createdAt(request.getCreatedAt())
                .build();
    }
} 