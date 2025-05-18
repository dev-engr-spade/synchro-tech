package com.synchrotech.commandcenter.model.module;

import lombok.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {
    private String id;
    private String fileName;
    private String fileType;
    private String url;
    private String uploadedBy;
    private Date uploadedAt;
} 