package com.synchrotech.commandcenter.model.module;

import lombok.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private String id;
    private String userId;
    private String content;
    private Date createdAt;
} 